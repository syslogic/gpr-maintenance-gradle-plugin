package io.syslogic.gpr.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.syslogic.gpr.model.Package;

/**
 * Model: Package Response
 * @author Martin Zeitler
 */
public class PackageResponse implements Serializable {

    /** Note: Node `items` is being wrapped, before the response JSON is passed to GSON. */
    @SerializedName("items")
    private final ArrayList<io.syslogic.gpr.model.Package> mItems = new ArrayList<>();

    public ArrayList<Package> getItems() {
        return this.mItems;
    }
}
