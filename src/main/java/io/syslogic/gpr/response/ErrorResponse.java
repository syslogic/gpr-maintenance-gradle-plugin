package io.syslogic.gpr.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model: Error Response
 * @author Martin Zeitler
 */
public class ErrorResponse implements Serializable {

    /** Error Message. */
    @SerializedName("message")
    private String message;

    /** Link to documentation URL. */
    @SerializedName("documentation_url")
    private String documentationUrl;

    /** Status Code. */
    @SerializedName("status")
    private Integer status;

    /**
     * Get message.
     * @return the message string.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Get documentation URL.
     * @return the documentation URL.
     */
    public String getDocumentationUrl() {
        return this.documentationUrl;
    }

    /**
     * Get status.
     * @return the status code.
     */
    public Integer getStatus() {
        return this.status;
    }

    /** Get Status. */
    @Override
    public String toString(){
        return "ErrorMessage {"+
            "status=" + this.getStatus() + ", " +
            "message=\"" + this.getMessage() + "\", " +
            "documentationUrl=\"" + this.getDocumentationUrl() + "\"" +
        "}";
    }
}
