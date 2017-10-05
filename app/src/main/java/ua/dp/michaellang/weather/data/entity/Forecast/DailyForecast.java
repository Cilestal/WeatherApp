package ua.dp.michaellang.weather.data.entity.Forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ua.dp.michaellang.weather.data.entity.Metric;

import java.util.List;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */

public class DailyForecast {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("EpochDate")
    @Expose
    private long epochDate;
    @SerializedName("Sun")
    @Expose
    private Sun sun;
    @SerializedName("Moon")
    @Expose
    private Moon moon;
    @SerializedName("Temperature")
    @Expose
    private MinMax temperature;
    @SerializedName("RealFeelTemperature")
    @Expose
    private MinMax realFeelTemperature;
    @SerializedName("RealFeelTemperatureShade")
    @Expose
    private MinMax realFeelTemperatureShade;
    @SerializedName("HoursOfSun")
    @Expose
    private double hoursOfSun;
    @SerializedName("DegreeDaySummary")
    @Expose
    private Metric degreeDaySummary;
    @SerializedName("AirAndPollen")
    @Expose
    private List<AirAndPollen> airAndPollen = null;
    @SerializedName("Day")
    @Expose
    private DayNight day;
    @SerializedName("Night")
    @Expose
    private DayNight night;
    @SerializedName("Sources")
    @Expose
    private List<String> sources = null;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(long epochDate) {
        this.epochDate = epochDate;
    }

    public Sun getSun() {
        return sun;
    }

    public void setSun(Sun sun) {
        this.sun = sun;
    }

    public Moon getMoon() {
        return moon;
    }

    public void setMoon(Moon moon) {
        this.moon = moon;
    }

    public MinMax getTemperature() {
        return temperature;
    }

    public void setTemperature(MinMax temperature) {
        this.temperature = temperature;
    }

    public MinMax getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(MinMax realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public MinMax getRealFeelTemperatureShade() {
        return realFeelTemperatureShade;
    }

    public void setRealFeelTemperatureShade(MinMax realFeelTemperatureShade) {
        this.realFeelTemperatureShade = realFeelTemperatureShade;
    }

    public double getHoursOfSun() {
        return hoursOfSun;
    }

    public void setHoursOfSun(double hoursOfSun) {
        this.hoursOfSun = hoursOfSun;
    }

    public Metric getDegreeDaySummary() {
        return degreeDaySummary;
    }

    public void setDegreeDaySummary(Metric degreeDaySummary) {
        this.degreeDaySummary = degreeDaySummary;
    }

    public List<AirAndPollen> getAirAndPollen() {
        return airAndPollen;
    }

    public void setAirAndPollen(List<AirAndPollen> airAndPollen) {
        this.airAndPollen = airAndPollen;
    }

    public DayNight getDay() {
        return day;
    }

    public void setDay(DayNight day) {
        this.day = day;
    }

    public DayNight getNight() {
        return night;
    }

    public void setNight(DayNight night) {
        this.night = night;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public String getMobileLink() {
        return mobileLink;
    }

    public void setMobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
