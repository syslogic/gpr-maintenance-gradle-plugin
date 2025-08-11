package io.syslogic.gpr.task;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import org.jetbrains.annotations.NotNull;

import io.syslogic.gpr.Constants;
import io.syslogic.gpr.HttpClientImpl;
import io.syslogic.gpr.response.PackageResponse;
import io.syslogic.gpr.response.VersionResponse;

/**
 * Package List {@link BasePackageTask}
 * @see <a href="https://docs.github.com/en/rest/packages/packages#list-packages-for-the-authenticated-users-namespace">List packages for the authenticated user's namespace</a>
 * @author Martin Zeitler
 */
abstract public class PackageListTask extends BasePackageTask {

    /**
     * API Page Size
     * @return the integer page-size
     */
    @Input
    @Optional
    public abstract Property<Integer> getPageSize();

    @NotNull Integer currentPage = 1;

    /** The default {@link TaskAction}. */
    @TaskAction
    public void run() {
        if (this.configure(getProject(), getLogHttp().get())) {
            this.getPackages(getPageSize().get(), this.currentPage);
        }
    }

    /**
     * List all packages of the authenticated user and the versions.
     * TODO: pagination support?
     * @param pageSize the page-size for the request.
     */
    public void getPackages(int pageSize, int page) {

        String uri = Constants.USER_PACKAGES + "?package_type=" + getPackageType().get() + "&per_page=" + pageSize + "&page=" + page;
        if (getLogHttp().get()) {
            this.stdOut("GET " + uri);
            this.stdOut("");
        }
        try {
            HttpGet request = new HttpGet(new URIBuilder(uri).build());
            request.setHeaders(this.getHeaders());
            client.execute(request, response -> {
                String nextPage = HttpClientImpl.getNextPage(response.getHeaders());
                if (nextPage != null && getLogHttp().get()) {this.stdOut(nextPage);}
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

                // TODO: Somehow use that "next page" pagination link.
                if (nextPage != null) {
                    packageResponse.setNextPage(nextPage);
                    if (getLogHttp().get()) {
                        this.stdOut("| next page: " + nextUrl);
                    }
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
                        this.stdOut("| + " + item.getId() + " ~ " + item.getName() + " -> " + item.getHtmlUrl());
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
