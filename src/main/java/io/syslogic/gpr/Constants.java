package io.syslogic.gpr;

import org.jetbrains.annotations.NotNull;

/**
 * GitHub API Endpoints
 * @author Martin Zeitler
 */
public class Constants {

    @NotNull public static final String GITHUB_API_VERSION = "2022-11-28";
    /** API date-format (used by GSON). */
    @NotNull public static final String GITHUB_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /** API Base URL */
    @NotNull public static final String GITHUB_API_BASE_URL = "https://api.github.com/";

    /** URI Packages Index */
    @NotNull public static final String USER_PACKAGES = Constants.GITHUB_API_BASE_URL + "user/packages";

    @NotNull
    public static String getPackageVersionsUri(@NotNull String packageType, @NotNull String packageName) {
        return Constants.GITHUB_API_BASE_URL + "user/packages/" + packageType + "/" + packageName + "/versions";
    }

    /**
     * URI: Package by userName and versionName.
     * @return <code>users/{USERNAME}/packages/{PACKAGE_TYPE}/{PACKAGE_NAME}/versions/{VERSION_NAME}</code>
     */
    @NotNull
    public static String getPackageVersionUri(@NotNull String userName, @NotNull String packageType, @NotNull String packageName, @NotNull String versionName) {
        return Constants.GITHUB_API_BASE_URL + "users/" + userName +"/packages/" + packageType + "/" + packageName + "/versions/" + versionName;
    }

    /**
     * URI: Package by numeric versionId.
     * @return <code>user/packages/{PACKAGE_TYPE}/{PACKAGE_NAME}/versions/{PACKAGE_VERSION_ID}</code>
     */
    @NotNull
    public static String getPackageUri(@NotNull String packageType, @NotNull String packageName, @NotNull Long versionId) {
        return Constants.GITHUB_API_BASE_URL + "user/packages/" + packageType + "/" + packageName + "/versions/" + versionId;
    }

    /**
     * URI: Package delete.
     * @return <code>user/packages/{PACKAGE_TYPE}/{PACKAGE_NAME}</code>
     */
    @NotNull
    public static String getPackageDeleteUri(@NotNull String packageType, @NotNull String packageName) {
        return Constants.GITHUB_API_BASE_URL + "user/packages/" + packageType + "/" + packageName ;
    }

    @NotNull public static final String PUBLISH_TASK_PATTERN = "^publish(?!AllPublications).*(?<!MavenLocal|publish)$";
    /** Task name: ID. */
    @NotNull public static final String VERSION_ID_TASK      = "gprVersionId";

    /** Task name: GET. */
    @NotNull public static final String PACKAGE_GET_TASK     = "gprPackageGet_";

    /** Task name: DELETED. */
    @NotNull public static final String PACKAGE_DELETE_TASK  = "gprPackageDel_";

    /** Task name: LIST. */
    @NotNull public static final String PACKAGE_LIST_TASK    = "gprPackageList";

    /** Task name: RESTORE. */
    @NotNull public static final String PACKAGE_RESTORE_TASK = "gprPackageRestore";
}
