package ua.dp.michaellang.weather.presentation.view;

import ua.dp.michaellang.weather.data.entity.Forecast.DailyForecast;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public interface WeatherDetailsView extends BaseView {
    void onDailyWeatherLoaded(ArrayList<DailyForecast> data);
    void onHourlyWeatherLoaded(List<HourlyForecast> data);

    void onLoadComplete();

    void onAddToFavoriteSuccess();
    void onFavoriteActionFail();
    void onIsFavoriteChecked(boolean result);

    void onRemoveFromFavoriteSuccess();
}
