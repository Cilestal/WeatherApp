package ua.dp.michaellang.weather.domain.interactor;

import android.accounts.AuthenticatorException;
import android.support.annotation.Nullable;
import dagger.internal.Preconditions;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ua.dp.michaellang.weather.data.entity.Location.Region;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.domain.usecase.CountryListUseCase;

import javax.inject.Inject;
import java.util.List;

import static ua.dp.michaellang.weather.data.network.ApiConstants.AUTH_ERROR_CODE;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
public class CountryListInteractor extends BaseInteractor
        implements CountryListUseCase {

    private LocationRepository mRepository;

    @Inject
    public CountryListInteractor(LocationRepository repository) {
        mRepository = repository;
    }

    @Override
    public void loadCityList(DisposableObserver<List<Region>> observer,
            @Nullable String language) {
        Preconditions.checkNotNull(observer);
        Disposable disp = mRepository.getLocalCountryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer::onNext, throwable -> loadNetworkCityList(observer, language));

        addDisposable(observer);
        addDisposable(disp);
    }

    private void loadNetworkCityList(DisposableObserver<List<Region>> observer,
            @Nullable String language) {
        Disposable disposable = mRepository.getNetworkCountryList(language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResponse -> {
                    if (listResponse.isSuccessful()) {
                        observer.onNext(listResponse.body());
                    } else if (listResponse.code() == AUTH_ERROR_CODE) {
                        observer.onError(new AuthenticatorException());
                    } else {
                        observer.onError(new Exception(listResponse.errorBody().string()));
                    }
                });
        addDisposable(disposable);
    }
}
