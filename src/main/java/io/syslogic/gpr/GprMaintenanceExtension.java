package io.syslogic.gpr;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Public API for Gradle build scripts.
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class GprMaintenanceExtension implements GprMaintenance {

    String packageType = "maven";
    String groupId = null;
    String packageName = null;
    String versionName = null;
    String tokenProperties = null;
    Boolean deleteOnConflict = false;
    Boolean deleteLastVersion = false;
    Boolean listPackagesAfterPublish = false;
    Boolean logHttp = false;
    Integer pageSize = 30;

    /** {@inheritDoc} */
    @Override
    public void setPackageType(@NotNull String value) {
        value = value.toLowerCase(Locale.ROOT);
        if (! GprMaintenance.packageTypes.contains(value)) {
            throw new IllegalArgumentException("unknown package-type: " + value);
        } else {
            this.packageType = value;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setTokenProperties(@NotNull String value) {
        this.tokenProperties = value;
    }

    /** {@inheritDoc} */
    @Override
    public void setLogHttp(@NotNull Boolean value) {
        this.logHttp = value;
    }

    /** {@inheritDoc} */
    @Override
    public void setListPackagesAfterPublish(Boolean value) {
        this.listPackagesAfterPublish = value;
    }

    /** {@inheritDoc} */
    @Override
    public void setDeleteOnConflict(Boolean value) {
        this.deleteOnConflict = value;
    }

    /** {@inheritDoc} */
    @Override
    public void setDeleteLastVersion(Boolean value) {
        this.deleteLastVersion = value;
    }

    /** {@inheritDoc} */
    @Override
    public void setGroupId(@NotNull String value) {
        this.groupId = value;
    }

    /** {@inheritDoc} */
    @Override
    public void setPackageName(String value) {
        this.packageName = value;
    }

    /** {@inheritDoc} */
    @Override
    public void setVersionName(String value) {
        this.versionName = value;
    }

    @Override
    public void setPageSize(Integer value) {
        if (value != null && value >= 0 && value <= 100 ) {
            this.pageSize = value;
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getPackageType() {
        return this.packageType;
    }

    @Override
    public Integer getPageSize() {
        return this.pageSize;
    }

    /** {@inheritDoc} */
    @Override
    public String getTokenProperties() {
        return this.tokenProperties;
    }

    /** {@inheritDoc} */
    @Override
    public Boolean getLogHttp() {
        return this.logHttp;
    }

    /** {@inheritDoc} */
    @Override
    public Boolean getListPackagesAfterPublish() {
        return this.listPackagesAfterPublish;
    }

    /** {@inheritDoc} */
    @Override
    public Boolean getDeleteOnConflict() {
        return this.deleteOnConflict;
    }

    /** {@inheritDoc} */
    @Override
    public Boolean getDeleteLastVersion() {
        return this.deleteLastVersion;
    }

    /** {@inheritDoc} */
    @Override
    public String getGroupId() {
        return this.groupId;
    }

    /** {@inheritDoc} */
    @Override
    public String getPackageName() {
        return this.packageName;
    }

    /** {@inheritDoc} */
    @Override
    public String getVersionName() {
        return this.versionName;
    }
}
