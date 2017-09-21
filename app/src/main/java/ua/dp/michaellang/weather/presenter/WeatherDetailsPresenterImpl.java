package ua.dp.michaellang.weather.presenter;

import android.accounts.AuthenticatorException;
import android.support.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecast;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.repository.FavoriteListRepository;
import ua.dp.michaellang.weather.repository.WeatherRepository;
import ua.dp.michaellang.weather.repository.model.CityWeather;
import ua.dp.michaellang.weather.view.WeatherDetailsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public class WeatherDetailsPresenterImpl implements WeatherDetailsPresenter {
    private WeatherDetailsView mView;
    private String mCityCode;

    private FavoriteListRepository mFavoriteListRepository;
    private WeatherRepository mWeatherRepository;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private DisposableObserver<CityWeather> mCityWeatherObserver;
    private DisposableObserver<City> mCityInfoObserver;

    public WeatherDetailsPresenterImpl(WeatherDetailsView view, String cityCode) {
        mView = view;
        mCityCode = cityCode;

        mWeatherRepository = new WeatherRepository();
        mFavoriteListRepository = new FavoriteListRepository();
    }

    @Override
    public void onStart() {
        mCityWeatherObserver = createCityWeatherObservable();
        mCityInfoObserver = createCityInfoObserver();

        mDisposables.add(mCityWeatherObserver);
        mDisposables.add(mCityInfoObserver);
    }

    private DisposableObserver<City> createCityInfoObserver() {
        return new DisposableObserver<City>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                mView.onAddToFavoriteFail();
            }

            @Override
            public void onNext(City city) {
                mView.onFavoriteActionSuccess();
            }
        };
    }

    @NonNull
    private DisposableObserver<CityWeather> createCityWeatherObservable() {
        return new DisposableObserver<CityWeather>() {
            @Override
            public void onError(Throwable e) {
                if (e instanceof AuthenticatorException) {
                    mView.onError(R.string.error_auth);
                } else {
                    mView.onError(R.string.error_connect);
                }
            }

            @Override
            public void onComplete() {
                mView.onLoadComplete();
            }

            @Override
            public void onNext(CityWeather response) {
                //Daily forecast -> показываем прогноз на неделю
                List<DailyForecast> forecasts = response.getDailyForecasts();
                ArrayList<DailyForecast> dailyForecasts = new ArrayList<>();

                for (int i = 0; i < 7; i++) {
                    dailyForecasts.add(forecasts.get(i));
                }
                mView.onDailyWeatherLoaded(dailyForecasts);

                //HourlyForecast -> показываем каждый 4 час
                List<HourlyForecast> body = response.getHourlyForecasts();
                List<HourlyForecast> hourlyForecasts = new ArrayList<>();

                hourlyForecasts.add(body.get(0));
                for (int i = 0; i < 6; i++) {
                    hourlyForecasts.add(body.get(i * 4 + 3));
                }

                mView.onHourlyWeatherLoaded(hourlyForecasts);
            }
        };
    }

    @Override
    public void onStop() {
        mDisposables.clear();
    }

    @Override
    public void loadWeather() {
        String language = Locale.getDefault().getLanguage();
        mWeatherRepository.getCityWeather(mCityWeatherObserver, mCityCode, language, true);
    }

    @Override
    public void addToFavorite() {
        String language = Locale.getDefault().getLanguage();
        mFavoriteListRepository.addToFavorites(mCityInfoObserver, mCityCode, language);
    }

    @Override
    public void checkIsFavorite() {
        boolean result = mFavoriteListRepository.getCity(mCityCode) != null;
        mView.onIsFavoriteChecked(result);
    }

    @Override
    public void removeFromFavorite() {
        City city = new City();
        city.setKey(mCityCode);
        mFavoriteListRepository.remove(city);
        mView.onFavoriteActionSuccess();
    }
}
