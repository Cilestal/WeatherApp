package ua.dp.michaellang.weather.view;

import android.support.annotation.StringRes;
import android.support.v4.util.Pair;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;

import java.util.List;

/**
 * Date: 15.09.2017
 *
 * @author Michael Lang
 */
public interface CityListView extends BaseView {
    void onCityListLoaded(List<City> data);
    void onCityWeatherLoaded(Pair<String, HourlyForecast> data);
    void onError(@StringRes int stringResId);
}
