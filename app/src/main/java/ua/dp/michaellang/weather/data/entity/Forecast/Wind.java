package ua.dp.michaellang.weather.data.entity.Forecast;

/**
 * Date: 16.09.2017
 *
 * @author Michael Lang
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import ua.dp.michaellang.weather.data.entity.Metric;

public class Wind {

    @SerializedName("Speed")
    @Expose
    private Metric speed;
    @SerializedName("Direction")
    @Expose
    private Direction direction;

    public Metric getSpeed() {
        return speed;
    }

    public void setSpeed(Metric speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
