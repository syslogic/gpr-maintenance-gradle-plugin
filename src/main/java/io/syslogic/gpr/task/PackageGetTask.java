package io.syslogic.gpr.task;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import org.gradle.api.tasks.TaskAction;

import io.syslogic.gpr.Constants;
import io.syslogic.gpr.response.VersionResponse;

/**
 * Package Get {@link BasePackageTask}
 * @see <a href="https://docs.github.com/en/rest/packages/packages#get-a-package-for-the-authenticated-user">Get a package version for the authenticated user</a>
 * @author Martin Zeitler
 */
abstract public class PackageGetTask extends BasePackageTask {

    /**
     * {@link TaskAction}: Get a package.
     * user/packages/PACKAGE_TYPE/PACKAGE_NAME/versions/PACKAGE_VERSION_ID
     */
    @TaskAction
    public void run() {
        if (this.configure(getProject(), getLogHttp().get())) {
            String uri = Constants.getPackageUri(getPackageType().get(), getPackageName().get(), getVersionId().get());
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
                            this.stdOut(item.toString());
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
}
