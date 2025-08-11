package io.syslogic.gpr.response;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

import io.syslogic.gpr.model.Package;

/**
 * Model: Package Response
 * @author Martin Zeitler
 */
public class PackageResponse implements Serializable {

    private String nextPage = null;

    /** Note: Node `items` is being wrapped, before the response JSON is passed to GSON. */
    @SerializedName("items")
    private ArrayList<io.syslogic.gpr.model.Package> mItems = new ArrayList<>();

    public ArrayList<Package> getItems() {
        return this.mItems;
    }

    @Nullable
    public String getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(@Nullable String nextPage) {
        this.nextPage = nextPage;
    }

    public void setItems(ArrayList<Package> value) {
        this.mItems = value;
    }
}
