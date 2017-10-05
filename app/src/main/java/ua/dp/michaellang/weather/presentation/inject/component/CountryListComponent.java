package ua.dp.michaellang.weather.presentation.inject.component;

import dagger.Component;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.presentation.inject.module.CountryListModule;
import ua.dp.michaellang.weather.presentation.inject.module.FragmentModule;
import ua.dp.michaellang.weather.presentation.ui.fragment.CountryListDialog;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {CountryListModule.class, FragmentModule.class})
public interface CountryListComponent {
    void inject(CountryListDialog dialog);
}
