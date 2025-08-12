package io.syslogic.gpr.model;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import io.syslogic.gpr.Constants;

/**
 * GitHub API Credentials
 * @author Martin Zeitler
 */
public class Credentials {

    static List<String> separators = List.of(" ", "|", "/");
    static List<String> data = List.of("");

    /**
     * Attempt to obtain credentials from file <code>token.properties</code>.
     * When not found, fall-back to the default GitHub environmental variables.
     * @param file the file to parse to credentials.
     * @return a list with username and token.
     */
    @NotNull
    public static List<String> getCredentials(@NotNull File file) {
        if (file.exists()) {
            // Attempt to parse file `token.properties`.
            String value = readFile(file).trim();
            for (String separator : separators) {
                if (value.contains(separator)) {
                    data = List.of(readFile(file).split(separator));
                    break; // for
                }
            }
        } else if (System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null) {
            // Attempt to pick up the default GitHub environmental variables.
            data = List.of(System.getenv("GITHUB_ACTOR").trim(), System.getenv("GITHUB_TOKEN").trim());
        } else {
            stdErr("*** File `token.properties` not found:" + file.getAbsolutePath());
            stdErr("*** `$GITHUB_ACTOR` and `$GITHUB_TOKEN` also not present");
        }
        return data;
    }

    /**
     * Check file <code>token.properties</code> for format plausibility.
     * @param file the file to check for format plausibility.
     * @return true, when the format seems plausible, else false.
     */
    @NotNull
    public static Boolean isPlausibleFormat(@NotNull File file) {
        if (file.exists()) { // exists
            String value = readFile(file).trim();
            return value.matches(Constants.PATTERN_TOKEN_FORMAT);
        }
        return false;
    }

    /**
     * Obtain credentials from the default environmental variables.
     * @return a list with username and token.
     */
    @NotNull
    public static List<String> getCredentials() {
        List<String> data = List.of("");
        if (System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null) {
            data = List.of(System.getenv("GITHUB_ACTOR"), System.getenv("GITHUB_TOKEN"));
        } else {
            stdErr("*** `$GITHUB_ACTOR` and `$GITHUB_TOKEN` not present");
        }
        return data;
    }

    /**
     * Read file.
     * @param file the file to read.
     * @return the file contents.
     */
    @NotNull
    private static String readFile(@NotNull File file) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {sb.append(line);}
            fis.close();
        } catch (IOException e) {
            stdErr(e.getMessage());
        }
        return sb.toString();
    }

    /**
     * Log to <code>stderr</code>.
     * @param value the string to log.
     */
    private static void stdErr(@NotNull String value) {
        System.err.println(value);
    }
}
