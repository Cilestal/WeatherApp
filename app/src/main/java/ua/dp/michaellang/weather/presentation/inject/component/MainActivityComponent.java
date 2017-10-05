package ua.dp.michaellang.weather.presentation.inject.component;

import dagger.Component;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.presentation.inject.module.ActivityModule;
import ua.dp.michaellang.weather.presentation.ui.activity.MainActivity;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
