package io.syslogic.gpr.task;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;

/**
 * Abstract {@link BasePackageTask}
 * @author Martin Zeitler
 */
abstract public class BasePackageTask extends BaseTask {

    /**
     * Optional Property: PackageType
     * @return string package-type.
     */
    @Input
    @Optional
    public abstract Property<String> getPackageType();

    /**
     * Optional Property: GroupId
     * @return string package group.
     */
    @Input
    @Optional
    public abstract Property<String> getGroupId();

    /**
     * Optional Property: UserName
     * @return string username.
     */
    @Input
    @Optional
    public abstract Property<String> getUserName();

    /**
     * Optional Property: PackageName
     * @return string repository name
     */
    @Input
    @Optional
    public abstract Property<String> getPackageName();

    /**
     * Optional Property: VersionName
     * @return string package version name.
     */
    @Input
    @Optional
    public abstract Property<String> getVersionName();

    /**
     * Internal Property: VersionId
     * @return the long versionId of a package.
     */
    @Internal
    public abstract Property<Long> getVersionId();
}
