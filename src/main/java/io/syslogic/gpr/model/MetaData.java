package io.syslogic.gpr.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model: Package Version MetaData
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class MetaData implements Serializable {

    /** The package-type. */
    @SerializedName("package_type")
    private String packageType;

    /**
     * Get the package-type.
     * @return the package-type.
     */
    public String getPackageType() {
        return this.packageType;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "MetaData {"+
            "packageType=\"" + this.packageType + "\"" +
        "}";
    }
}
