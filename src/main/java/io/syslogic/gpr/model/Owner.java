package io.syslogic.gpr.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: Owner
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class Owner implements Serializable {

    @SerializedName("login")
    private String login;

    @SerializedName("id")
    private long id;

    @SerializedName("node_id")
    private String nodeId;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("gravatar_id")
    private String gravatarId;

    @SerializedName("url")
    private String url;

    @SerializedName("html_url")
    private String htmlUrl;

    @SerializedName("followers_url")
    private String followersUrl;

    @SerializedName("following_url")
    private String followingUrl;

    @SerializedName("gists_url")
    private String gistsUrl;

    @SerializedName("starred_url")
    private String starredUrl;

    @SerializedName("subscriptions_url")
    private String subscriptionsUrl;

    @SerializedName("organizations_url")
    private String organizationsUrl;

    @SerializedName("repos_url")
    private String reposUrl;

    @SerializedName("events_url")
    private String eventsUrl;

    @SerializedName("received_events_url")
    private String receiverEventsUrl;

    @SerializedName("type")
    private String type;

    @SerializedName("site_admin")
    private Boolean siteAdmin;

    @SerializedName("name")
    private String name;

    @SerializedName("company")
    private String company;

    @SerializedName("blog")
    private String blog;

    @SerializedName("location")
    private String location;

    @SerializedName("email")
    private String email;

    @SerializedName("hireable")
    private Boolean hireable;

    @SerializedName("bio")
    private String bio;

    @SerializedName("public_repos")
    private Integer publicRepos;

    @SerializedName("public_gists")
    private Integer publicGists;

    @SerializedName("followers")
    private Integer followers;

    @SerializedName("following")
    private Integer following;

    @SerializedName("created_at")
    private Date createdAt;

    @SerializedName("updated_at")
    private Date updatedAt;

    /**
     * Getter for login.
     * @return the login of the user.
     */
    @NotNull
    public String getLogin() {
        return this.login;
    }

    /**
     * Getter for id.
     * @return the id of the user.
     */
    @NotNull public Long getId() {
        return this.id;
    }

    /**
     * Getter for nodeId.
     * @return the nodeId of the user.
     */
    @NotNull public String getNodeId() {
        return this.nodeId;
    }

    /**
     * Getter for avatarUrl.
     * @return the avatarUrl of the user.
     */
    @NotNull public String getAvatarUrl() {
        return this.avatarUrl;
    }

    /**
     * Getter for gravatarId.
     * @return the gravatarId of the user.
     */
    @NotNull public String getGravatarId() {
        return this.gravatarId;
    }

    /**
     * Getter for url.
     * @return the url of the user.
     */
    @NotNull public String getUrl() {
        return this.url;
    }

    /**
     * Getter for htmlUrl.
     * @return the htmlUrl of the user.
     */
    @NotNull public String getHtmlUrl() {
        return this.htmlUrl;
    }

    /**
     * Getter for reposUrl.
     * @return the reposUrl of the user.
     */
    @NotNull public String getReposUrl() {
        return this.reposUrl;
    }

    /**
     * Getter for type.
     * @return the type of the user.
     */
    @NotNull public String getType() {
        return this.type;
    }

    /**
     * Getter for siteAdmin.
     * @return is siteAdmin.
     */
    @NotNull public Boolean getSiteAdmin() {
        return this.siteAdmin;
    }

    /**
     * Getter for name.
     * @return the name of the user.
     */
    @NotNull public String getName() {
        return this.name;
    }

    /**
     * Setter for login.
     * @param value the login of the user.
     */
    public void setLogin(@NotNull String value) {
        this.login = value;
    }

    /**
     * Setter for id.
     * @param value the id of the user.
     */
    public void setId(@NotNull Long value) {
        this.id = value;
    }

    /**
     * Setter for nodeId.
     * @param value the nodeId of the user.
     */
    public void setNodeId(@NotNull String value) {
        this.nodeId = value;
    }

    /**
     * Setter for avatarUrl.
     * @param value the avatarUrl of the user.
     */
    public void setAvatarUrl(@NotNull String value) {
        this.avatarUrl = value;
    }

    /**
     * Setter for gravatarId.
     * @param value the gravatarId of the user.
     */
    public void setGravatarId(@NotNull String value) {
        this.gravatarId = value;
    }

    /**
     * Setter for url.
     * @param value the url of the user.
     */
    public void setUrl(@NotNull String value) {
        this.url = value;
    }

    /**
     * Setter for htmlUrl.
     * @param value the htmlUrl of the user.
     */
    public void setHtmlUrl(@NotNull String value) {
        this.htmlUrl = value;
    }

    /**
     * Setter for reposUrl.
     * @param value the reposUrl of the user.
     */
    public void setReposUrl(@NotNull String value) {
        this.reposUrl = value;
    }

    /**
     * Setter for type.
     * @param value the type of the user.
     */
    public void setType(@NotNull String value) {
        this.type = value;
    }

    /**
     * Setter for siteAdmin.
     * @param value if the user is site-admin.
     */
    public void setSiteAdmin(@NotNull Boolean value) {
        this.siteAdmin = value;
    }

    /**
     * Setter for name.
     * @param value the name of the user.
     */
    public void setName(@NotNull String value) {
        this.name = value;
    }

    @NotNull
    public String toString() {
        return this.login;
    }
}
