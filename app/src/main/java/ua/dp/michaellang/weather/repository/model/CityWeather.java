package ua.dp.michaellang.weather.repository.model;

import ua.dp.michaellang.weather.network.model.Forecast.DailyForecast;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;

import java.util.List;

/**
 * Date: 21.09.2017
 *
 * @author Michael Lang
 */
public class CityWeather {
    private List<DailyForecast> mDailyForecasts;
    private List<HourlyForecast> mHourlyForecasts;

    public List<DailyForecast> getDailyForecasts() {
        return mDailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        mDailyForecasts = dailyForecasts;
    }

    public List<HourlyForecast> getHourlyForecasts() {
        return mHourlyForecasts;
    }

    public void setHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
        mHourlyForecasts = hourlyForecasts;
    }
}
