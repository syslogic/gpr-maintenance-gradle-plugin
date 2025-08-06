package io.syslogic.gpr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

import io.syslogic.gpr.model.Credentials;
import io.syslogic.gpr.response.VersionResponse;
import io.syslogic.gpr.task.PackageDeleteTask;
import io.syslogic.gpr.task.PackageGetTask;
import io.syslogic.gpr.task.PackageListTask;
import io.syslogic.gpr.task.PackageRestoreTask;
import io.syslogic.gpr.task.VersionIdTask;

/**
 * GitHub Package Registry: Maintenance Plugin
 * @author Martin Zeitler
 */
@SuppressWarnings({"unused"})
class GprMaintenancePlugin implements Plugin<Project> {
    @NotNull private final String taskGroup = "publishing";
    @NotNull private String packageType = "maven";
    @SuppressWarnings("FieldCanBeLocal")
    @NotNull private final String extName = "gpr";
    @Nullable private File tokenProperties = null;
    @NotNull private Boolean listPackagesAfterPublish = false;
    @NotNull private Boolean deleteLastVersion = false;
    @NotNull private Boolean logHttp = false;
    @Nullable private String groupId = null;
    @Nullable private String userName = null;
    @Nullable private String packageName = null;
    @Nullable private String versionName = null;
    @NotNull private Long versionId = 0L;

    @Override
    public void apply(@NotNull Project project) {

        /* Check for plugin `maven-publish`. */
        this.applyMavenPublish(project);

        /* Create the project extension. */
        GprMaintenance extension = this.applyExtension(project);

        /* Project after evaluate. */
        project.afterEvaluate(it -> {

            /* Using the groupId & version of the project. */
            this.groupId = it.getGroup().toString();
            this.versionName = it.getVersion().toString();

            this.packageType = extension.getPackageType();
            this.packageName = it.getGroup() + "." + extension.getPackageName();

            if (extension.getTokenProperties() != null) {
                this.tokenProperties = new File(extension.getTokenProperties());
            }

            if (this.tokenProperties == null || ! this.tokenProperties.exists()) {
                this.tokenProperties = project.file("../buildSrc/token.properties");
                if (!this.tokenProperties.exists()) {
                    this.tokenProperties = project.file("../token.properties");
                }
            }

            this.listPackagesAfterPublish = extension.getListPackagesAfterPublish();
            this.deleteLastVersion = extension.getDeleteLastVersion();
            this.logHttp = extension.getLogHttp();

            /* HTTP: Translate versionName to versionId. */
            this.updateVersionId(project, this.packageName);

            /* Always register the list task. */
            this.registerPackageListTask(it.getTasks(), Constants.PACKAGE_LIST_TASK);
            this.registerVersionIdTask(it.getTasks(), Constants.VERSION_ID_TASK);

            /* Register get & delete tasks, when a versionId is known. */
            if (versionId > 0L) {
                this.registerPackageDeleteTask(it.getTasks(), Constants.PACKAGE_DELETE_TASK + this.versionId);
                this.registerPackageGetTask(it.getTasks(), Constants.PACKAGE_GET_TASK + this.versionId);
            } else {
                // It is unclear where to get the ID to restore from.
                // this.registerPackageRestoreTask(it.getTasks(), Constants.PACKAGE_RESTORE_TASK);
            }
        });
    }

    /** Translate versionName to versionId, synchronous call. */
    void updateVersionId(@NotNull Project project, String packageName) {
        List<String> credentials;
        if (this.tokenProperties != null && this.tokenProperties.exists()) {
            credentials = Credentials.getCredentials(this.tokenProperties);
        } else {
            credentials = Credentials.getCredentials();
        }
        this.userName = credentials.get(0);
        String versionName = project.getVersion().toString();
        String uri = Constants.getPackageVersionsUri(packageType, packageName);
        try {
            HttpGet request = new HttpGet(new URIBuilder(uri).build());
            request.setHeaders(HttpClientImpl.getHeaders(credentials.get(0), credentials.get(1)));
            HttpClient client = HttpClientImpl.getHttpClient(project, logHttp);
            this.versionId = client.execute(request, response -> {
                VersionResponse versionResponse;
                if (response.getCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = response.getEntity();
                    String result = EntityUtils.toString(httpEntity);
                    Gson gson = new GsonBuilder().setDateFormat(Constants.GITHUB_API_DATE_FORMAT).create();
                    versionResponse = gson.fromJson(wrapResponseItems(result), VersionResponse.class);
                    for (io.syslogic.gpr.model.Version item : versionResponse.getItems()) {
                        this.stdOut("| + " + item.getId() + " ~ " + item.getName() + " -> " + item.getPackageHtmlUrl());
                        if (item.getName().equals(versionName)) {
                            return item.getId(); // versionId
                        }
                    }
                } else if (response.getCode() == HttpStatus.SC_NOT_FOUND) {
                    this.stdOut("> [GPR] package " + packageName + " not found.");
                } else if (this.logHttp) {
                    this.stdErr("HTTP " + response.getCode() + " " + response.getReasonPhrase());
                }
                return 0L;
            });
        } catch (Exception e) {
            this.stdErr(e.getMessage());
        }
    }

    /** The {@link PackageListTask} lists packages and their versions. */
    @SuppressWarnings("SameParameterValue")
    void registerPackageListTask(@NotNull TaskContainer tasks, @NotNull String taskName) {
        if (tasks.findByName(taskName) == null) {
            tasks.register(taskName, PackageListTask.class, task -> {
                task.setGroup(taskGroup);
                task.getGroupId().set(groupId);
                task.getPackageType().set(packageType);
                task.getPackageName().set(packageName);
                if (versionName != null) {task.getVersionName().set(versionName);}
                task.getLogHttp().set(logHttp);
            });

            /* List all packages after publish. */
            if (this.listPackagesAfterPublish) {
                tasks.getByName("publish").finalizedBy(taskName);
            }
        }
    }

    /** The {@link VersionIdTask} updates the extension package versionId. */
    @SuppressWarnings("SameParameterValue")
    void registerVersionIdTask(@NotNull TaskContainer tasks, @NotNull String taskName) {
        if (tasks.findByName(taskName) == null) {
            tasks.register(taskName, VersionIdTask.class, task -> {
                task.setGroup(taskGroup);
                task.getGroupId().set(groupId);
                task.getPackageType().set(packageType);
                task.getPackageName().set(packageName);
                task.getVersionName().set(versionName);
                task.getLogHttp().set(logHttp);
            });

            /* Always run after publish. */
            tasks.getByName("publish").finalizedBy(taskName);
        }
    }

    /** The {@link PackageGetTask} gets a package. */
    @SuppressWarnings("SameParameterValue")
    void registerPackageGetTask(@NotNull TaskContainer tasks, @NotNull String taskName) {
        if (tasks.findByName(taskName) == null) {
            tasks.register(taskName, PackageGetTask.class, task -> {
                task.setGroup(taskGroup);
                task.getGroupId().set(groupId);
                task.getUserName().set(userName);
                task.getPackageType().set(packageType);
                task.getPackageName().set(packageName);
                if (versionId > 0L) {task.getVersionId().set(versionId);}
                task.getLogHttp().set(logHttp);
            });
        }
    }

    /** The {@link PackageDeleteTask} deletes a package. */
    @SuppressWarnings("SameParameterValue")
    void registerPackageDeleteTask(@NotNull TaskContainer tasks, @NotNull String taskName) {
        if (tasks.findByName(taskName) == null) {
            tasks.register(taskName, PackageDeleteTask.class, task -> {
                task.setGroup(taskGroup);
                task.getGroupId().set(groupId);
                task.getUserName().set(userName);
                task.getPackageType().set(packageType);
                task.getPackageName().set(packageName);
                task.getVersionName().set(versionName);
                task.getVersionId().set(versionId);
                task.getDeleteLastVersion().set(deleteLastVersion);
                task.getLogHttp().set(logHttp);
            });

            /* Delete the package upon publish. */
            if (this.deleteLastVersion) {
                tasks.getByName("publish").dependsOn(taskName);
            }
        }
    }

    /** The {@link PackageRestoreTask} restores a package. */
    @SuppressWarnings("SameParameterValue")
    void registerPackageRestoreTask(@NotNull TaskContainer tasks, @NotNull String taskName) {
        if (tasks.findByName(taskName) == null) {
            tasks.register(taskName, PackageRestoreTask.class, task -> {
                task.setGroup(taskGroup);
                task.getGroupId().set(groupId);
                task.getUserName().set(userName);
                task.getPackageType().set(packageType);
                task.getPackageName().set(packageName);
                if (versionId > 0L) {task.getVersionId().set(versionId);}
                task.getLogHttp().set(logHttp);
            });
        }
    }

    @NotNull
    private GprMaintenance applyExtension(@NotNull Project project) {
        return project.getExtensions().create(this.extName, GprMaintenanceExtension.class);
    }

    private void applyMavenPublish(@NotNull Project project) {
        if (! project.getPluginManager().hasPlugin("maven-publish")) {
            project.getPluginManager().apply("maven-publish");
        }
    }

    /** <b>Utility</b> Wrapping the JSON response with an array called <code>items</code>, in order to parse it. */
    @NotNull
    protected String wrapResponseItems(@NotNull String response) {
        if (! response.startsWith("[") && ! response.endsWith("]")) {response = "[" + response + "]";}
        response = "{\"items\": " + response + "}";
        return response;
    }

    /** Printing logs to <code>stdout</code>. */
    void stdOut(@NotNull String value) {
        System.out.println(value);
    }

    /** Printing logs to <code>stderr</code>. */
    void stdErr(@SuppressWarnings("SameParameterValue") @NotNull String value) {
        System.err.println(value);
    }
}
