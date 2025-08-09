package io.syslogic.gpr.task;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import org.gradle.api.tasks.TaskAction;

import io.syslogic.gpr.Constants;
import io.syslogic.gpr.response.PackageResponse;
import io.syslogic.gpr.response.VersionResponse;

/**
 * Package List {@link BasePackageTask}
 * @see <a href="https://docs.github.com/en/rest/packages/packages#list-packages-for-the-authenticated-users-namespace">List packages for the authenticated user's namespace</a>
 * @author Martin Zeitler
 */
abstract public class PackageListTask extends BasePackageTask {

    /** The default {@link TaskAction}. */
    @TaskAction
    public void run() {
        if (this.configure(getProject(), getLogHttp().get())) {
            this.getPackages(100);
        }
    }

    /** List all packages of the authenticated user and the versions. */
    public void getPackages(int pageSize) {
        String uri = Constants.USER_PACKAGES + "?package_type=" + getPackageType().get() + "&per_page=" + pageSize;
        if (getLogHttp().get()) {
            this.stdOut("GET " + uri);
            this.stdOut("");
        }
        try {
            HttpGet request = new HttpGet(new URIBuilder(uri).build());
            request.setHeaders(this.getHeaders());
            client.execute(request, response -> {
                PackageResponse packageResponse = null;
                if (response.getCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = response.getEntity();
                    String result = EntityUtils.toString(httpEntity);
                    packageResponse = this.gson.fromJson(wrapResponseItems(result), PackageResponse.class);
                    for (io.syslogic.gpr.model.Package item : packageResponse.getItems()) {
                        this.stdOut(item.toString());
                        this.getPackageVersions(item.getName());
                        this.stdOut(""); // line-feed.
                    }
                } else if (getLogHttp().get()) {
                    this.stdErr("HTTP " + response.getCode() + " " + response.getReasonPhrase());
                }
                return packageResponse;
            });

        } catch(Exception e) {
            this.stdErr(e.getMessage());
        }
    }

    /**
     * List all versions of a package.
     * @param packageName name of the package, formatted as: groupId(dotted) + repoName(dashed).
     */
    public void getPackageVersions(String packageName) {
        String uri = Constants.USER_PACKAGES + "/" + getPackageType().get() + "/" + packageName + "/versions";
        if (getLogHttp().get()) {this.stdOut("GET " + uri);}
        try {
            HttpGet request = new HttpGet(new URIBuilder(uri).build());
            request.setHeaders(this.getHeaders());
            client.execute(request, response -> {
                VersionResponse versionResponse = null;
                if (response.getCode() == HttpStatus.SC_OK) {
                    HttpEntity httpEntity = response.getEntity();
                    String result = EntityUtils.toString(httpEntity);
                    versionResponse = this.gson.fromJson(wrapResponseItems(result), VersionResponse.class);
                    for (io.syslogic.gpr.model.Version item : versionResponse.getItems()) {
                        this.stdOut("| + " + item.getId() + " ~ " + item.getName() + " -> " + item.getPackageHtmlUrl());
                    }
                } else if (getLogHttp().get()) {
                    this.stdErr("HTTP " + response.getCode() + " " + response.getReasonPhrase());
                }
                return versionResponse;
            });
        } catch(Exception e) {
            this.stdErr(e.getMessage());
        }
    }
}
