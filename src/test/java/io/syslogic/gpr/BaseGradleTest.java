package io.syslogic.gpr;

import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Abstract Base Gradle Test
 * @author Martin Zeitler
 */
abstract public class BaseGradleTest {

    List<String> buildArgs;
    String parent = "../";
    @TempDir File tmpDir;

    /**
     * Setting up {@link GradleRunner}, when running from directory `buildSrc`.
     * Eventually could also copy to @tmpDir and then inject the configuration.
     */
    @BeforeEach
    public void setup() {
        if (! this.livesInBuildSrc()) {
            // throw new InvalidRunnerConfigurationException("This needs a parent Java project to test with.");
            // TODO: use tmpDir
        }
    }

    /** @return instance of {@link GradleRunner} */
    GradleRunner gradle(@SuppressWarnings("SameParameterValue") String pathName) {
        return GradleRunner.create().withProjectDir(new File(pathName))
                .withPluginClasspath()
                .withDebug(true)
                .forwardOutput();
    }


    /** Check if running from directory `buildSrc` */
    boolean livesInBuildSrc() {
        return new File("").getAbsoluteFile().getName().equals("buildSrc");
    }

    @SuppressWarnings("unused")
    void writeFile(File destination, String content) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination))) {
            output.write(content);
        }
    }
}
