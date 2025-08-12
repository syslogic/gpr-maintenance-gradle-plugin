package io.syslogic.gpr;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.message.BasicHeader;

import org.gradle.api.Project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * HttpClient
 * @author Martin Zeitler
 */
public class HttpClientImpl {

    /**
     * @param clientId the GitHub user name.
     * @param clientSecret the GitHub access token.
     * @return an array of {@link Header}.
     */
    @NotNull
    public static Header[] getRequestHeaders(@NotNull String clientId, @NotNull String clientSecret) {
        Header[] headers = new Header[5];
        headers[0] = new BasicHeader(HttpHeaders.ACCEPT, "application/vnd.github+json");
        headers[1] = new BasicHeader("X-GitHub-Api-Version", Constants.GITHUB_API_VERSION);
        headers[2] = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        headers[3] = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + clientSecret);
        headers[4] = new BasicHeader("client_id", clientId);
        return headers;
    }

    /**
     * PoolingHttpClientConnectionManager is required for subsequent requests.
     * @param project the Gradle project the plugin had been applied to.
     * @param logHttp log HTTP requests to console true/false.
     * @return instance of {@link HttpClient}.
     */
    @NotNull
    public static HttpClient getHttpClient(@NotNull Project project, @NotNull Boolean logHttp) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(20);
        cm.setMaxTotal(100);

        org.apache.hc.client5.http.impl.classic.HttpClientBuilder cb = org.apache.hc.client5.http.impl.classic.HttpClientBuilder.create()
                .setUserAgent("Gradle/" + project.getGradle().getGradleVersion())
                .setConnectionManager(cm);

        if (logHttp) {
            cb.addRequestInterceptorFirst((request, details, context) ->
                            stdOut("> " + request.getRequestUri()))
            .addResponseInterceptorLast((response, details, context) ->
                            stdOut("> " + response.toString()));
        }
        return cb.build();
    }

    /**
     * PoolingHttpClientConnectionManager is required for subsequent requests.
     * @param kilobytes kilobytes transferred.
     * @param ms the transfer duration.
     * @return <code>String</code> formatted transfer rate.
     */
    @NotNull
    @SuppressWarnings("unused")
    String getTransferRate(long kilobytes, long ms) {
        long rate = kilobytes / (ms / 1000) * 1024; // bytes per second
        int u = 0;
        for (; rate > 1024*1024; rate >>= 10) {u++;}
        if (rate > 1024) {u++;}
        return String.format(Locale.ROOT, "%.1f %cB", rate/1024f, " kMGTPE".charAt(u))+ "/s";
    }

    /**
     * Obtain the pagination "next page" link from headers.
     * @param headers the headers to parse.
     * @return <code>String</code> next page URL or <code>null</code>.
     */
    @Nullable
    public static String getNextPage(@NotNull Header[] headers) {
        for (Header header : headers) {
            if (header.getName().equals("Link")) {
                @NotNull String[] links = header.getValue().split(", ");
                for (String link : links) {
                    @NotNull String[] parts = link.split("; ");
                    if (parts[1].contains("next")) {
                        return parts[0].replaceAll("[<>]", "");
                    }
                }
            }
        }
        return null;
    }

    /**
     * Log to <code>stdout</code>.
     * @param value the string to log.
     */
    protected static void stdOut(@NotNull String value) {
        System.out.println(value);
    }

    /**
     * Log to <code>stderr</code>.
     * @param value the string to log.
     */
    @SuppressWarnings("unused")
    protected static void stdErr(@NotNull String value) {
        System.err.println(value);
    }
}
