package ua.dp.michaellang.weather.data.entity.Forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */
public class Moon {

    @SerializedName("Rise")
    @Expose
    private String rise;
    @SerializedName("EpochRise")
    @Expose
    private int epochRise;
    @SerializedName("Set")
    @Expose
    private String set;
    @SerializedName("EpochSet")
    @Expose
    private int epochSet;
    @SerializedName("Phase")
    @Expose
    private String phase;
    @SerializedName("Age")
    @Expose
    private int age;

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public int getEpochRise() {
        return epochRise;
    }

    public void setEpochRise(int epochRise) {
        this.epochRise = epochRise;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public int getEpochSet() {
        return epochSet;
    }

    public void setEpochSet(int epochSet) {
        this.epochSet = epochSet;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
