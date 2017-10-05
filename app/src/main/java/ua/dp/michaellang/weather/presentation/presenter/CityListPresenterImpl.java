package ua.dp.michaellang.weather.presentation.presenter;

import android.accounts.AuthenticatorException;
import android.support.v4.util.Pair;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.domain.usecase.CityListUseCase;
import ua.dp.michaellang.weather.presentation.view.CityListView;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Locale;

/**
 * Date: 15.09.2017
 *
 * @author Michael Lang
 */
@ActivityScope
public class CityListPresenterImpl implements CityListPresenter {
    private CityListView mView;
    private CityListUseCase mCityListInteractor;
    private String mCountryId;

    private List<City> mCities;

    @Inject
    public CityListPresenterImpl(CityListView view, CityListUseCase cityListInteractor,
            @Named("country_id") String countryId) {
        mView = view;
        mCityListInteractor = cityListInteractor;
        mCountryId = countryId;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mCityListInteractor.dispose();
    }

    private DisposableObserver<List<City>> createCityListSubscriber() {
        return new DisposableObserver<List<City>>() {
            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                if (e instanceof AuthenticatorException) {
                    mView.onError(R.string.error_auth);
                } else {
                    mView.onError(R.string.error_connect);
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(List<City> response) {
                if (response != null) {
                    mCities = response;
                    mView.onCityListLoaded(response);
                }
            }
        };
    }

    private DisposableObserver<Pair<String, HourlyForecast>> createWeatherSubscriber() {
        return new DisposableObserver<Pair<String, HourlyForecast>>() {
            @Override
            public void onError(Throwable e) {
                Timber.e("Load weather error.");
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(Pair<String, HourlyForecast> data) {
                mView.onCityWeatherLoaded(data);
            }
        };
    }

    @Override
    public void loadCityList() {
        String language = Locale.getDefault().getLanguage();
        mCityListInteractor.getCityList(createCityListSubscriber(), mCountryId, language, true);
    }

    @Override
    public void loadCitiesWeather() {
        String language = Locale.getDefault().getLanguage();
        Observable.fromIterable(mCities)
                .map(City::getKey)
                .toList()
                .subscribe(keys -> {
                    mCityListInteractor.getCurrentCitiesWeather(
                            createWeatherSubscriber(), keys, language, false);
                });
    }
}
