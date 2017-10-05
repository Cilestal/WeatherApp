package ua.dp.michaellang.weather.domain.interactor;

import android.support.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ua.dp.michaellang.weather.data.entity.CityWeather;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.data.repository.WeatherRepository;
import ua.dp.michaellang.weather.domain.usecase.WeatherDetailsUseCase;

import javax.inject.Inject;

/**
 * Date: 26.09.2017
 *
 * @author Michael Lang
 */
public class WeatherDetailsInteractor extends BaseInteractor
        implements WeatherDetailsUseCase {
    private LocationRepository mLocationRepository;
    private WeatherRepository mWeatherRepository;

    @Inject
    public WeatherDetailsInteractor(LocationRepository locationRepository, WeatherRepository weatherRepository) {
        mLocationRepository = locationRepository;
        mWeatherRepository = weatherRepository;
    }

    @Override
    public void getCityWeather(DisposableObserver<CityWeather> observer, String cityCode,
            @Nullable String language, @Nullable Boolean details) {
        mWeatherRepository.getCityWeather(cityCode, language, details)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        addDisposable(observer);
    }

    @Override
    public void addToFavorites(DisposableCompletableObserver observer,
            String cityCode, @Nullable String language) {
        Disposable disposable = mLocationRepository.getCityInfo(cityCode, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cityResponse -> {
                    if (cityResponse.isSuccessful()) {
                        addToFavorite(observer, cityResponse.body());
                    } else {
                        observer.onError(new Exception(cityResponse.errorBody().string()));
                    }
                }, observer::onError);

        addDisposable(disposable);
        addDisposable(observer);
    }

    private void addToFavorite(DisposableCompletableObserver observer, City city) {
        mLocationRepository.addToFavorites(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void checkFavorite(DisposableSingleObserver<Boolean> observer, String cityCode) {
        mLocationRepository.checkIsFavorite(cityCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        addDisposable(observer);
    }

    @Override
    public void remove(DisposableCompletableObserver observer, String cityCode) {
        mLocationRepository.removeFavoriteCity(cityCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        addDisposable(observer);
    }
}
