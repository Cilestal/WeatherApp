package ua.dp.michaellang.weather.network;

import android.support.annotation.Nullable;
import io.reactivex.Observable;
import retrofit2.Response;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecastResponse;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.network.model.Location.Region;
import ua.dp.michaellang.weather.utils.RetrofitUtils;

import java.util.List;

import static ua.dp.michaellang.weather.Contants.API_KEY;

/**
 * Date: 13.09.2017
 *
 * @author Michael Lang
 */
public class AccuWeatherMethods {
    private AccuWeatherService mService;

    private static class Nested {
        static AccuWeatherMethods instance = new AccuWeatherMethods();
    }

    private AccuWeatherMethods() {
        mService = RetrofitUtils.createService(AccuWeatherService.class);
    }

    public static AccuWeatherMethods getInstance() {
        return Nested.instance;
    }

    public Observable<Response<List<HourlyForecast>>> getOneHourForecast(String locationKey,
            @Nullable String language, @Nullable Boolean details) {
        return mService.getOneHourForecast(locationKey, API_KEY, language, details);
    }

    public Observable<Response<List<City>>> getCitiesByCountry(String countryId,
            @Nullable String language, @Nullable Boolean details) {
        return mService.getCitiesByCountry(countryId, API_KEY, language, details);
    }

    public Observable<Response<List<HourlyForecast>>> get24HoursForecast(String locationKey,
            @Nullable String language, @Nullable Boolean details) {
        return mService.get24HoursForecast(locationKey, API_KEY, language, details);
    }

    public Observable<Response<DailyForecastResponse>> getTenDaysForecast(String locationKey,
            @Nullable String language, @Nullable Boolean details) {
        return mService.getTenDaysForecast(locationKey, API_KEY, language, details);
    }

    public Observable<Response<City>> getCityInfo(String locationKey, @Nullable String language) {
        return mService.getCityInfo(locationKey, API_KEY, language);
    }

    public Observable<Response<List<Region>>> getCountryList(@Nullable String language) {
        return mService.getCountryList(API_KEY, language);
    }


}
