package ua.dp.michaellang.weather.presenter;

import android.support.annotation.NonNull;
import retrofit2.Response;
import rx.Subscriber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.AccuWeatherMethods;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecast;
import ua.dp.michaellang.weather.network.model.Forecast.DailyForecastResponse;
import ua.dp.michaellang.weather.network.model.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.repository.CityListRepository;
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

    private CityListRepository mCityListRepository;

    private Subscriber<Response<DailyForecastResponse>> mDailyWeatherSubscriber;
    private Subscriber<Response<List<HourlyForecast>>> mHourlyWeatherSubscriber;

    private Subscriber<City> mCityInfoSubscriber;

    private int mCompletedSubscribers = 0;

    public WeatherDetailsPresenterImpl(WeatherDetailsView view, String cityCode) {
        mView = view;
        mCityCode = cityCode;

        mCityListRepository = new CityListRepository();
    }

    @Override
    public void onStart() {
        onStop();

        mDailyWeatherSubscriber = createDailyWeatherSubscriber();
        mHourlyWeatherSubscriber = createHourlyWeatherSubscriber();
        mCityInfoSubscriber = createCityInfoSubscriber();
    }

    private Subscriber<City> createCityInfoSubscriber() {
        return new Subscriber<City>() {
            @Override
            public void onCompleted() {
                //stub
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
    private Subscriber<Response<DailyForecastResponse>> createDailyWeatherSubscriber() {
        return new Subscriber<Response<DailyForecastResponse>>() {
            @Override
            public void onCompleted() {
                mCompletedSubscribers++;
                checkCompleted();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(R.string.error_connect);
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
    private Subscriber<Response<List<HourlyForecast>>> createHourlyWeatherSubscriber() {
        return new Subscriber<Response<List<HourlyForecast>>>() {
            @Override
            public void onCompleted() {
                mCompletedSubscribers++;
                checkCompleted();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(R.string.error_connect);
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

    private void checkCompleted() {
        if (mCompletedSubscribers >= 2) {
            mView.onLoadComplete();
            mCompletedSubscribers = 0;
        }
    }

    @Override
    public void onStop() {
        if (mDailyWeatherSubscriber != null && mDailyWeatherSubscriber.isUnsubscribed()) {
            mDailyWeatherSubscriber.unsubscribe();
        }

        if (mHourlyWeatherSubscriber != null && mHourlyWeatherSubscriber.isUnsubscribed()) {
            mHourlyWeatherSubscriber.unsubscribe();
        }

        if (mCityInfoSubscriber != null && mCityInfoSubscriber.isUnsubscribed()) {
            mCityInfoSubscriber.unsubscribe();
        }
    }

    @Override
    public void loadHourlyWeather() {
        String language = Locale.getDefault().getLanguage();
        AccuWeatherMethods.getInstance()
                .get24HoursForecast(mHourlyWeatherSubscriber, mCityCode, language, true);
    }

    @Override
    public void loadDailyWeather() {
        String language = Locale.getDefault().getLanguage();
        AccuWeatherMethods.getInstance()
                .getTenDaysForecast(mDailyWeatherSubscriber, mCityCode, language, true);
    }

    @Override
    public void addToFavorite() {
        String language = Locale.getDefault().getLanguage();
        mCityListRepository.addToFavorites(mCityInfoSubscriber, mCityCode, language);
    }

    @Override
    public void checkIsFavorite() {
        boolean result = mCityListRepository.getCity(mCityCode) != null;
        mView.onIsFavoriteChecked(result);
    }

    @Override
    public void removeFromFavorite() {
        City city = new City();
        city.setKey(mCityCode);
        mCityListRepository.remove(city);
        mView.onFavoriteActionSuccess();

    }
}
