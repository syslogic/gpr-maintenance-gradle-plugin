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

    /** Link to Documentation URL. */
    @SerializedName("documentation_url")
    private String documentationUrl;

    /** Status Code. */
    @SerializedName("status")
    private Integer status;

    public String getMessage() {
        return this.message;
    }
    public String getDocumentationUrl() {
        return this.documentationUrl;
    }
    public Integer getStatus() {
        return this.status;
    }

    @Override
    public String toString(){
        return "ErrorMessage {"+
            "status=" + this.getStatus() + ", " +
            "message=\"" + this.getMessage() + "\", " +
            "documentationUrl=\"" + this.getDocumentationUrl() + "\"" +
        "}";
    }
}
