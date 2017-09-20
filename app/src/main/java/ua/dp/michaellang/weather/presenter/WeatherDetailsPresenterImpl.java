package ua.dp.michaellang.weather.presenter;

import android.support.annotation.NonNull;
import io.reactivex.observers.DisposableObserver;
import retrofit2.Response;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.AccuWeatherMethods;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecast;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecastResponse;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.repository.FavoriteListRepository;
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

    private DisposableObserver<Response<DailyForecastResponse>> mDailyWeatherSubscriber;
    private DisposableObserver<Response<List<HourlyForecast>>> mHourlyWeatherSubscriber;
    private DisposableObserver<City> mCityInfoSubscriber;

    private int mCompletedSubscribers = 0;

    public WeatherDetailsPresenterImpl(WeatherDetailsView view, String cityCode) {
        mView = view;
        mCityCode = cityCode;

        mFavoriteListRepository = new FavoriteListRepository();
    }

    @Override
    public void onStart() {
        onStop();

        mDailyWeatherSubscriber = createDailyWeatherSubscriber();
        mHourlyWeatherSubscriber = createHourlyWeatherSubscriber();
        mCityInfoSubscriber = createCityInfoSubscriber();
    }

    private DisposableObserver<City> createCityInfoSubscriber() {
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
    private DisposableObserver<Response<DailyForecastResponse>> createDailyWeatherSubscriber() {
        return new DisposableObserver<Response<DailyForecastResponse>>() {
            @Override
            public void onError(Throwable e) {
                mView.onError(R.string.error_connect);
            }

            @Override
            public void onComplete() {
                loadHourlyWeather();
            }

            @Override
            public void onNext(Response<DailyForecastResponse> response) {
                if (response.raw().code() == 503) {
                    mView.onError(R.string.error_auth);
                } else {
                    List<DailyForecast> forecasts = response.body().getDailyForecasts();
                    ArrayList<DailyForecast> dailyForecasts = new ArrayList<>();

                    for (int i = 0; i < 7; i++) {
                        dailyForecasts.add(forecasts.get(i));
                    }
                    mView.onDailyWeatherLoaded(dailyForecasts);
                }
            }
        };
    }

    @NonNull
    private DisposableObserver<Response<List<HourlyForecast>>> createHourlyWeatherSubscriber() {
        return new DisposableObserver<Response<List<HourlyForecast>>>() {

            @Override
            public void onError(Throwable e) {
                mView.onError(R.string.error_connect);
            }

            @Override
            public void onComplete() {
                mView.onLoadComplete();
            }

            @Override
            public void onNext(Response<List<HourlyForecast>> response) {
                if (response.raw().code() == 503) {
                    mView.onError(R.string.error_auth);
                } else {
                    List<HourlyForecast> body = response.body();
                    List<HourlyForecast> hourlyForecasts = new ArrayList<>();

                    hourlyForecasts.add(body.get(0));
                    for (int i = 0; i < 6; i++) {
                        hourlyForecasts.add(body.get(i * 4 + 3));
                    }

                    mView.onHourlyWeatherLoaded(hourlyForecasts);
                }
            }
        };
    }

    @Override
    public void onStop() {
        if (mDailyWeatherSubscriber != null && !mDailyWeatherSubscriber.isDisposed()) {
            mDailyWeatherSubscriber.dispose();
        }

        if (mHourlyWeatherSubscriber != null && !mHourlyWeatherSubscriber.isDisposed()) {
            mHourlyWeatherSubscriber.dispose();
        }

        if (mCityInfoSubscriber != null && !mCityInfoSubscriber.isDisposed()) {
            mCityInfoSubscriber.dispose();
        }
    }

    public void loadHourlyWeather() {
        String language = Locale.getDefault().getLanguage();
        AccuWeatherMethods.getInstance()
                .get24HoursForecast(mHourlyWeatherSubscriber, mCityCode, language, true);
    }

    @Override
    public void loadWeather() {
        String language = Locale.getDefault().getLanguage();
        AccuWeatherMethods.getInstance()
                .getTenDaysForecast(mDailyWeatherSubscriber, mCityCode, language, true);
    }

    @Override
    public void addToFavorite() {
        String language = Locale.getDefault().getLanguage();
        mFavoriteListRepository.addToFavorites(mCityInfoSubscriber, mCityCode, language);
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
