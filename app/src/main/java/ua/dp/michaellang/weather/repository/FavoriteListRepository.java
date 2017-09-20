package ua.dp.michaellang.weather.repository;

import android.accounts.AuthenticatorException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import retrofit2.Response;
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
public class FavoriteListRepository {

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
                .doOnNext(new Consumer<Response<City>>() {
                    @Override
                    public void accept(Response<City> cityResponse) throws Exception {
                        if (cityResponse.isSuccessful()) {
                            City body = cityResponse.body();
                            insertOrUpdate(body);
                            cityObservable.onNext(body);
                        } else {
                            cityObservable.onError(new AuthenticatorException());
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        cityObservable.onError(e);
                    }
                })
                .map(new Function<Response<City>, City>() {
                    @Override
                    public City apply(@io.reactivex.annotations.NonNull Response<City> cityResponse) throws Exception {
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
        Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<City>> e) throws Exception {
                final Realm realm = Realm.getDefaultInstance();
                final RealmResults<FavoriteCity> all = realm
                        .where(FavoriteCity.class)
                        .findAll();

                final RealmChangeListener<Realm> listener = new RealmChangeListener<Realm>() {
                    @Override
                    public void onChange(@NonNull Realm realm) {
                        e.onNext(getCities(all));
                    }
                };
                realm.addChangeListener(listener);

                e.setDisposable(new MainThreadDisposable() {
                    @Override
                    protected void onDispose() {
                        realm.removeChangeListener(listener);
                        realm.close();
                    }
                });

                e.onNext(getCities(all));
            }
        }).subscribe(observer);




   /*     Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Object> e) throws Exception {
                RealmResults<FavoriteCity> all = Realm.getDefaultInstance()
                        .where(FavoriteCity.class)
                        .findAll();
            }
        });




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
                .subscribe(observer);*/
    }

    @NonNull
    private List<City> getCities(RealmResults<FavoriteCity> all) {
        List<City> list = new ArrayList<>();
        for (FavoriteCity favoriteCity : all) {
            list.add(mMapper.map(favoriteCity));
        }
        return list;
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
