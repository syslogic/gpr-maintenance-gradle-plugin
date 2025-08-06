package io.syslogic.gpr.model;


import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Model: Repository
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class Repository implements Serializable {

    /** ID */
    @SerializedName("id")
    private Long id;

    /** NodeId */
    @SerializedName("node_id")
    private String nodeId;

    /** Name */
    @SerializedName("name")
    private String name;

    /** Full Name */
    @SerializedName("full_name")
    private String fullName;

    /** File-Name */
    private String fileName;

    /** Url */
    @SerializedName("url")
    private String url;

    /** HtmlUrl */
    @SerializedName("html_url")
    private String htmlUrl;

    /** Owner */
    @SerializedName("owner")
    private Owner owner;

    /** OwnerId */
    private Long ownerId = 0L;

    /** LicenseId */
    @SerializedName("license_id")
    private Long licenseId = 0L;

    /** Forks */
    @SerializedName("forks_count")
    private Long forkCount = 0L;

    /** Stargazers */
    @SerializedName("stargazers_count")
    private Long stargazerCount = 0L;

    /** Watchers */
    @SerializedName("watchers_count")
    private Long watcherCount = 0L;

    /** Subscribers */
    @SerializedName("subscribers_count")
    private Long subscriberCount = 0L;

    /** Network */
    @SerializedName("network_count")
    private Long networkCount = 0L;

    /** Topics */
    @SerializedName("topics")
    private String[] topics;

    /** Constructor */
    public Repository() {}

    /**
     * Constructor
     * @param id the id of the repository.
     * @param name the title of the repository.
     * @param url the URL of the repository.
     */

    public Repository(@NotNull Long id, @NotNull String name, @NotNull String url) {
        this.setId(id);
        this.setName(name);
        this.setUrl(url);
    }

    /**
     * Setter for id.
     * @param value the id of the repository.
     */
    public void setId(@NotNull Long value) {
        this.id = value;
    }

    /**
     * Setter for nodeId.
     * @param value the nodeId of the repository.
     */
    public void setNodeId(@NotNull String value) {
        this.nodeId = value;
    }

    /**
     * Setter for name.
     * @param value the name of the repository.
     */
    public void setName(@NotNull String value) {
        this.name = value;
    }

    /**
     * Setter for fullName.
     * @param value the fullName of the repository.
     */
    public void setFullName(@NotNull  String value) {
        this.fullName = value;
    }

    /**
     * Setter for fileName.
     * @param value the file-name of the repository.
     */
    public void setFileName(@NotNull  String value) {
        this.fileName = value;
    }

    /**
     * Setter for url.
     * @param value the url of the repository.
     */
    public void setUrl(@NotNull String value) {
        this.url = value;
    }

    /**
     * Setter for htmlUrl.
     * @param value the htmlUrl of the repository.
     */
    public void setHtmlUrl(@NotNull String value) {
        this.htmlUrl = value;
    }

    /**
     * Setter for owner.
     * @param value the owner of the repository.
     */
    public void setOwner(@NotNull Owner value) {
        this.owner = value;
    }

    /**
     * Setter for ownerId.
     * @param value the ownerId of the repository.
     */
    public void setOwnerId(@NotNull Long value) {
        this.ownerId = value;
    }


    /**
     * Setter for licenseId.
     * @param value the licenseId of the repository.
     */
    public void setLicenseId(@NotNull Long value) {
        this.licenseId = value;
    }

    /**
     * Setter for forkCount.
     * @param value the fork-count of the repository.
     */
    public void setForkCount(@NotNull Long value) {
        this.forkCount = value;
    }

    /**
     * Setter for stargazerCount.
     * @param value the stargazer-count of the repository.
     */
    public void setStargazerCount(@NotNull Long value) {
        this.stargazerCount = value;
    }

    /**
     * Setter for watcherCount.
     * @param value the watcher-count of the repository.
     */
    public void setWatcherCount(@NotNull Long value) {
        this.watcherCount = value;
    }

    /**
     * Setter for subscriberCount.
     * @param value the subscriber-count of the repository.
     */
    public void setSubscriberCount(@NotNull Long value) {
        this.subscriberCount = value;
    }

    /**
     * Setter for networkCount.
     * @param value the network-count of the repository.
     */
    public void setNetworkCount(@NotNull Long value) {
        this.networkCount = value;
    }

    /**
     * Setter for topics.
     * @param value the topics of the repository.
     */
    public void setTopics(@NotNull String[] value) {
        this.topics = value;
    }

    /**
     * Getter for id.
     * @return the ID of the repository.
     */
    @NotNull
    public Long getId() {
        return this.id;
    }

    /**
     * Getter for nodeId.
     * @return the nodeId of the repository.
     */
    @NotNull
    public String getNodeId() {
        return this.nodeId;
    }

    /**
     * Getter for name.
     * @return the name of the repository.
     */
    @NotNull
    public String getName() {
        return this.name;
    }

    /**
     * Getter for fileName.
     * @return the file-name of the repository.
     */
    @NotNull
    public String getFileName() {
        return this.fileName;
    }

    /**
     * Getter for fullName.
     * @return the full name of the repository.
     */
    @NotNull
    public String getFullName() {
        return this.fullName;
    }

    /**
     * Getter for url.
     * @return the url of the repository.
     */
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Getter for htmlUrl.
     * @return the htmlUrl of the repository.
     */
    @NotNull
    public String getHtmlUrl() {
        return this.htmlUrl;
    }

    /**
     * Getter for owner.
     * @return the owner of the repository.
     */
    @NotNull
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * Getter for ownerId.
     * @return the ownerId of the repository.
     */
    @NotNull
    public Long getOwnerId() {
        return this.ownerId;
    }

    /**
     * Getter for licenseId.
     * @return the licenseId of the repository.
     */
    @NotNull
    public Long getLicenseId() {
        return this.licenseId;
    }

    /**
     * Getter for forkCount.
     * @return the fork-count of the repository.
     */
    @NotNull
    public Long getForkCount() {
        return this.forkCount;
    }

    /**
     * Getter for forkCount.
     * @return the fork-count of the repository.
     */
    @NotNull
    public Long getStargazerCount() {
        return this.stargazerCount;
    }

    /**
     * Getter for forkCount.
     * @return the fork-count of the repository.
     */
    @NotNull
    public Long getWatcherCount() {
        return this.watcherCount;
    }

    /**
     * Getter for forkCount.
     * @return the fork-count of the repository.
     */
    @NotNull
    public Long getSubscriberCount() {
        return this.subscriberCount;
    }

    /**
     * Getter for forkCount.
     * @return the fork-count of the repository.
     */
    @NotNull
    public Long getNetworkCount() {
        return this.networkCount;
    }

    /**
     * Getter for topics.
     * @return the topics of the repository.
     */
    @NotNull
    public String[] getTopics() {
        return this.topics;
    }
}
