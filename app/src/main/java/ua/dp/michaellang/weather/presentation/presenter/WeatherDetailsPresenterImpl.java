package ua.dp.michaellang.weather.presentation.presenter;

import android.accounts.AuthenticatorException;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.CityWeather;
import ua.dp.michaellang.weather.data.entity.Forecast.DailyForecast;
import ua.dp.michaellang.weather.data.entity.Forecast.HourlyForecast;
import ua.dp.michaellang.weather.domain.usecase.WeatherDetailsUseCase;
import ua.dp.michaellang.weather.presentation.view.WeatherDetailsView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public class WeatherDetailsPresenterImpl implements WeatherDetailsPresenter {

    private WeatherDetailsUseCase mInteractor;
    private WeatherDetailsView mView;

    @Inject
    public WeatherDetailsPresenterImpl(WeatherDetailsUseCase interactor, WeatherDetailsView view) {
        mInteractor = interactor;
        mView = view;
    }

    @Override
    public void onStart() {

    }

    private DisposableCompletableObserver createAddToFavoriteObserver() {
        return new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.onAddToFavoriteSuccess();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.onFavoriteActionFail();
            }
        };
    }

    private DisposableCompletableObserver createRemoveFromFavoriteObserver() {
        return new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                mView.onRemoveFromFavoriteSuccess();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.onFavoriteActionFail();
            }
        };
    }

    private DisposableSingleObserver<Boolean> createFavoriteCheckObserver() {
        return new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(@NonNull Boolean result) {
                mView.onIsFavoriteChecked(result);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mView.onFavoriteActionFail();
            }
        };
    }

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
    public void onDestroy() {
        mInteractor.dispose();
        mView = null;
    }

    @Override
    public void loadWeather(String cityCode) {
        String language = Locale.getDefault().getLanguage();
        mInteractor.getCityWeather(createCityWeatherObservable(), cityCode, language, true);
    }

    @Override
    public void addToFavorite(String cityCode) {
        String language = Locale.getDefault().getLanguage();
        mInteractor.addToFavorites(createAddToFavoriteObserver(), cityCode, language);
    }

    @Override
    public void checkIsFavorite(String cityCode) {
        mInteractor.checkFavorite(createFavoriteCheckObserver(), cityCode);
    }

    @Override
    public void removeFromFavorite(String cityCode) {
        mInteractor.remove(createRemoveFromFavoriteObserver(), cityCode);
    }
}
