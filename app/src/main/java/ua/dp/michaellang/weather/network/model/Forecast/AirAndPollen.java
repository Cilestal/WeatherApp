package ua.dp.michaellang.weather.network.model.Forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */
public class AirAndPollen {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Value")
    @Expose
    private int value;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("CategoryValue")
    @Expose
    private int categoryValue;
    @SerializedName("Type")
    @Expose
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(int categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
