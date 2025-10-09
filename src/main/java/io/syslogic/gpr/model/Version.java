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
    private long id;

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

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLicense() {
        return this.license;
    }

    public String getUrl() {
        return this.url;
    }

    public String getHtmlUrl() {
        return this.htmlUrl;
    }

    public String getPackageHtmlUrl() {
        return this.packageHtmlUrl;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

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
