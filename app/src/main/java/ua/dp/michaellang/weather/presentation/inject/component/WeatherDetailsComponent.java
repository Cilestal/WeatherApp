package ua.dp.michaellang.weather.presentation.inject.component;

import dagger.Component;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.presentation.inject.module.ActivityModule;
import ua.dp.michaellang.weather.presentation.inject.module.WeatherDetailsModule;
import ua.dp.michaellang.weather.presentation.ui.activity.WeatherDetailsActivity;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {WeatherDetailsModule.class, ActivityModule.class})
public interface WeatherDetailsComponent {
    void inject(WeatherDetailsActivity activity);
}
