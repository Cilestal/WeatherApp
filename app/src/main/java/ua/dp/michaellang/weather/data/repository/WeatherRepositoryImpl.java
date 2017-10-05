package ua.dp.michaellang.weather.data.repository;

import android.accounts.AuthenticatorException;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import retrofit2.Response;
import ua.dp.michaellang.weather.data.entity.CityWeather;
import ua.dp.michaellang.weather.data.entity.Forecast.DailyForecastResponse;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.data.network.AccuWeatherService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static ua.dp.michaellang.weather.data.network.ApiConstants.AUTH_ERROR_CODE;

/**
 * Date: 21.09.2017
 *
 * @author Michael Lang
 */
@Singleton
public class WeatherRepositoryImpl implements WeatherRepository {

    private AccuWeatherService mService;

    @Inject
    public WeatherRepositoryImpl(AccuWeatherService service) {
        mService = service;
    }

    @android.support.annotation.NonNull
    private BiFunction<Response<List<HourlyForecast>>, Response<DailyForecastResponse>, CityWeather> getZipper() {
        return (response, response2) -> {
            if (response.code() == AUTH_ERROR_CODE || response2.code() == AUTH_ERROR_CODE) {
                throw new AuthenticatorException();
            }

            if (!response.isSuccessful()) {
                throw new Exception(response.errorBody().string());
            }

            if (!response2.isSuccessful()) {
                throw new Exception(response2.errorBody().string());
            }

            CityWeather cityWeather = new CityWeather();
            cityWeather.setDailyForecasts(response2.body().getDailyForecasts());
            cityWeather.setHourlyForecasts(response.body());

            return cityWeather;
        };
    }

    @Override
    public Observable<Pair<String, HourlyForecast>> getCurrentCitiesWeather(Iterable<String> locationKeys,
            @Nullable String language, @Nullable Boolean details) {
        //записываем все Observable в List
        List<Observable<Pair<String, HourlyForecast>>> lst = new ArrayList<>();
        for (final String locationKey : locationKeys) {
            //сохраняем результат в виде ключ-значение
            Observable<Pair<String, HourlyForecast>> observable
                    = mService.getOneHourForecast(locationKey, language, details)
                    .map(response -> {
                        HourlyForecast hourlyForecast = response.body().get(0);
                        return new Pair<>(locationKey, hourlyForecast);
                    });
            lst.add(observable);
        }

/*        //собираем все KeyValuePair в Map, выполняем subscribe
        Observable.merge(lst)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);*/

        return Observable.merge(lst);
    }

    @Override
    public Observable<CityWeather> getCityWeather(String locationKey, @Nullable String language, @Nullable Boolean details) {
        Observable<Response<List<HourlyForecast>>> hoursForecastObserver
                = mService.get24HoursForecast(locationKey, language, details);

        Observable<Response<DailyForecastResponse>> tenDaysForecastObserver
                = mService.getTenDaysForecast(locationKey, language, details);

        return hoursForecastObserver.zipWith(tenDaysForecastObserver, getZipper());
    }
}
