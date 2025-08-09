package io.syslogic.gpr.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: Package
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class Package implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("visibility")
    private String visibility;

    @SerializedName("url")
    private String url;

    @SerializedName("package_type")
    private String packageType;

    @SerializedName("version_count")
    private int versionCount;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("updated_at")
    private Date updatedAt;

    @SerializedName("repository")
    private Repository repository;

    @SerializedName("owner")
    private Owner owner;

    public long getId() {
        return this.id;
    }
    public int getVersionCount() {
        return this.versionCount;
    }
    public String getName() {
        return this.name;
    }
    public String getPackageType() {
        return this.packageType;
    }
    public String getVisibility() {
        return this.visibility;
    }
    public String getUrl() {
        return this.url;
    }
    public Repository getRepository() {
        return this.repository;
    }
    public Owner getOwner() {
        return this.owner;
    }
    @Override
    public String toString(){
        return "Package {"+
            "id=" + this.id + ", " +
            "name=\"" + this.name + "\", " +
            "packageType=\"" + this.packageType + "\", " +
            "visibility=\"" + this.visibility + "\", " +
            "versionCount=" + this.versionCount + " " +
            // "url=\"" + this.url + "\"" +
        "}";
    }
}
