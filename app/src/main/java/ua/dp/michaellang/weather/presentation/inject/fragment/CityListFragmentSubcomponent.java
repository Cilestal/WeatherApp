package ua.dp.michaellang.weather.presentation.inject.fragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import ua.dp.michaellang.weather.presentation.ui.fragment.CityListFragment;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Subcomponent(modules = CityListModule.class)
public interface CityListFragmentSubcomponent extends AndroidInjector<CityListFragment>{
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<CityListFragment>{}
}
