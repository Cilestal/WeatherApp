package ua.dp.michaellang.weather.domain.usecase;

import android.support.annotation.Nullable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import ua.dp.michaellang.weather.data.entity.CityWeather;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
public interface WeatherDetailsUseCase extends UseCase {
    void getCityWeather(DisposableObserver<CityWeather> observer,
            String cityCode, @Nullable String language, @Nullable Boolean details);

    void addToFavorites(DisposableCompletableObserver observer, String cityCode, @Nullable String language);

    void checkFavorite(DisposableSingleObserver<Boolean> observer, String cityCode);

    void remove(DisposableCompletableObserver observer, String cityCode);
}
