package ua.dp.michaellang.weather.presentation.presenter;

import android.accounts.AuthenticatorException;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.data.entity.Location.Region;
import ua.dp.michaellang.weather.domain.usecase.CountryListUseCase;
import ua.dp.michaellang.weather.presentation.view.CountryListView;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CountryPresenterImpl implements CountryListPresenter {
    private CountryListView mView;
    private CountryListUseCase mInteractor;

    @Inject
    public CountryPresenterImpl(CountryListView view, CountryListUseCase interactor) {
        mView = view;
        mInteractor = interactor;
    }

    private DisposableObserver<List<Region>> createObserver() {
        return new DisposableObserver<List<Region>>() {
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

            }

            @Override
            public void onNext(List<Region> data) {
                sortData(data);
            }
        };
    }

    private void sortData(List<Region> data){
        Observable.fromIterable(data)
                .sorted((o1, o2) -> o1.getLocalizedName().compareTo(o2.getLocalizedName()))
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(regions -> mView.onCountryListLoaded(regions));
    }

    @Override
    public void loadCountryList() {
        String lang = Locale.getDefault().getLanguage();

        if(lang == null || lang.length() == 0){
            lang = Locale.US.getLanguage();
        }
        mInteractor.loadCityList(createObserver(), lang);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        mInteractor.dispose();
    }
}
