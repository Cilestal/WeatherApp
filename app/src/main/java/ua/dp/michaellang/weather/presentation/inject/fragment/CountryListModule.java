package ua.dp.michaellang.weather.presentation.inject.fragment;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.domain.interactor.CountryListInteractor;
import ua.dp.michaellang.weather.domain.usecase.CountryListUseCase;
import ua.dp.michaellang.weather.presentation.presenter.CountryListPresenter;
import ua.dp.michaellang.weather.presentation.presenter.CountryPresenterImpl;
import ua.dp.michaellang.weather.presentation.ui.adapter.CountryListAdapter;
import ua.dp.michaellang.weather.presentation.ui.fragment.CountryListDialog;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;
import ua.dp.michaellang.weather.presentation.view.CountryListView;

import javax.inject.Named;

import static ua.dp.michaellang.weather.Contants.ACTIVITY_CONTEXT;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Module
public class CountryListModule {
    @Provides
    CountryListView provideCountryListView(CountryListDialog fragment) {
        return fragment;
    }

    @Provides
    CountryListUseCase provideCountryListUseCase(LocationRepository repository) {
        return new CountryListInteractor(repository);
    }

    @Provides
    CountryListPresenter provideCountryListPresenter(CountryListView view, CountryListUseCase iteractor) {
        return new CountryPresenterImpl(view, iteractor);
    }

    @Provides
    CountryListAdapter provideCountryListAdapter(@Named(ACTIVITY_CONTEXT) Context context, AssetsUtils assets) {
        return new CountryListAdapter(context, assets);
    }
}
