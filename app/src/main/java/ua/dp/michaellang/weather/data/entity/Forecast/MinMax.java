package ua.dp.michaellang.weather.data.entity.Forecast;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ua.dp.michaellang.weather.data.entity.Metric;

public class MinMax {

    @SerializedName("Minimum")
    @Expose
    private Metric minimum;
    @SerializedName("Maximum")
    @Expose
    private Metric maximum;

    public Metric getMinimum() {
        return minimum;
    }

    public void setMinimum(Metric minimum) {
        this.minimum = minimum;
    }

    public Metric getMaximum() {
        return maximum;
    }

    public void setMaximum(Metric maximum) {
        this.maximum = maximum;
    }

}
