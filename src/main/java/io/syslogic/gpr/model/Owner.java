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

    /** Login name */
    @SerializedName("login")
    private String login;

    /** ID */
    @SerializedName("id")
    private long id;

    /** NodeID */
    @SerializedName("node_id")
    private String nodeId;

    /** Avatar URL */
    @SerializedName("avatar_url")
    private String avatarUrl;

    /** Gravatar ID */
    @SerializedName("gravatar_id")
    private String gravatarId;

    /** User URL */
    @SerializedName("url")
    private String url;

    /** User HTML URL */
    @SerializedName("html_url")
    private String htmlUrl;

    /** User followers URL */
    @SerializedName("followers_url")
    private String followersUrl;

    /** User following URL */
    @SerializedName("following_url")
    private String followingUrl;

    /** User gists URL */
    @SerializedName("gists_url")
    private String gistsUrl;

    /** User starred URL */
    @SerializedName("starred_url")
    private String starredUrl;

    /** User Subscriptions URL */
    @SerializedName("subscriptions_url")
    private String subscriptionsUrl;

    /** User organizations URL */
    @SerializedName("organizations_url")
    private String organizationsUrl;

    /** User repositories URL */
    @SerializedName("repos_url")
    private String reposUrl;

    /** User events URL */
    @SerializedName("events_url")
    private String eventsUrl;

    /** User received events URL */
    @SerializedName("received_events_url")
    private String receiverEventsUrl;

    /** The user's type. */
    @SerializedName("type")
    private String type;

    /** If the user is site admin. */
    @SerializedName("site_admin")
    private Boolean siteAdmin;

    /** The user's name. */
    @SerializedName("name")
    private String name;

    /** The user's company. */
    @SerializedName("company")
    private String company;

    /** The user's blog. */
    @SerializedName("blog")
    private String blog;

    /** The user's location. */
    @SerializedName("location")
    private String location;

    /** The user's email. */
    @SerializedName("email")
    private String email;

    /** If the user is hire-able. */
    @SerializedName("hireable")
    private Boolean hireable;

    /** The user's bio. */
    @SerializedName("bio")
    private String bio;

    /** The number of public repositories. */
    @SerializedName("public_repos")
    private Integer publicRepos;

    /** The number of public gists. */
    @SerializedName("public_gists")
    private Integer publicGists;

    /** The number of follower accounts. */
    @SerializedName("followers")
    private Integer followers;

    /** The number of following accounts. */
    @SerializedName("following")
    private Integer following;

    /** Date account created at. */
    @SerializedName("created_at")
    private Date createdAt;

    /** Date account last updated at. */
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
     * Setter for user name.
     * @param value the name of the user.
     */
    public void setName(@NotNull String value) {
        this.name = value;
    }

    /** {@inheritDoc} */
    @NotNull
    public String toString() {
        return this.login;
    }
}
