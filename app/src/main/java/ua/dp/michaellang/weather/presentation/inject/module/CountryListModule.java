package ua.dp.michaellang.weather.presentation.inject.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.domain.interactor.CountryListInteractor;
import ua.dp.michaellang.weather.domain.usecase.CountryListUseCase;
import ua.dp.michaellang.weather.presentation.presenter.CountryListPresenter;
import ua.dp.michaellang.weather.presentation.presenter.CountryPresenterImpl;
import ua.dp.michaellang.weather.presentation.ui.adapter.CountryListAdapter;
import ua.dp.michaellang.weather.presentation.view.CountryListView;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

import javax.inject.Named;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Module
public class CountryListModule {
    private CountryListView mView;

    public CountryListModule(CountryListView view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    CountryListUseCase provideCountryListUseCase(LocationRepository repository){
        return new CountryListInteractor(repository);
    }


    @Provides
    @ActivityScope
    CountryListPresenter provideCountryListPresenter(CountryListUseCase iteractor){
        return new CountryPresenterImpl(mView, iteractor);
    }

    @Provides
    @ActivityScope
    CountryListAdapter provideCountryListAdapter(@Named("CONTEXT") Context context, AssetsUtils assets){
        return new CountryListAdapter(context, assets);
    }
}
