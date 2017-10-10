package ua.dp.michaellang.weather.presentation.inject.fragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import ua.dp.michaellang.weather.presentation.ui.fragment.CountryListDialog;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Subcomponent(modules = CountryListModule.class)
public interface CountryListFragmentSubcomponent extends AndroidInjector<CountryListDialog>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<CountryListDialog> {
    }
}
