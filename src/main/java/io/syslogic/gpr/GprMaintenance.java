package io.syslogic.gpr;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Public API for Gradle build scripts.
 *
 * @author Martin Zeitler
 */

@SuppressWarnings("unused")
public interface GprMaintenance {

    /** GPR package-types. */
    List<String> packageTypes = List.of("npm", "maven", "rubygems", "docker", "nuget", "container");

    /**
     * Package Type
     * <code>gpr { packageType = "maven" }</code>
     * @return the package type.
     */
    String getPackageType();

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

    /**
     * Package-Listing Page Size.
     * <code>gpr { pageSize = 30 }</code>
     * @return integer value.
     */
    Integer getPageSize();

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
     * Package Type.
     * <code>gpr {packageType = "maven"}</code>
     * @param value the type of package.
     */
    void setPackageType(String value);

    /**
     * Define the path to the API client configuration file.
     * <code>gpr {tokenProperties = ""}</code>
     * @param value the absolute path to the <code>token.properties</code> file.
     * @throws IllegalArgumentException when the file not exists or has a bad format.
     */
    void setTokenProperties(String value) throws IllegalArgumentException;

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
     * Package-Listing Page Size.
     * @param value integer, max value 100.
     */
    void setPageSize(Integer value);

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
