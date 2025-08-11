package io.syslogic.gpr.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model: Package Version MetaData
 * @author Martin Zeitler
 */
@SuppressWarnings("unused")
public class MetaData implements Serializable {

    @SerializedName("package_type")
    private String packageType;

    public String getPackageType() {
        return this.packageType;
    }

    @Override
    public String toString() {
        return "MetaData {"+
            "packageType=\"" + this.packageType + "\"" +
        "}";
    }
}
