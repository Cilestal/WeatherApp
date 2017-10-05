package ua.dp.michaellang.weather.data.network;

import android.support.annotation.Nullable;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ua.dp.michaellang.weather.data.entity.Forecast.DailyForecastResponse;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.data.entity.Location.Region;

import java.util.List;

/**
 * Date: 13.09.2017
 *
 * @author Michael Lang
 */
public interface AccuWeatherService {

    @GET("/locations/v1/countries")
    Observable<Response<List<Region>>> getCountryList(
            @Nullable @Query("language") String language
    );

    @GET("/locations/v1/cities/{country}")
    Observable<Response<List<City>>> getCitiesByCountry(
            @Path("country") String countryId,
            @Nullable @Query("language") String language,
            @Nullable @Query("details") Boolean details
    );

    @GET("/forecasts/v1/daily/1day/{location_key}?metric=true")
    Observable<Response<DailyForecastResponse>> getOneDayForecast(
            @Path("location_key") String locationKey,
            @Nullable @Query("language") String language,
            @Nullable @Query("details") Boolean details
    );

    @GET("/forecasts/v1/daily/10day/{location_key}?metric=true")
    Observable<Response<DailyForecastResponse>> getTenDaysForecast(
            @Path("location_key") String locationKey,
            @Nullable @Query("language") String language,
            @Nullable @Query("details") Boolean details
    );

    @GET("/forecasts/v1/hourly/24hour/{location_key}?metric=true")
    Observable<Response<List<HourlyForecast>>> get24HoursForecast(
            @Path("location_key") String locationKey,
            @Nullable @Query("language") String language,
            @Nullable @Query("details") Boolean details
    );

    @GET("/forecasts/v1/hourly/1hour/{location_key}?metric=true")
    Observable<Response<List<HourlyForecast>>> getOneHourForecast(
            @Path("location_key") String locationKey,
            @Nullable @Query("language") String language,
            @Nullable @Query("details") Boolean details
    );

    @GET("/locations/v1/{location_key}?metric=true&details=true")
    Observable<Response<City>> getCityInfo(
            @Path("location_key") String locationKey,
            @Nullable @Query("language") String language
    );

}
