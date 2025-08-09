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

    /** Note: Module <code>:library</code> is now being called <code>:androidx-github</code>. */
    @Test
    public void testLibraryPublish() {
        this.buildArgs = List.of(":androidx-github:publish", "--info");
        BuildResult result = gradle(parent).withArguments(buildArgs).build();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getOutput().contains("BUILD SUCCESSFUL"));
    }
}
