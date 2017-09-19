package ua.dp.michaellang.weather.network.model.Location;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Details {

    @SerializedName("Key")
    @Expose
    private String key;
    @SerializedName("StationCode")
    @Expose
    private String stationCode;
    @SerializedName("StationGmtOffset")
    @Expose
    private double stationGmtOffset;
    @SerializedName("BandMap")
    @Expose
    private String bandMap;
    @SerializedName("Climo")
    @Expose
    private String climo;
    @SerializedName("LocalRadar")
    @Expose
    private String localRadar;
    @SerializedName("MediaRegion")
    @Expose
    private Object mediaRegion;
    @SerializedName("Metar")
    @Expose
    private String metar;
    @SerializedName("NXMetro")
    @Expose
    private String nXMetro;
    @SerializedName("NXState")
    @Expose
    private String nXState;
    @SerializedName("Population")
    @Expose
    private int population;
    @SerializedName("PrimaryWarningCountyCode")
    @Expose
    private String primaryWarningCountyCode;
    @SerializedName("PrimaryWarningZoneCode")
    @Expose
    private String primaryWarningZoneCode;
    @SerializedName("Satellite")
    @Expose
    private String satellite;
    @SerializedName("Synoptic")
    @Expose
    private String synoptic;
    @SerializedName("MarineStation")
    @Expose
    private String marineStation;
    @SerializedName("MarineStationGMTOffset")
    @Expose
    private Object marineStationGMTOffset;
    @SerializedName("VideoCode")
    @Expose
    private String videoCode;
    @SerializedName("PartnerID")
    @Expose
    private Object partnerID;
    @SerializedName("Sources")
    @Expose
    private List<Source> sources = null;
    @SerializedName("CanonicalPostalCode")
    @Expose
    private String canonicalPostalCode;
    @SerializedName("CanonicalLocationKey")
    @Expose
    private String canonicalLocationKey;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public double getStationGmtOffset() {
        return stationGmtOffset;
    }

    public void setStationGmtOffset(double stationGmtOffset) {
        this.stationGmtOffset = stationGmtOffset;
    }

    public String getBandMap() {
        return bandMap;
    }

    public void setBandMap(String bandMap) {
        this.bandMap = bandMap;
    }

    public String getClimo() {
        return climo;
    }

    public void setClimo(String climo) {
        this.climo = climo;
    }

    public String getLocalRadar() {
        return localRadar;
    }

    public void setLocalRadar(String localRadar) {
        this.localRadar = localRadar;
    }

    public Object getMediaRegion() {
        return mediaRegion;
    }

    public void setMediaRegion(Object mediaRegion) {
        this.mediaRegion = mediaRegion;
    }

    public String getMetar() {
        return metar;
    }

    public void setMetar(String metar) {
        this.metar = metar;
    }

    public String getNXMetro() {
        return nXMetro;
    }

    public void setNXMetro(String nXMetro) {
        this.nXMetro = nXMetro;
    }

    public String getNXState() {
        return nXState;
    }

    public void setNXState(String nXState) {
        this.nXState = nXState;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getPrimaryWarningCountyCode() {
        return primaryWarningCountyCode;
    }

    public void setPrimaryWarningCountyCode(String primaryWarningCountyCode) {
        this.primaryWarningCountyCode = primaryWarningCountyCode;
    }

    public String getPrimaryWarningZoneCode() {
        return primaryWarningZoneCode;
    }

    public void setPrimaryWarningZoneCode(String primaryWarningZoneCode) {
        this.primaryWarningZoneCode = primaryWarningZoneCode;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getSynoptic() {
        return synoptic;
    }

    public void setSynoptic(String synoptic) {
        this.synoptic = synoptic;
    }

    public String getMarineStation() {
        return marineStation;
    }

    public void setMarineStation(String marineStation) {
        this.marineStation = marineStation;
    }

    public Object getMarineStationGMTOffset() {
        return marineStationGMTOffset;
    }

    public void setMarineStationGMTOffset(Object marineStationGMTOffset) {
        this.marineStationGMTOffset = marineStationGMTOffset;
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode;
    }

    public Object getPartnerID() {
        return partnerID;
    }

    public void setPartnerID(Object partnerID) {
        this.partnerID = partnerID;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public String getCanonicalPostalCode() {
        return canonicalPostalCode;
    }

    public void setCanonicalPostalCode(String canonicalPostalCode) {
        this.canonicalPostalCode = canonicalPostalCode;
    }

    public String getCanonicalLocationKey() {
        return canonicalLocationKey;
    }

    public void setCanonicalLocationKey(String canonicalLocationKey) {
        this.canonicalLocationKey = canonicalLocationKey;
    }

}
