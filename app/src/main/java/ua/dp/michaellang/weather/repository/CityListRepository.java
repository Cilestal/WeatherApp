package ua.dp.michaellang.weather.repository;

import android.accounts.AuthenticatorException;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import ua.dp.michaellang.weather.network.AccuWeatherMethods;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;

import java.util.ArrayList;
import java.util.List;

import static ua.dp.michaellang.weather.Contants.AUTH_ERROR_CODE;

/**
 * Date: 21.09.2017
 *
 * @author Michael Lang
 */
public class CityListRepository {
    public void getCitiesByCountry(final Observer<List<City>> observer, String countryId,
            @Nullable String language, @Nullable Boolean details) {
        AccuWeatherMethods.getInstance()
                .getCitiesByCountry(countryId, language, details)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Response<List<City>>, List<City>>() {
                    @Override
                    public List<City> apply(@NonNull Response<List<City>> listResponse) throws Exception {
                        if (listResponse.code() == AUTH_ERROR_CODE) {
                            throw new AuthenticatorException();
                        }

                        if (!listResponse.isSuccessful()) {
                            throw new Exception(listResponse.errorBody().string());
                        }
                        return listResponse.body();
                    }
                })
                .subscribe(observer);
    }

    public void getCurrentCitiesWeather(Observer<Pair<String, HourlyForecast>> observer,
            final String[] locationKeys, @Nullable String language, @Nullable Boolean details){
        //записываем все Observable в List
        List<Observable<Pair<String, HourlyForecast>>> lst = new ArrayList<>();
        for (final String locationKey : locationKeys) {
            //сохраняем результат в виде ключ-значение
            Observable<Pair<String, HourlyForecast>> observable
                    = AccuWeatherMethods.getInstance()
                    .getOneHourForecast(locationKey, language, details)
                    .map(new Function<Response<List<HourlyForecast>>, Pair<String, HourlyForecast>>() {
                        @Override
                        public Pair<String, HourlyForecast> apply(@NonNull Response<List<HourlyForecast>> response) throws Exception {
                            HourlyForecast hourlyForecast = response.body().get(0);
                            return new Pair<>(locationKey, hourlyForecast);
                        }
                    });
            lst.add(observable);
        }

        //собираем все KeyValuePair в Map, выполняем subscribe
        Observable.merge(lst)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
