package ua.dp.michaellang.weather.presentation.inject.module;

import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

import javax.inject.Singleton;

/**
 * Date: 23.09.2017
 *
 * @author Michael Lang
 */
@Module
public class UtilsModule {
    @Provides
    @Singleton
    AssetsUtils provideAssertsUtils() {
        return new AssetsUtils();
    }
}
