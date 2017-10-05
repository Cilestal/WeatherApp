package ua.dp.michaellang.weather.presentation.inject.component;

import android.content.Context;
import dagger.Component;
import ua.dp.michaellang.weather.WeatherApplication;
import ua.dp.michaellang.weather.data.network.AccuWeatherService;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.data.repository.WeatherRepository;
import ua.dp.michaellang.weather.data.repository.entity.mapper.CountryMapper;
import ua.dp.michaellang.weather.presentation.inject.module.AppModule;
import ua.dp.michaellang.weather.presentation.inject.module.NetModule;
import ua.dp.michaellang.weather.presentation.inject.module.UtilsModule;
import ua.dp.michaellang.weather.presentation.navigation.Navigator;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Date: 23.09.2017
 *
 * @author Michael Lang
 */
@Singleton
@Component(modules = {AppModule.class, UtilsModule.class, NetModule.class})
public interface AppComponent {
    void inject(WeatherApplication weatherApplication);

    Navigator getNavigator();

    @Named("APP_CONTEXT")
    Context getAppContext();

    AssetsUtils getAssetsUtils();

    AccuWeatherService getAccuWeatherService();

    LocationRepository getLocationRepository();

    WeatherRepository getWeatherRepository();

    CountryMapper getCountryMapper();
}
