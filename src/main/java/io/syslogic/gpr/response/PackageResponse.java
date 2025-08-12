package io.syslogic.gpr.response;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

import io.syslogic.gpr.model.Package;
import io.syslogic.gpr.model.Version;

/**
 * Model: Package Response
 * @author Martin Zeitler
 */
public class PackageResponse implements Serializable {

    /** Note: Node `items` is being wrapped, before the response JSON is passed to GSON. */
    @SerializedName("items")
    private ArrayList<io.syslogic.gpr.model.Package> mItems = new ArrayList<>();

    /** This holds the value of the next page URL - if any. */
    private String nextPage = null;

    /**
     * Getter for the response items.
     * @return an array-list of {@link io.syslogic.gpr.model.Package}.
     */
    public ArrayList<Package> getItems() {
        return this.mItems;
    }

    /**
     * Getter for the the next page URL.
     * @return {@link String} next page URL,
     */
    @Nullable
    public String getNextPage() {
        return this.nextPage;
    }
    /**
     * Getter for the the next page URL.
     * @param nextPage {@link String} next page URL,
     */
    public void setNextPage(@Nullable String nextPage) {
        this.nextPage = nextPage;
    }
}
