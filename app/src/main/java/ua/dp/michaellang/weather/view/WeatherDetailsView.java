package ua.dp.michaellang.weather.view;

import ua.dp.michaellang.weather.network.model.Forecast.DailyForecast;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecastResponse;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;

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

    void onFavoriteActionSuccess();
    void onAddToFavoriteFail();
    void onIsFavoriteChecked(boolean result);
}
