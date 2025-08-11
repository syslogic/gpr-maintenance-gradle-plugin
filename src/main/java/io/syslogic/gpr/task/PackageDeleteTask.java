package io.syslogic.gpr.task;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

import io.syslogic.gpr.Constants;
import io.syslogic.gpr.response.ErrorResponse;

/**
 * Package Delete {@link BasePackageTask}
 * @see <a href="https://docs.github.com/en/rest/packages/packages#delete-a-package-version-for-the-authenticated-user">Delete a package version for the authenticated user</a>
 * @author Martin Zeitler
 */
abstract public class PackageDeleteTask extends BasePackageTask {

    /** @return DeleteLastVersion */
    @Input
    @Optional
    public abstract Property<Boolean> getDeleteLastVersion();

    /**
     * {@link TaskAction}: Delete the version of a package.
     * user/packages/PACKAGE_TYPE/PACKAGE_NAME/versions/PACKAGE_VERSION_ID
     */
    @TaskAction
    public void run() {
        if (this.configure(getProject(), getLogHttp().get())) {
            String uri = Constants.getPackageUri(getPackageType().get(), getPackageName().get(), getVersionId().get());
            if (getLogHttp().get()) {this.stdOut("DELETE " + uri);}
            try {
                HttpDelete request = new HttpDelete(new URIBuilder(uri).build());
                request.setHeaders(this.getHeaders());
                client.execute(request, response -> {
                    if (response.getCode() == HttpStatus.SC_NO_CONTENT) {
                        this.stdOut("> [GPR] package version " + getVersionId().get() + " deleted.");
                        return true;
                    } else if (response.getCode() == HttpStatus.SC_BAD_REQUEST) {
                        HttpEntity httpEntity = response.getEntity();
                        String result = EntityUtils.toString(httpEntity);
                        if (getDeleteLastVersion().get()) {
                            this.deleteLastVersion();
                        } else {
                            String message = this.gson.fromJson(result, ErrorResponse.class).getMessage();
                            this.stdErr("> [GPR] " + message);
                        }
                    } else if (getLogHttp().get()) {
                        this.stdErr("HTTP " + response.getCode() + " " + response.getReasonPhrase());
                    }
                    return false;
                });
            } catch(Exception e) {
                this.stdErr("> [GPR] " + e.getMessage());
            }
        }
    }

    void deleteLastVersion() {
        String uri = Constants.getPackageDeleteUri(getPackageType().get(), getPackageName().get());
        if (getLogHttp().get()) {this.stdOut("DELETE " + uri);}
        try {
            HttpDelete request = new HttpDelete(new URIBuilder(uri).build());
            request.setHeaders(this.getHeaders());
            client.execute(request, response -> {
                if (response.getCode() == HttpStatus.SC_NO_CONTENT) {
                    this.stdOut("> [GPR] package " + getPackageName().get() + " deleted.");
                    getVersionId().set(0L);
                    return true;
                } else if (getLogHttp().get()) {
                    this.stdErr("HTTP " + response.getCode() + " " + response.getReasonPhrase());
                }
                return false;
            });
        } catch(Exception e) {
            this.stdErr(e.getMessage());
        }
    }
}
