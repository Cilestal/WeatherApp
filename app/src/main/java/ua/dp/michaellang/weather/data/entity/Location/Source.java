package ua.dp.michaellang.weather.data.entity.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class Source {

    @SerializedName("DataType")
    @Expose
    private String dataType;
    @SerializedName("Source")
    @Expose
    private String source;
    @SerializedName("SourceId")
    @Expose
    private int sourceId;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

}
