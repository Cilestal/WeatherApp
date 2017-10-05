package ua.dp.michaellang.weather.data.repository;

import android.support.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.data.entity.Location.Region;

import java.util.List;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
public interface LocationRepository {
    Observable<Response<List<City>>> getCitiesByCountry(String countryId,
            @Nullable String language, @Nullable Boolean details);

    Observable<List<Region>> getLocalCountryList();

    Observable<Response<List<Region>>> getNetworkCountryList(String language);

    Observable<Response<City>> getCityInfo(String key, @Nullable String language);

    Completable addToFavorites(City city);

    Observable<List<City>> getFavoriteCities();

    Single<Boolean> checkIsFavorite(String cityCode);

    Completable removeFavoriteCity(String cityKey);
}
