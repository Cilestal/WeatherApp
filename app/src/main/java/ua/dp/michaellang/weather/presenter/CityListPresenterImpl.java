package ua.dp.michaellang.weather.presenter;

import android.accounts.AuthenticatorException;
import android.support.annotation.Nullable;
import rx.Subscriber;
import timber.log.Timber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.AccuWeatherMethods;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.repository.CityListRepository;
import ua.dp.michaellang.weather.utils.KeyValuePair;
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
    private Subscriber<List<City>> mCityListSubscriber;
    private Subscriber<KeyValuePair<String, HourlyForecast>> mWeatherSubscriber;

    private CityListView mView;

    private String mCountryId;
    private List<City> mCities;

    public CityListPresenterImpl(CityListView view, @Nullable String countryId) {
        mView = view;
        mCountryId = countryId;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCityListSubscriber != null && mCityListSubscriber.isUnsubscribed()) {
            mCityListSubscriber.unsubscribe();
        }

        if (mWeatherSubscriber != null && mWeatherSubscriber.isUnsubscribed()) {
            mWeatherSubscriber.unsubscribe();
        }
    }

    private Subscriber<List<City>> createCityListSubscriber() {
        return new Subscriber<List<City>>() {
            @Override
            public void onCompleted() {
                //stub
            }

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
            public void onNext(List<City> response) {
                if (response != null) {
                    mCities = response;
                    mView.onCityListLoaded(response);
                }
            }
        };
    }

    private Subscriber<KeyValuePair<String, HourlyForecast>> createWeatherSubscriber() {
        return new Subscriber<KeyValuePair<String, HourlyForecast>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Timber.e("Load weather error.");
            }

            @Override
            public void onNext(KeyValuePair<String, HourlyForecast> data) {
                mView.onCityWeatherLoaded(data);
            }
        };
    }

    @Override
    public void loadCityList() {
        if (mCityListSubscriber != null && mCityListSubscriber.isUnsubscribed()) {
            mCityListSubscriber.unsubscribe();
        }

        mCityListSubscriber = createCityListSubscriber();

        String language = Locale.getDefault().getLanguage();

        if (mCountryId != null) {
            AccuWeatherMethods.getInstance()
                    .getCitiesByCountry(mCityListSubscriber, mCountryId, language, true);
        } else {
            new CityListRepository()
                    .getFavoriteCities(mCityListSubscriber);
        }
    }

    @Override
    public void loadCitiesWeather() {
        if (mWeatherSubscriber != null && mWeatherSubscriber.isUnsubscribed()) {
            mWeatherSubscriber.unsubscribe();
        }

        mWeatherSubscriber = createWeatherSubscriber();


        String language = Locale.getDefault().getLanguage();
        List<String> keys = new ArrayList<>();
        for (City city : mCities) {
            keys.add(city.getKey());
        }

        String[] array = keys.toArray(new String[0]);

        AccuWeatherMethods.getInstance()
                .getCurrentCitiesWeather(mWeatherSubscriber, array, language, false);
    }
}
