package ua.dp.michaellang.weather.domain.usecase;

import android.support.annotation.Nullable;
import io.reactivex.observers.DisposableObserver;
import ua.dp.michaellang.weather.data.entity.Location.Region;

import java.util.List;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
public interface CountryListUseCase extends UseCase{
    void loadCityList(DisposableObserver<List<Region>> observer, @Nullable String language);
}
