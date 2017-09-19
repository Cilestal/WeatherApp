package ua.dp.michaellang.weather.repository;

import android.accounts.AuthenticatorException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import ua.dp.michaellang.weather.network.AccuWeatherMethods;
import ua.dp.michaellang.weather.network.model.Location.AdministrativeArea;
import ua.dp.michaellang.weather.network.model.Location.City;
import ua.dp.michaellang.weather.network.model.Location.Details;
import ua.dp.michaellang.weather.repository.model.FavoriteCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 19.09.2017
 *
 * @author Michael Lang
 */
public class CityListRepository {

    private final Mapper<FavoriteCity, City> mMapper = new Mapper<FavoriteCity, City>() {
        @Override
        public City map(FavoriteCity favoriteCity) {
            City city = new City();
            city.setLocalizedName(favoriteCity.getName());
            city.setKey(favoriteCity.getKey());
            Details details = new Details();
            details.setPopulation(favoriteCity.getPopulation());
            city.setDetails(details);

            AdministrativeArea administrativeArea = new AdministrativeArea();
            administrativeArea.setLocalizedName(favoriteCity.getAreaName());
            city.setAdministrativeArea(administrativeArea);

            return city;
        }
    };

    public void addToFavorites(final Observer<City> cityObservable, String key,
            @Nullable String language) {
        AccuWeatherMethods.getInstance()
                .getCityInfo(key, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<Response<City>>() {
                    @Override
                    public void call(Response<City> cityResponse) {
                        if (cityResponse.isSuccessful()) {
                            City body = cityResponse.body();
                            insertOrUpdate(body);
                            cityObservable.onNext(body);
                        } else {
                            cityObservable.onError(new AuthenticatorException());
                        }
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable e) {
                        cityObservable.onError(e);
                    }
                })
                .map(new Func1<Response<City>, City>() {
                    @Override
                    public City call(Response<City> cityResponse) {
                        return cityResponse.body();
                    }
                })
                .subscribe();
    }

    public void insertOrUpdate(final City item) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                FavoriteCity fc = item.toFavoriteCity();
                realm.insertOrUpdate(fc);
            }
        });

        realm.close();
    }

    public void getFavoriteCities(Observer<List<City>> observer) {
        Realm.getDefaultInstance()
                .where(FavoriteCity.class)
                .findAll()
                .asObservable()
                .flatMap(new Func1<RealmResults<FavoriteCity>, Observable<List<City>>>() {
                    @Override
                    public Observable<List<City>> call(RealmResults<FavoriteCity> favoriteCities) {
                        List<City> list = new ArrayList<>();
                        for (FavoriteCity favoriteCity : favoriteCities) {
                            list.add(mMapper.map(favoriteCity));
                        }
                        return Observable.just(list);
                    }
                })
                .subscribe(observer);
    }

    public City getCity(String key) {
        FavoriteCity first = Realm.getDefaultInstance()
                .where(FavoriteCity.class)
                .equalTo("key", key)
                .findFirst();
        if (first != null) {
            return mMapper.map(first);
        } else {
            return null;
        }
    }

    public void remove(City item) {
        // obtain the results of a query
        Realm realm = Realm.getDefaultInstance();
        final FavoriteCity fcity = realm.where(FavoriteCity.class)
                .equalTo("key", item.getKey())
                .findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                if (fcity != null) {
                    fcity.deleteFromRealm();
                }
            }
        });
        realm.close();
    }
}
