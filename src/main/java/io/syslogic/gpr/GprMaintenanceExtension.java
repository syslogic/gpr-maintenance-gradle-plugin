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

    /**
     * Package Type
     * <code>gpr.packageType = "maven"</code>
     * @param value whether true or false.
     */
    @Override
    public void setPackageType(@NotNull String value) {
        value = value.toLowerCase(Locale.ROOT);
        if (! GprMaintenance.packageTypes.contains(value)) {
            throw new IllegalArgumentException("unknown package-type: " + value);
        } else {
            this.packageType = value;
        }
    }

    /**
     * Token Properties (Personal Access Token)
     * <code>gpr {tokenProperties = ""}</code>
     * @param value the absolute path to the <code>token.properties</code> file.
     */
    @Override
    public void setTokenProperties(@NotNull String value) {
        this.tokenProperties = value;
    }

    /**
     * HTTP Logging
     * <code>gpr.logHttp = true</code>
     * @param value whether true or false.
     */
    @Override
    public void setLogHttp(@NotNull Boolean value) {
        this.logHttp = value;
    }

    @Override
    public void setListPackagesAfterPublish(Boolean value) {
        this.listPackagesAfterPublish = value;
    }

    @Override
    public void setDeleteOnConflict(Boolean value) {
        this.deleteOnConflict = value;
    }

    /**
     * Delete Last Version
     * <code>gpr.deleteLastVersion = true</code>
     * @param value whether true or false.
     */
    @Override
    public void setDeleteLastVersion(Boolean value) {
        this.deleteLastVersion = value;
    }

    /**
     * Package Group
     * <code>gpr.groupId = "io.syslogic"</code>
     * @param value whether true or false.
     */
    @Override
    public void setGroupId(@NotNull String value) {
        this.groupId = value;
    }

    /**
     * Package Name
     * <code>gpr.packageName = "org.acme.box"</code>
     * @param value whether true or false.
     */
    @Override
    public void setPackageName(String value) {
        this.packageName = value;
    }

    /**
     * Version Name
     * <code>gpr.versionName = "2.0.1"</code>
     * @param value whether true or false.
     */
    @Override
    public void setVersionName(String value) {
        this.versionName = value;
    }

    /**
     * Package Type
     * @return the current value of <code>packageType</code>.
     */
    @Override
    public String getPackageType() {
        return this.packageType;
    }

    /**
     * Token Properties (Personal Access Token)
     * @return the absolute path to the <code>token.properties</code> file.
     */
    @Override
    public String getTokenProperties() {
        return this.tokenProperties;
    }

    /**
     * HTTP logging
     * @return the current value of <code>logHttp</code>.
     */
    @Override
    public Boolean getLogHttp() {
        return this.logHttp;
    }

    @Override
    public Boolean getListPackagesAfterPublish() {
        return this.listPackagesAfterPublish;
    }

    @Override
    public Boolean getDeleteOnConflict() {
        return this.deleteOnConflict;
    }

    @Override
    public Boolean getDeleteLastVersion() {
        return this.deleteLastVersion;
    }

    /**
     * Package Group
     * @return the current value of <code>groupId</code>.
     */
    @Override
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * Package Name
     * @return the current value of <code>packageName</code>.
     */
    @Override
    public String getPackageName() {
        return this.packageName;
    }

    /**
     * Version Name
     * @return the current value of <code>versionName</code>.
     */
    @Override
    public String getVersionName() {
        return this.versionName;
    }
}
