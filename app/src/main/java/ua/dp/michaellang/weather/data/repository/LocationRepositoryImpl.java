package ua.dp.michaellang.weather.data.repository;

import android.support.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.MainThreadDisposable;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Response;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.data.entity.Location.Region;
import ua.dp.michaellang.weather.data.network.AccuWeatherService;
import ua.dp.michaellang.weather.data.repository.entity.FavoriteCity;
import ua.dp.michaellang.weather.data.repository.entity.RealmCountry;
import ua.dp.michaellang.weather.data.repository.entity.mapper.CountryMapper;
import ua.dp.michaellang.weather.data.repository.entity.mapper.FavoriteCityMapper;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Date: 21.09.2017
 *
 * @author Michael Lang
 */
@Singleton
public class LocationRepositoryImpl implements LocationRepository {
    private AccuWeatherService mService;
    private CountryMapper mMapper;
    private FavoriteCityMapper mFavoriteCityMapper;

    @Inject
    public LocationRepositoryImpl(CountryMapper mapper, FavoriteCityMapper favoriteCityMapper,
            AccuWeatherService service) {
        mMapper = mapper;
        mService = service;
        mFavoriteCityMapper = favoriteCityMapper;
    }

    @Override
    public Observable<Response<List<City>>> getCitiesByCountry(String countryId,
            @Nullable String language, @Nullable Boolean details) {
        return mService.getCitiesByCountry(countryId, language, details);
    }

    @Override
    public Observable<Response<List<Region>>> getNetworkCountryList(@Nullable String language) {
        return mService.getCountryList(language);
    }

    @Override
    public Observable<Response<City>> getCityInfo(String key, @Nullable String language) {
        return mService.getCityInfo(key, language);
    }

    @Override
    public Completable addToFavorites(City city) {
        return Completable.create(e -> {
            final Realm realm = Realm.getDefaultInstance();

            realm.executeTransaction(realm1 -> {
                FavoriteCity fc = city.toFavoriteCity();
                realm1.insertOrUpdate(fc);
            });

            realm.close();
            e.onComplete();
        });
    }

    @Override
    public Observable<List<City>> getFavoriteCities() {
        return Observable.create(e -> {
            final Realm realm = Realm.getDefaultInstance();
            final RealmResults<FavoriteCity> all = realm
                    .where(FavoriteCity.class)
                    .findAll();


            all.addChangeListener(favoriteCities -> {
                List<FavoriteCity> es = realm.copyFromRealm(favoriteCities);
                e.onNext(getCities(es));
            });

//            final RealmChangeListener<Realm> listener = realm1 -> e.onNext(getCities(all));
//            realm.addChangeListener(listener);

            e.setDisposable(new MainThreadDisposable() {
                @Override
                protected void onDispose() {
                    all.removeAllChangeListeners();
                    //realm.removeChangeListener(listener);
                    realm.close();
                }
            });

            e.onNext(getCities(all));
        });
    }

    @Override
    public Single<Boolean> checkIsFavorite(String cityCode) {
        return Single.create(e -> {
            Realm realm = Realm.getDefaultInstance();

            FavoriteCity key = realm.where(FavoriteCity.class)
                    .equalTo("key", cityCode)
                    .findFirst();

            e.onSuccess(key != null);
        });
    }

    private List<City> getCities(Collection<FavoriteCity> all) {
        List<City> list = new ArrayList<>();
        for (FavoriteCity favoriteCity : all) {
            list.add(mFavoriteCityMapper.map(favoriteCity));
        }
        return list;
    }

    @Override
    public Completable removeFavoriteCity(String cityKey) {
        return Completable.create(e -> {
            // obtain the results of a query
            Realm realm = Realm.getDefaultInstance();
            final FavoriteCity fcity = realm.where(FavoriteCity.class)
                    .equalTo("key", cityKey)
                    .findFirst();

            realm.executeTransaction(realm1 -> {
                if (fcity != null) {
                    fcity.deleteFromRealm();
                }
            });
            realm.close();
            e.onComplete();
        });
    }

    @Override
    public Observable<List<Region>> getLocalCountryList() {
        return Observable.defer(() -> {
            Realm realm = Realm.getDefaultInstance();

            List<Region> regions = new ArrayList<>();
            RealmResults<RealmCountry> all = realm.where(RealmCountry.class).findAll();

            for (RealmCountry realmCountry : all) {
                regions.add(mMapper.map(realmCountry));
            }

            realm.close();

            if (regions.size() > 0) {
                return Observable.just(regions);
            } else {
                return Observable.error(new Exception());
            }
        });
    }
}
