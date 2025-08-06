package io.syslogic.gpr.task;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;

import org.jetbrains.annotations.NotNull;

/**
 * Abstract {@link BasePackageTask}
 * @author Martin Zeitler
 */
abstract public class BasePackageTask extends BaseTask {

    @Input
    @Optional
    public abstract Property<String> getPackageType();

    @Input
    @Optional
    public abstract Property<String> getGroupId();

    @Input
    @Optional
    public abstract Property<String> getUserName();

    @Input
    @Optional
    public abstract Property<String> getPackageName();

    @Input
    @Optional
    public abstract Property<String> getVersionName();

    @Internal
    public abstract Property<Long> getVersionId();
}
