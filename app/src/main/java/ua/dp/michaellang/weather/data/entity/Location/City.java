package ua.dp.michaellang.weather.data.entity.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.annotations.PrimaryKey;
import ua.dp.michaellang.weather.data.repository.entity.FavoriteCity;

import java.util.List;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class City {
    @PrimaryKey
    @SerializedName("Key")
    @Expose
    private String key;
    @SerializedName("Version")
    @Expose
    private int version;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Rank")
    @Expose
    private int rank;
    @SerializedName("LocalizedName")
    @Expose
    private String localizedName;
    @SerializedName("EnglishName")
    @Expose
    private String englishName;
    @SerializedName("PrimaryPostalCode")
    @Expose
    private String primaryPostalCode;
    @SerializedName("Region")
    @Expose
    private Region region;
    @SerializedName("Country")
    @Expose
    private Region country;
    @SerializedName("AdministrativeArea")
    @Expose
    private AdministrativeArea administrativeArea;
    @SerializedName("TimeZone")
    @Expose
    private TimeZone timeZone;
    @SerializedName("GeoPosition")
    @Expose
    private GeoPosition geoPosition;
    @SerializedName("IsAlias")
    @Expose
    private boolean isAlias;
    @SerializedName("SupplementalAdminAreas")
    @Expose
    private List<SupplementalAdminArea> supplementalAdminAreas = null;
    @SerializedName("DataSets")
    @Expose
    private List<Object> dataSets = null;
    @SerializedName("Details")
    @Expose
    private Details details;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getPrimaryPostalCode() {
        return primaryPostalCode;
    }

    public void setPrimaryPostalCode(String primaryPostalCode) {
        this.primaryPostalCode = primaryPostalCode;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Region getCountry() {
        return country;
    }

    public void setCountry(Region country) {
        this.country = country;
    }

    public AdministrativeArea getAdministrativeArea() {
        return administrativeArea;
    }

    public void setAdministrativeArea(AdministrativeArea administrativeArea) {
        this.administrativeArea = administrativeArea;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }

    public boolean isIsAlias() {
        return isAlias;
    }

    public void setIsAlias(boolean isAlias) {
        this.isAlias = isAlias;
    }

    public List<SupplementalAdminArea> getSupplementalAdminAreas() {
        return supplementalAdminAreas;
    }

    public void setSupplementalAdminAreas(List<SupplementalAdminArea> supplementalAdminAreas) {
        this.supplementalAdminAreas = supplementalAdminAreas;
    }

    public List<Object> getDataSets() {
        return dataSets;
    }

    public void setDataSets(List<Object> dataSets) {
        this.dataSets = dataSets;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public FavoriteCity toFavoriteCity() {
        FavoriteCity fcity = new FavoriteCity();
        fcity.setKey(this.getKey());
        fcity.setName(this.getLocalizedName());

        AdministrativeArea administrativeArea = this.getAdministrativeArea();
        if (administrativeArea != null) {
            fcity.setAreaName(administrativeArea.getLocalizedName());
        }

        Details details = this.getDetails();
        if (details != null) {
            fcity.setPopulation(details.getPopulation());
        }
        return fcity;
    }

}
