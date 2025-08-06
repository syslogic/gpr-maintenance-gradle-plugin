package io.syslogic.gpr.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.syslogic.gpr.model.Version;

/**
 * Model: Version Response
 * @author Martin Zeitler
 */
public class VersionResponse implements Serializable {

    /** Note: Node `items` is being wrapped, before the response JSON is passed to GSON. */
    @SerializedName("items")
    private final ArrayList<Version> mItems = new ArrayList<>();

    public ArrayList<Version> getItems() {
        return this.mItems;
    }
}
