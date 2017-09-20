package ua.dp.michaellang.weather.repository;

import android.accounts.AuthenticatorException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Response;
import ua.dp.michaellang.weather.network.AccuWeatherMethods;
import ua.dp.michaellang.weather.network.model.Location.Region;
import ua.dp.michaellang.weather.repository.model.RealmCountry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Date: 19.09.2017
 *
 * @author Michael Lang
 */
public class CountryListRepository {
    private final Mapper<RealmCountry, Region> mMapper = new Mapper<RealmCountry, Region>() {
        @Override
        public Region map(RealmCountry realmCountry) {
            Region region = new Region();
            region.setId(realmCountry.getId());
            region.setLocalizedName(realmCountry.getLocalizedName());
            region.setEnglishName(realmCountry.getEnglishName());
            return region;
        }
    };

    //сначала возвращает данные из кэша, при отсутствии из инета
    public void getCountryList(final Observer<List<Region>> observer,
            @Nullable final String language) {
        getLocalCountryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Region>>() {
                    @Override
                    public void onError(Throwable e) {
                        getNetworkCountryList(language, observer);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(List<Region> regions) {
                        observer.onNext(regions);
                    }
                });
    }

    private void getNetworkCountryList(@Nullable String language, final Observer<List<Region>> observer) {
        getNetworkCountryList(language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<Region>>>() {
                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        //stub
                    }

                    @Override
                    public void onNext(Response<List<Region>> listResponse) {
                        if (listResponse.isSuccessful()) {
                            observer.onNext(listResponse.body());
                        } else {
                            observer.onError(new AuthenticatorException());
                        }
                    }
                });
    }

    private Observable<Response<List<Region>>> getNetworkCountryList(@Nullable String language) {
        return AccuWeatherMethods.getInstance()
                .getCountryList(language)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(new Function<Response<List<Region>>, Response<List<Region>>>() {
                    @Override
                    public Response<List<Region>> apply(@io.reactivex.annotations.NonNull Response<List<Region>> listResponse) throws Exception {
                        addAll(listResponse.body());
                        return listResponse;
                    }
                });
    }

    public void addAll(final Iterable<Region> regions) {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                for (Region region : regions) {
                    RealmCountry realmObject = realm.createObject(RealmCountry.class, region.getId());
                    realmObject.setEnglishName(region.getEnglishName());
                    realmObject.setLocalizedName(region.getLocalizedName());
                }
            }
        });

        realm.close();
    }

    public void clearCache() {
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.where(RealmCountry.class)
                        .findAll()
                        .deleteAllFromRealm();
            }
        });

        realm.close();
    }

    private Observable<List<Region>> getLocalCountryList() {
        return Observable.defer(new Callable<ObservableSource<? extends List<Region>>>() {
            @Override
            public ObservableSource<? extends List<Region>> call() throws Exception {
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
            }
        });
    }
}
