package ua.dp.michaellang.weather.presentation.inject.app;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.data.network.AccuWeatherService;
import ua.dp.michaellang.weather.data.repository.LocationRepository;
import ua.dp.michaellang.weather.data.repository.LocationRepositoryImpl;
import ua.dp.michaellang.weather.data.repository.WeatherRepository;
import ua.dp.michaellang.weather.data.repository.WeatherRepositoryImpl;
import ua.dp.michaellang.weather.data.repository.entity.mapper.CountryMapper;
import ua.dp.michaellang.weather.data.repository.entity.mapper.FavoriteCityMapper;
import ua.dp.michaellang.weather.presentation.inject.activity.MainActivitySubcomponent;
import ua.dp.michaellang.weather.presentation.inject.activity.WeatherDetailsActivitySubcomponent;
import ua.dp.michaellang.weather.presentation.navigation.Navigator;

import javax.inject.Named;
import javax.inject.Singleton;

import static ua.dp.michaellang.weather.Contants.APP_CONTEXT;

/**
 * Date: 23.09.2017
 *
 * @author Michael Lang
 */
@Module(subcomponents = {
        MainActivitySubcomponent.class,
        WeatherDetailsActivitySubcomponent.class
})
public class AppModule {

    @Provides
    @Singleton
    @Named(APP_CONTEXT)
    Context provideApplicationContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    CountryMapper provideCountryMapper() {
        return new CountryMapper();
    }

    @Provides
    @Singleton
    FavoriteCityMapper provideFavoriteCityMapper() {
        return new FavoriteCityMapper();
    }

    @Provides
    @Singleton
    LocationRepository provideCountryListRepository(CountryMapper mapper, FavoriteCityMapper fm, AccuWeatherService service) {
        return new LocationRepositoryImpl(mapper, fm, service);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherRepository(AccuWeatherService service) {
        return new WeatherRepositoryImpl(service);
    }

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

}
