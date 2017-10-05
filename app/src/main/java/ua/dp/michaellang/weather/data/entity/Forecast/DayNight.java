package ua.dp.michaellang.weather.data.entity.Forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ua.dp.michaellang.weather.data.entity.Metric;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */
public class DayNight {

    @SerializedName("Icon")
    @Expose
    private int icon;
    @SerializedName("IconPhrase")
    @Expose
    private String iconPhrase;
    @SerializedName("ShortPhrase")
    @Expose
    private String shortPhrase;
    @SerializedName("LongPhrase")
    @Expose
    private String longPhrase;
    @SerializedName("PrecipitationProbability")
    @Expose
    private int precipitationProbability;
    @SerializedName("ThunderstormProbability")
    @Expose
    private int thunderstormProbability;
    @SerializedName("RainProbability")
    @Expose
    private int rainProbability;
    @SerializedName("SnowProbability")
    @Expose
    private int snowProbability;
    @SerializedName("IceProbability")
    @Expose
    private int iceProbability;
    @SerializedName("Wind")
    @Expose
    private Wind wind;
    @SerializedName("WindGust")
    @Expose
    private Metric windGust;
    @SerializedName("TotalLiquid")
    @Expose
    private Metric totalLiquid;
    @SerializedName("Rain")
    @Expose
    private Metric rain;
    @SerializedName("Snow")
    @Expose
    private Metric snow;
    @SerializedName("Ice")
    @Expose
    private Metric ice;
    @SerializedName("HoursOfPrecipitation")
    @Expose
    private double hoursOfPrecipitation;
    @SerializedName("HoursOfRain")
    @Expose
    private double hoursOfRain;
    @SerializedName("HoursOfSnow")
    @Expose
    private double hoursOfSnow;
    @SerializedName("HoursOfIce")
    @Expose
    private double hoursOfIce;
    @SerializedName("CloudCover")
    @Expose
    private int cloudCover;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public String getShortPhrase() {
        return shortPhrase;
    }

    public void setShortPhrase(String shortPhrase) {
        this.shortPhrase = shortPhrase;
    }

    public String getLongPhrase() {
        return longPhrase;
    }

    public void setLongPhrase(String longPhrase) {
        this.longPhrase = longPhrase;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public int getThunderstormProbability() {
        return thunderstormProbability;
    }

    public void setThunderstormProbability(int thunderstormProbability) {
        this.thunderstormProbability = thunderstormProbability;
    }

    public int getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(int rainProbability) {
        this.rainProbability = rainProbability;
    }

    public int getSnowProbability() {
        return snowProbability;
    }

    public void setSnowProbability(int snowProbability) {
        this.snowProbability = snowProbability;
    }

    public int getIceProbability() {
        return iceProbability;
    }

    public void setIceProbability(int iceProbability) {
        this.iceProbability = iceProbability;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Metric getWindGust() {
        return windGust;
    }

    public void setWindGust(Metric windGust) {
        this.windGust = windGust;
    }

    public Metric getTotalLiquid() {
        return totalLiquid;
    }

    public void setTotalLiquid(Metric totalLiquid) {
        this.totalLiquid = totalLiquid;
    }

    public Metric getRain() {
        return rain;
    }

    public void setRain(Metric rain) {
        this.rain = rain;
    }

    public Metric getSnow() {
        return snow;
    }

    public void setSnow(Metric snow) {
        this.snow = snow;
    }

    public Metric getIce() {
        return ice;
    }

    public void setIce(Metric ice) {
        this.ice = ice;
    }

    public double getHoursOfPrecipitation() {
        return hoursOfPrecipitation;
    }

    public void setHoursOfPrecipitation(double hoursOfPrecipitation) {
        this.hoursOfPrecipitation = hoursOfPrecipitation;
    }

    public double getHoursOfRain() {
        return hoursOfRain;
    }

    public void setHoursOfRain(double hoursOfRain) {
        this.hoursOfRain = hoursOfRain;
    }

    public double getHoursOfSnow() {
        return hoursOfSnow;
    }

    public void setHoursOfSnow(double hoursOfSnow) {
        this.hoursOfSnow = hoursOfSnow;
    }

    public double getHoursOfIce() {
        return hoursOfIce;
    }

    public void setHoursOfIce(double hoursOfIce) {
        this.hoursOfIce = hoursOfIce;
    }

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

}
