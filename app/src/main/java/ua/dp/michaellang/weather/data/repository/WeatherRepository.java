package ua.dp.michaellang.weather.data.repository;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import io.reactivex.Observable;
import ua.dp.michaellang.weather.data.entity.CityWeather;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
public interface WeatherRepository {
    Observable<Pair<String, HourlyForecast>> getCurrentCitiesWeather(final Iterable<String> locationKeys,
            @Nullable String language, @Nullable Boolean details);

    Observable<CityWeather> getCityWeather(String locationKey,
            @Nullable String language, @Nullable Boolean details);
}
