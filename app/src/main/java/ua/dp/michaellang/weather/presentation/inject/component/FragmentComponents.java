package ua.dp.michaellang.weather.presentation.inject.component;

import dagger.Component;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.presentation.inject.module.FragmentModule;
import ua.dp.michaellang.weather.presentation.ui.fragment.SearchByCountryFragment;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {FragmentModule.class})
public interface FragmentComponents {
    void inject(SearchByCountryFragment fragment);
}
