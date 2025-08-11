package io.syslogic.gpr.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.Header;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import io.syslogic.gpr.Constants;
import io.syslogic.gpr.HttpClientImpl;
import io.syslogic.gpr.model.Credentials;

/**
 * Abstract {@link BaseTask}
 * @author Martin Zeitler
 */
abstract public class BaseTask extends DefaultTask {
    String clientId = null;
    String clientSecret = null;
    HttpClient client;
    Gson gson = null;

    /** @return LogHttp */
    @Input
    public abstract Property<Boolean> getLogHttp();

    @Input
    Header[] getHeaders() {
        return HttpClientImpl.getHeaders(this.clientId, this.clientSecret);
    }

    /** It sets up HttpClient and parses two JSON config files. */
    boolean configure(@NotNull Project project, Boolean logHttp) {

        // First try directory `buildSrc`, then the parent project directory.
        File properties = project.file("../buildSrc/token.properties");
        if (! properties.exists()) {properties = project.file("../token.properties");}
        List<String> credentials;
        if (properties.exists()) {credentials = Credentials.getCredentials(properties);}
        else {credentials = Credentials.getCredentials();}

        this.clientId = credentials.get(0);
        this.clientSecret = credentials.get(1);
        this.client = HttpClientImpl.getHttpClient(project, logHttp);
        this.gson = new GsonBuilder().setDateFormat(Constants.GITHUB_API_DATE_FORMAT).create();
        return this.clientId != null && this.clientSecret != null;
    }

    /**
     * <b>Utility</b> Wrapping the JSON response with an array called <code>items</code>, in order to parse it.
     * @param response the JSON response to wrap.
     * @return the wrapped JSON response.
     */
    @NotNull
    protected String wrapResponseItems(@NotNull String response) {
        if (! response.startsWith("[") && ! response.endsWith("]")) {response = "[" + response + "]";}
        response = "{\"items\": " + response + "}";
        return response;
    }

    /**
     * Log to <code>stdout</code>.
     * @param value the string to log.
     */
    protected void stdOut(@NotNull String value) {
        System.out.println(value);
    }

    /**
     * Log to <code>stderr</code>.
     * @param value the string to log.
     */
    protected void stdErr(@NotNull String value) {
        System.err.println(value);
    }
}
