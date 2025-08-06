package io.syslogic.gpr;

import org.gradle.testkit.runner.BuildResult;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * GPR Maintenance Test
 * @author Martin Zeitler
 */
public class GprMaintenanceTest extends BaseGradleTest {

    @Test
    public void testJavadocReleaseGeneration() {
        this.buildArgs = List.of(":library:javadocReleaseGeneration", "--stacktrace");
        BuildResult result = gradle(parent).withArguments(buildArgs).build();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getOutput().contains("BUILD SUCCESSFUL"));
    }

    @Test
    public void testLibraryPublish() {
        this.buildArgs = List.of(":library:publish", "--info");
        BuildResult result = gradle(parent).withArguments(buildArgs).build();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getOutput().contains("BUILD SUCCESSFUL"));
    }
}
