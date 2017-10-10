package ua.dp.michaellang.weather.presentation.inject.fragment;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.data.repository.WeatherRepository;
import ua.dp.michaellang.weather.domain.interactor.CityListInteractor;
import ua.dp.michaellang.weather.domain.usecase.CityListUseCase;
import ua.dp.michaellang.weather.presentation.presenter.CityListPresenter;
import ua.dp.michaellang.weather.presentation.presenter.CityListPresenterImpl;
import ua.dp.michaellang.weather.presentation.ui.adapter.CityListAdapter;
import ua.dp.michaellang.weather.presentation.ui.fragment.CityListFragment;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;
import ua.dp.michaellang.weather.presentation.view.CityListView;

import javax.inject.Named;

import static ua.dp.michaellang.weather.Contants.ACTIVITY_CONTEXT;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Module
public class CityListModule {

    @Provides
    CityListView provideCityListView(CityListFragment fragment){
        return fragment;
    }

    @Provides
    CityListAdapter provideCityListAdapter(@Named(ACTIVITY_CONTEXT) Context context, AssetsUtils utils) {
        return new CityListAdapter(context, utils);
    }

    @Provides
    CityListPresenter provideCityListPresenter(CityListView view, CityListUseCase cityListUseCase) {
        return new CityListPresenterImpl(view, cityListUseCase);
    }

    @Provides
    CityListUseCase provideCityListUseCase(LocationRepository locationRepository, WeatherRepository weatherRepository) {
        return new CityListInteractor(locationRepository, weatherRepository);
    }

}
