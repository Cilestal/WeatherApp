package ua.dp.michaellang.weather.data.entity.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class TimeZone {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("GmtOffset")
    @Expose
    private double gmtOffset;
    @SerializedName("IsDaylightSaving")
    @Expose
    private boolean isDaylightSaving;
    @SerializedName("NextOffsetChange")
    @Expose
    private Object nextOffsetChange;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(double gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public boolean isIsDaylightSaving() {
        return isDaylightSaving;
    }

    public void setIsDaylightSaving(boolean isDaylightSaving) {
        this.isDaylightSaving = isDaylightSaving;
    }

    public Object getNextOffsetChange() {
        return nextOffsetChange;
    }

    public void setNextOffsetChange(Object nextOffsetChange) {
        this.nextOffsetChange = nextOffsetChange;
    }

}
