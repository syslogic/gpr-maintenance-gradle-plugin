package io.syslogic.gpr.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: Version
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class Version implements Serializable {

    /** ID */
    @SerializedName("id")
    private Long id;

    /** Name */
    @SerializedName("name")
    private String name;

    /** License */
    @SerializedName("license")
    private String license;

    /** URL */
    @SerializedName("url")
    private String url;

    /** HTML URL */
    @SerializedName("html_url")
    private String htmlUrl;

    /** Package HTML URL */
    @SerializedName("package_html_url")
    private String packageHtmlUrl;

    /** Date created at */
    @SerializedName("created_at")
    private Date createdAt;

    /** Date last updated at */
    @SerializedName("updated_at")
    private Date updatedAt;

    /** Meta-data */
    @SerializedName("metadata")
    private MetaData metadata;

    /**
     * Get ID.
     * @return version ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get Name.
     * @return version name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get License.
     * @return license
     */
    public String getLicense() {
        return this.license;
    }

    /**
     * Get URL.
     * @return URL */
    public String getUrl() {
        return this.url;
    }

    /**
     * Get HTML URL.
     * @return HTML URL */
    public String getHtmlUrl() {
        return this.htmlUrl;
    }

    /**
     * Get package HTML URL.
     * @return package HTML URL
     */
    public String getPackageHtmlUrl() {
        return this.packageHtmlUrl;
    }

    /**
     * Get version created at.
     * @return version created at
     */
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Get version last updated at.
     * @return version last updated at
     */
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Get version meta-data.
     * @return version meta-data
     */
    public MetaData getMetadata() {
        return this.metadata;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Version {"+
            "id=" + this.id + ", " +
            "name=\"" + this.name + "\", " +
            "url=\"" + this.htmlUrl + "\"" +
        "}";
    }
}
