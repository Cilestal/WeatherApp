package ua.dp.michaellang.weather.presentation.inject.module;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.data.repository.WeatherRepository;
import ua.dp.michaellang.weather.domain.interactor.WeatherDetailsInteractor;
import ua.dp.michaellang.weather.domain.usecase.WeatherDetailsUseCase;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.presentation.presenter.WeatherDetailsPresenter;
import ua.dp.michaellang.weather.presentation.presenter.WeatherDetailsPresenterImpl;
import ua.dp.michaellang.weather.presentation.ui.adapter.DailyWeatherAdapter;
import ua.dp.michaellang.weather.presentation.ui.adapter.HourlyWeatherAdapter;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;
import ua.dp.michaellang.weather.presentation.view.WeatherDetailsView;

import javax.inject.Named;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Module
public class WeatherDetailsModule {
    private WeatherDetailsView mView;
    private String mCityKey;

    public WeatherDetailsModule(WeatherDetailsView view, String cityKey) {
        mView = view;
        mCityKey = cityKey;
    }

    @Provides
    @ActivityScope
    WeatherDetailsPresenter provideWeatherDetailsPresenter(WeatherDetailsUseCase interactor){
        return new WeatherDetailsPresenterImpl(interactor, mView, mCityKey);
    }

    @Provides
    @ActivityScope
    WeatherDetailsUseCase provideWeatherDetailsUseCase(LocationRepository repository, WeatherRepository weatherRepository){
        return new WeatherDetailsInteractor(repository, weatherRepository);
    }

    @Provides
    @ActivityScope
    DailyWeatherAdapter provideDailyWeatherAdapter(@Named("CONTEXT") Context context, AssetsUtils assetsUtils){
        return new DailyWeatherAdapter(context, assetsUtils);
    }

    @Provides
    @ActivityScope HourlyWeatherAdapter provideHourlyWeatherAdapter(@Named("CONTEXT") Context context){
        return new HourlyWeatherAdapter(context);
    }
}
