package ua.dp.michaellang.weather.presentation.inject.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.data.repository.WeatherRepository;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.domain.interactor.CityListInteractor;
import ua.dp.michaellang.weather.domain.usecase.CityListUseCase;
import ua.dp.michaellang.weather.presentation.presenter.CityListPresenter;
import ua.dp.michaellang.weather.presentation.presenter.CityListPresenterImpl;
import ua.dp.michaellang.weather.presentation.ui.adapter.CityListAdapter;
import ua.dp.michaellang.weather.presentation.view.CityListView;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

import javax.inject.Named;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Module
public class CityListModule {
    private CityListView mView;
    private String mCountryId;

    public CityListModule(CityListView view, String countryId) {
        mView = view;
        mCountryId = countryId;
    }

    @Provides
    @ActivityScope
    CityListAdapter provideCityListAdapter(@Named("CONTEXT") Context context, AssetsUtils utils) {
        return new CityListAdapter(context, utils);
    }

    @Provides
    @ActivityScope
    CityListPresenter provideCityListPresenter(CityListUseCase cityListUseCase) {
        return new CityListPresenterImpl(mView, cityListUseCase, mCountryId);
    }

    @Provides
    @ActivityScope
    CityListUseCase provideCityListUseCase(LocationRepository locationRepository, WeatherRepository weatherRepository) {
        return new CityListInteractor(locationRepository, weatherRepository);
    }


}
