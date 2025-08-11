package io.syslogic.gpr.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import org.gradle.api.tasks.TaskAction;

import io.syslogic.gpr.Constants;
import io.syslogic.gpr.HttpClientImpl;
import io.syslogic.gpr.response.VersionResponse;

/**
 * Package Get {@link BasePackageTask}
 * @see <a href="https://docs.github.com/en/rest/packages/packages#get-a-package-for-the-authenticated-user">Get a package version for the authenticated user</a>
 * @author Martin Zeitler
 */
abstract public class VersionIdTask extends BasePackageTask {

    /**
     * {@link TaskAction}: Get a package.
     * user/packages/PACKAGE_TYPE/PACKAGE_NAME/versions/PACKAGE_VERSION_ID
     */
    @TaskAction
    public void run() {
        if (this.configure(getProject(), getLogHttp().get())) {
            String versionName = getProject().getVersion().toString();
            String uri = Constants.getPackageVersionsUri(getPackageType().get(), getPackageName().get());
            try {
                HttpGet request = new HttpGet(new URIBuilder(uri).build());
                request.setHeaders(HttpClientImpl.getRequestHeaders(this.clientId, this.clientSecret));
                HttpClient client = HttpClientImpl.getHttpClient(getProject(), getLogHttp().get());
                getVersionId().set((Long) client.execute(request, response -> {
                    VersionResponse versionResponse;
                    if (response.getCode() == HttpStatus.SC_OK) {
                        HttpEntity httpEntity = response.getEntity();
                        String result = EntityUtils.toString(httpEntity);
                        Gson gson = new GsonBuilder().setDateFormat(Constants.GITHUB_API_DATE_FORMAT).create();
                        versionResponse = gson.fromJson(wrapResponseItems(result), VersionResponse.class);
                        for (io.syslogic.gpr.model.Version item : versionResponse.getItems()) {
                            this.stdOut("| + " + item.getId() + " ~ " + item.getName() + " -> " + item.getHtmlUrl());
                            if (item.getName().equals(versionName)) {return item.getId();}
                        }
                    } else if (response.getCode() == HttpStatus.SC_NOT_FOUND) {
                            this.stdOut("> [GPR] package " + getPackageName().get() + " not found.");
                        } else if (getLogHttp().get()) {
                            this.stdErr("HTTP " + response.getCode() + " " + response.getReasonPhrase());
                        }
                    return 0L;
                }));
            } catch (Exception e) {
                this.stdErr(e.getMessage());
            }
        }
    }
}
