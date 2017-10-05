package ua.dp.michaellang.weather.domain.interactor;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.data.repository.WeatherRepository;
import ua.dp.michaellang.weather.domain.usecase.CityListUseCase;

import javax.inject.Inject;
import java.util.List;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
public class CityListInteractor extends BaseInteractor
        implements CityListUseCase {
    private LocationRepository mRepository;
    private WeatherRepository mWeatherRepository;

    @Inject
    public CityListInteractor(LocationRepository repository, WeatherRepository weatherRepository) {
        mRepository = repository;
        mWeatherRepository = weatherRepository;
    }

    @Override
    public void getCityList(DisposableObserver<List<City>> observer, @Nullable String countryId,
            @Nullable String language, @Nullable Boolean details) {
        Observable<List<City>> obs;

        if (countryId == null) {
            obs = mRepository.getFavoriteCities()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            obs = mRepository.getCitiesByCountry(countryId, language, details)
                    .flatMap(listResponse -> {
                        if (listResponse.isSuccessful()) {
                            return Observable.just(listResponse.body());
                        } else {
                            throw new Exception(listResponse.errorBody().string());
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        obs.subscribe(observer);

        addDisposable(observer);
    }

    @Override
    public void getCurrentCitiesWeather(DisposableObserver<Pair<String, HourlyForecast>> weatherSubscriber,
            Iterable<String> it, String language, boolean details) {
        mWeatherRepository.getCurrentCitiesWeather(it, language, details)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherSubscriber);

        addDisposable(weatherSubscriber);
    }
}
