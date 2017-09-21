package ua.dp.michaellang.weather.repository;

import android.accounts.AuthenticatorException;
import android.support.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ua.dp.michaellang.weather.network.AccuWeatherMethods;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecastResponse;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.repository.model.CityWeather;

import java.util.List;

import static ua.dp.michaellang.weather.Contants.AUTH_ERROR_CODE;

/**
 * Date: 21.09.2017
 *
 * @author Michael Lang
 */
public class WeatherRepository {

    public void getCityWeather(Observer<CityWeather> observer, String locationKey,
            @Nullable String language, @Nullable Boolean details) {
        AccuWeatherMethods instance = AccuWeatherMethods.getInstance();

        Observable<Response<List<HourlyForecast>>> hoursForecastObserver
                = instance.get24HoursForecast(locationKey, language, details);

        Observable<Response<DailyForecastResponse>> tenDaysForecastObserver
                = instance.getTenDaysForecast(locationKey, language, details);

        hoursForecastObserver.zipWith(tenDaysForecastObserver, getZipper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @android.support.annotation.NonNull
    private BiFunction<Response<List<HourlyForecast>>, Response<DailyForecastResponse>, CityWeather> getZipper() {
        return new BiFunction<Response<List<HourlyForecast>>,
                Response<DailyForecastResponse>, CityWeather>() {
            @Override
            public CityWeather apply(@NonNull Response<List<HourlyForecast>> response,
                    @NonNull Response<DailyForecastResponse> response2) throws Exception {
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
            }
        };
    }
}
