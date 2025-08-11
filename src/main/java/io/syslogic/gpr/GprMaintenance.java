package io.syslogic.gpr;

import java.util.List;

/**
 * Public API for Gradle build scripts.
 *
 * @author Martin Zeitler
 */
public interface GprMaintenance {

    /** GPR package-types. */
    List<String> packageTypes = List.of("npm", "maven", "rubygems", "docker", "nuget", "container");

    /**
     * Define the path to the PAT properties file.
     * <code>gpr {tokenProperties = "token.properties"}</code>
     * @return the absolute path to the <code>token.properties</code> file.
     */
    String getTokenProperties();

    /**
     * Package Group
     * <code>gpr { groupId = null }</code>
     * @return the package group ID.
     */
    String getGroupId();

    /**
     * Package Name
     * <code>gpr { packageName = null }</code>
     * @return the package name.
     */
    String getPackageName();

    String getVersionName();

    /**
     * Package Type
     * <code>gpr { packageType = "maven" }</code>
     * @return the package type.
     */
    String getPackageType();

    /**
     * HTTP Request Logging
     * <code>gpr { logHttp = true }</code>
     * @return logHttp true/false.
     */
    Boolean getLogHttp();

    /**
     * List Packages After Publish
     * <code>gpr { listPackagesAfterPublish = true }</code>
     * @return listPackagesAfterPublish true/false.
     */
    Boolean getListPackagesAfterPublish();

    /**
     * Delete On Conflict
     * <code>gpr { deleteOnConflict = true }</code>
     * @return deleteOnConflict true/false.
     */
    Boolean getDeleteOnConflict();

    /**
     * Delete Last Version
     * <code>gpr { deleteLastVersion = true }</code>
     * @return deleteLastVersion true/false.
     */
    Boolean getDeleteLastVersion();

    /**
     * Define the path to the API client configuration file.
     * <code>gpr {tokenProperties = ""}</code>
     * @param value the absolute path to the <code>token.properties</code> file.
     */
    void setTokenProperties(String value);

    /**
     * Package Type.
     * <code>gpr {packageType = "maven"}</code>
     * @param value the type of package.
     */
    void setPackageType(String value);

    /**
     * Package GroupId.
     * <code>gpr {groudId = "io.syslogic"}</code>
     * @param value the group of package.
     */
    void setGroupId(String value);

    /**
     * Package Name.
     * <code>gpr {packageName = ""}</code>
     * @param value the name of package.
     */
    void setPackageName(String value);

    /**
     * Package Version Name.
     * <code>gpr {versionName = ""}</code>
     * @param value the version of package.
     */
    void setVersionName(String value);

    /**
     * Set HTTP Request Logging.
     * @param value true/false.
     */
    void setLogHttp(Boolean value);

    /**
     * Set List Packages After Publish.
     * @param value true/false.
     */
    void setListPackagesAfterPublish(Boolean value);

    /**
     * Set Delete On Conflict.
     * @param value true/false.
     */
    void setDeleteOnConflict(Boolean value);

    /**
     * Set Delete Last Version.
     * @param value true/false.
     */
    void setDeleteLastVersion(Boolean value);
}
