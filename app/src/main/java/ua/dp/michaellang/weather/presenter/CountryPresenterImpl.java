package ua.dp.michaellang.weather.presenter;

import android.accounts.AuthenticatorException;
import rx.Subscriber;
import ua.dp.michaellang.weather.R;
import ua.dp.michaellang.weather.network.model.Location.Region;
import ua.dp.michaellang.weather.repository.CountryListRepository;
import ua.dp.michaellang.weather.view.CountryListView;

import java.util.List;
import java.util.Locale;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class CountryPresenterImpl implements CountryListPresenter {
    private CountryListView mView;
    private Subscriber<List<Region>> mSubscriber;

    public CountryPresenterImpl(CountryListView view) {
        mView = view;
    }

    private Subscriber<List<Region>> createSubscriber() {
        return new Subscriber<List<Region>>() {
            @Override
            public void onCompleted() {
                // TODO: 14.09.2017
            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof AuthenticatorException) {
                    mView.onError(R.string.error_auth);
                } else {
                    mView.onError(R.string.error_connect);
                }
            }

            @Override
            public void onNext(List<Region> data) {
                mView.onCountryListLoaded(data);
            }
        };
    }

    @Override
    public void loadCountryList() {
        String lang = Locale.getDefault().getLanguage();

        new CountryListRepository()
                .getCountryList(mSubscriber, lang);
    }

    @Override
    public void onStart() {
        mSubscriber = createSubscriber();
    }

    @Override
    public void onStop() {
        if (mSubscriber != null && mSubscriber.isUnsubscribed()) {
            mSubscriber.unsubscribe();
        }
    }
}
