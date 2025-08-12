package io.syslogic.gpr;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;

import io.syslogic.gpr.model.Credentials;

/**
 * Public API for Gradle build scripts.
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class GprMaintenanceExtension implements GprMaintenance {

    private String packageType = "maven";
    private String groupId = null;
    private String packageName = null;
    private String tokenProperties = null;
    private Boolean deleteOnConflict = false;
    private Boolean deleteLastVersion = false;
    private Boolean listPackagesAfterPublish = false;
    private Boolean logHttp = false;
    private Integer pageSize = 30;

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
    public void setTokenProperties(@NotNull String value) throws IllegalArgumentException {
        if (! Credentials.isPlausibleFormat(new File(value))) {
            throw new IllegalArgumentException("file not found or bad format: " + value);
        } else {
            this.tokenProperties = value;
        }
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

    /** {@inheritDoc} */
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
}
