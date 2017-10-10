package ua.dp.michaellang.weather.presentation.inject.app;

import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.presentation.utils.AssetsUtils;

/**
 * Date: 23.09.2017
 *
 * @author Michael Lang
 */
@Module
public class UtilsModule {
    @Provides
    AssetsUtils provideAssertsUtils() {
        return new AssetsUtils();
    }
}
