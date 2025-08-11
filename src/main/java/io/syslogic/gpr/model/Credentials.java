package io.syslogic.gpr.model;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * GitHub API Credentials
 * @author Martin Zeitler
 */
public class Credentials {
    static List<String> separators = List.of(" ", "|", "/");
    static List<String> data = List.of("");

    @NotNull
    public static List<String> getCredentials(@NotNull File properties) {
        if (properties.exists()) {
            // Attempt to parse file `token.properties`.
            String value = readFile(properties).trim();
            for (String separator : separators) {
                if (value.contains(separator)) {
                    data = List.of(readFile(properties).split(separator));
                    break; // for
                }
            }
        } else if (System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null) {
            // Attempt to pick up GitHub environmental variables.
            data = List.of(System.getenv("GITHUB_ACTOR").trim(), System.getenv("GITHUB_TOKEN").trim());
        } else {
            stdErr("*** File `token.properties` missing:" + properties.getAbsolutePath());
            stdErr("*** `$GITHUB_ACTOR` and `$GITHUB_TOKEN` also not present");
        }
        return data;
    }

    @NotNull
    public static Boolean isPlausible(@NotNull File properties) {
        List<String> separators = List.of(" ", "|", "/");
        if (properties.exists()) { // exists
            String value = readFile(properties).trim();
            for (String separator : separators) {
                if (value.contains(separator)) {
                    data = List.of(readFile(properties).split(separator));
                    return data.size() == 2;
                }
            }
        }
        return false;
    }

    @NotNull
    public static List<String> getCredentials() {
        List<String> data = List.of("");
        if (System.getenv("GITHUB_ACTOR") != null && System.getenv("GITHUB_TOKEN") != null) {
            data = List.of(System.getenv("GITHUB_ACTOR"), System.getenv("GITHUB_TOKEN"));
        }
        return data;
    }

    @NotNull
    private static String readFile(@NotNull File file) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            while ((line = reader.readLine()) != null) {sb.append(line);}
        } catch (IOException e) {
            stdErr(e.getMessage());
        }
        return sb.toString();
    }

    private static void stdErr(@NotNull String value) {
        System.err.println(value);
    }
}
