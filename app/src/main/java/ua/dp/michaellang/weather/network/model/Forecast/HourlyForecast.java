package ua.dp.michaellang.weather.network.model.Forecast;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ua.dp.michaellang.weather.network.model.Metric;

public class HourlyForecast {

    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("EpochDateTime")
    @Expose
    private long epochDateTime;
    @SerializedName("WeatherIcon")
    @Expose
    private int weatherIcon;
    @SerializedName("IconPhrase")
    @Expose
    private String iconPhrase;
    @SerializedName("IsDaylight")
    @Expose
    private boolean isDaylight;
    @SerializedName("Temperature")
    @Expose
    private Metric temperature;
    @SerializedName("RealFeelTemperature")
    @Expose
    private Metric realFeelTemperature;
    @SerializedName("WetBulbTemperature")
    @Expose
    private Metric wetBulbTemperature;
    @SerializedName("DewPoint")
    @Expose
    private Metric dewPoint;
    @SerializedName("Wind")
    @Expose
    private Wind wind;
    @SerializedName("WindGust")
    @Expose
    private Wind windGust;
    @SerializedName("RelativeHumidity")
    @Expose
    private int relativeHumidity;
    @SerializedName("Visibility")
    @Expose
    private Metric visibility;
    @SerializedName("Ceiling")
    @Expose
    private Metric ceiling;
    @SerializedName("UVIndex")
    @Expose
    private int uVIndex;
    @SerializedName("UVIndexText")
    @Expose
    private String uVIndexText;
    @SerializedName("PrecipitationProbability")
    @Expose
    private int precipitationProbability;
    @SerializedName("RainProbability")
    @Expose
    private int rainProbability;
    @SerializedName("SnowProbability")
    @Expose
    private int snowProbability;
    @SerializedName("IceProbability")
    @Expose
    private int iceProbability;
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
    @SerializedName("CloudCover")
    @Expose
    private int cloudCover;
    @SerializedName("MobileLink")
    @Expose
    private String mobileLink;
    @SerializedName("Link")
    @Expose
    private String link;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public long getEpochDateTime() {
        return epochDateTime;
    }

    public void setEpochDateTime(long epochDateTime) {
        this.epochDateTime = epochDateTime;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(int weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }

    public void setIconPhrase(String iconPhrase) {
        this.iconPhrase = iconPhrase;
    }

    public boolean isIsDaylight() {
        return isDaylight;
    }

    public void setIsDaylight(boolean isDaylight) {
        this.isDaylight = isDaylight;
    }

    public Metric getTemperature() {
        return temperature;
    }

    public void setTemperature(Metric temperature) {
        this.temperature = temperature;
    }

    public Metric getRealFeelTemperature() {
        return realFeelTemperature;
    }

    public void setRealFeelTemperature(Metric realFeelTemperature) {
        this.realFeelTemperature = realFeelTemperature;
    }

    public Metric getWetBulbTemperature() {
        return wetBulbTemperature;
    }

    public void setWetBulbTemperature(Metric wetBulbTemperature) {
        this.wetBulbTemperature = wetBulbTemperature;
    }

    public Metric getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Metric dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWindGust() {
        return windGust;
    }

    public void setWindGust(Wind windGust) {
        this.windGust = windGust;
    }

    public int getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(int relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public Metric getVisibility() {
        return visibility;
    }

    public void setVisibility(Metric visibility) {
        this.visibility = visibility;
    }

    public Metric getCeiling() {
        return ceiling;
    }

    public void setCeiling(Metric ceiling) {
        this.ceiling = ceiling;
    }

    public int getUVIndex() {
        return uVIndex;
    }

    public void setUVIndex(int uVIndex) {
        this.uVIndex = uVIndex;
    }

    public String getUVIndexText() {
        return uVIndexText;
    }

    public void setUVIndexText(String uVIndexText) {
        this.uVIndexText = uVIndexText;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
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

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
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
