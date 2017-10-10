package ua.dp.michaellang.weather.presentation.inject.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.data.repository.WeatherRepository;
import ua.dp.michaellang.weather.domain.interactor.WeatherDetailsInteractor;
import ua.dp.michaellang.weather.domain.usecase.WeatherDetailsUseCase;
import ua.dp.michaellang.weather.presentation.presenter.WeatherDetailsPresenter;
import ua.dp.michaellang.weather.presentation.presenter.WeatherDetailsPresenterImpl;
import ua.dp.michaellang.weather.presentation.ui.activity.WeatherDetailsActivity;
import ua.dp.michaellang.weather.presentation.ui.adapter.DailyWeatherAdapter;
import ua.dp.michaellang.weather.presentation.ui.adapter.HourlyWeatherAdapter;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;
import ua.dp.michaellang.weather.presentation.view.WeatherDetailsView;

import javax.inject.Named;

import static ua.dp.michaellang.weather.Contants.ACTIVITY_CONTEXT;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Module
public class WeatherDetailsModule {

    @Provides
    WeatherDetailsView provideMainView(WeatherDetailsActivity activity){
        return activity;
    }

    @Provides
    WeatherDetailsPresenter provideWeatherDetailsPresenter(WeatherDetailsView view, WeatherDetailsUseCase interactor) {
        return new WeatherDetailsPresenterImpl(interactor, view);
    }

    @Provides
    WeatherDetailsUseCase provideWeatherDetailsUseCase(LocationRepository repository, WeatherRepository weatherRepository) {
        return new WeatherDetailsInteractor(repository, weatherRepository);
    }

    @Provides
    DailyWeatherAdapter provideDailyWeatherAdapter(@Named(ACTIVITY_CONTEXT) Context context, AssetsUtils assetsUtils) {
        return new DailyWeatherAdapter(context, assetsUtils);
    }

    @Provides
    HourlyWeatherAdapter provideHourlyWeatherAdapter(@Named(ACTIVITY_CONTEXT) Context context) {
        return new HourlyWeatherAdapter(context);
    }

    @Provides
    AppCompatActivity provideAppCompatActivity(WeatherDetailsActivity activity){
        return activity;
    }
}
