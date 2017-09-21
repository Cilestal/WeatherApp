package ua.dp.michaellang.weather.presenter;

import android.accounts.AuthenticatorException;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.repository.CityListRepository;
import ua.dp.michaellang.weather.repository.FavoriteListRepository;
import ua.dp.michaellang.weather.view.CityListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Date: 15.09.2017
 *
 * @author Michael Lang
 */
public class CityListPresenterImpl implements CityListPresenter {
    private DisposableObserver<List<City>> mCityListSubscriber;
    private DisposableObserver<Pair<String, HourlyForecast>> mWeatherSubscriber;

    private CityListRepository mRepository;

    private CityListView mView;

    private String mCountryId;
    private List<City> mCities;
    private FavoriteListRepository mFavoriteListRepository;

    public CityListPresenterImpl(CityListView view, @Nullable String countryId) {
        mView = view;
        mCountryId = countryId;
        mFavoriteListRepository = new FavoriteListRepository();
        mRepository = new CityListRepository();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCityListSubscriber != null && !mCityListSubscriber.isDisposed()) {
            mCityListSubscriber.dispose();
        }

        if (mWeatherSubscriber != null && !mWeatherSubscriber.isDisposed()) {
            mWeatherSubscriber.dispose();
        }
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
        if (mCityListSubscriber != null && !mCityListSubscriber.isDisposed()) {
            mCityListSubscriber.dispose();
        }

        mCityListSubscriber = createCityListSubscriber();

        String language = Locale.getDefault().getLanguage();

        if (mCountryId != null) {
            mRepository.getCitiesByCountry(mCityListSubscriber, mCountryId, language, true);
        } else {
            mFavoriteListRepository.getFavoriteCities(mCityListSubscriber);
        }
    }

    @Override
    public void loadCitiesWeather() {
        if (mWeatherSubscriber != null && !mWeatherSubscriber.isDisposed()) {
            mWeatherSubscriber.dispose();
        }

        mWeatherSubscriber = createWeatherSubscriber();


        String language = Locale.getDefault().getLanguage();
        List<String> keys = new ArrayList<>();
        for (City city : mCities) {
            keys.add(city.getKey());
        }

        String[] array = keys.toArray(new String[0]);

        mRepository.getCurrentCitiesWeather(mWeatherSubscriber, array, language, false);
    }
}
