package ua.dp.michaellang.weather.presentation.inject.fragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import ua.dp.michaellang.weather.presentation.ui.fragment.SearchByCountryFragment;

/**
 * Date: 08.10.2017
 *
 * @author Michael Lang
 */
@Subcomponent
public interface SearchByCountryFragmentSubcomponent extends AndroidInjector<SearchByCountryFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<SearchByCountryFragment> {
    }
}
