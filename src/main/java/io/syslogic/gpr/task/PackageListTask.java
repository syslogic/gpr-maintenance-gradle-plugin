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

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import io.syslogic.gpr.Constants;
import io.syslogic.gpr.HttpClientImpl;
import io.syslogic.gpr.model.Package;
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

    ArrayList<Package> mItems = new ArrayList<>();

    /** The default {@link TaskAction}. */
    @TaskAction
    public void run() {
        if (this.configure(getProject(), getLogHttp().get())) {
            String uri = Constants.getPackageIndexUri(getPackageType().get(), getPageSize().get(), this.currentPage);
            this.getPackages(uri);
        }
    }

    /**
     * List packages and their versions for the authenticated user.
     * @param uri the paginated URL for the request.
     */
    public void getPackages(String uri) {
        if (getLogHttp().get()) {this.stdOut("GET " + uri);}
        try {
            AtomicBoolean hasNext = new AtomicBoolean(true);
            HttpGet request = new HttpGet(new URIBuilder(uri).build());
            request.setHeaders(this.getHeaders());
            while (hasNext.get()) {
                PackageResponse paged = client.execute(request, response -> {
                    String nextPage = HttpClientImpl.getNextPage(response.getHeaders());
                    PackageResponse packageResponse = new PackageResponse();
                    if (response.getCode() == HttpStatus.SC_OK) {
                        HttpEntity httpEntity = response.getEntity();
                        String result = EntityUtils.toString(httpEntity);
                        packageResponse = this.gson.fromJson(wrapResponseItems(result), PackageResponse.class);
                        for (io.syslogic.gpr.model.Package item : packageResponse.getItems()) {
                            this.stdOut(item.toString());
                            this.getPackageVersions(item.getName());
                            this.stdOut(""); // line-feed.
                        }
                        if (nextPage != null) {
                            // Return that "next page" pagination-link with the response.
                            if (getLogHttp().get()) {this.stdOut("| next page: " + nextPage);}
                            packageResponse.setNextPage(nextPage);
                        } else {
                            packageResponse.setNextPage(null);
                            hasNext.set(false);
                        }
                        return packageResponse;
                    } else if (getLogHttp().get()) {
                        this.stdErr("HTTP " + response.getCode() + " " + response.getReasonPhrase());
                    }
                    return packageResponse;
                });

                ArrayList<Package> items = paged.getItems();
                mItems.addAll(items);
                if (paged.getNextPage() != null) {
                    request.setUri(URI.create(paged.getNextPage()));
                }
            }
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
