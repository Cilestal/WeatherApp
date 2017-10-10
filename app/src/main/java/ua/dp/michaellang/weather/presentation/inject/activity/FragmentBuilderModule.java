package ua.dp.michaellang.weather.presentation.inject.activity;

import android.support.v4.app.Fragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import ua.dp.michaellang.weather.presentation.inject.fragment.CityListFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.inject.fragment.CountryListFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.inject.fragment.SearchByCountryFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.ui.fragment.CityListFragment;
import ua.dp.michaellang.weather.presentation.ui.fragment.CountryListDialog;
import ua.dp.michaellang.weather.presentation.ui.fragment.SearchByCountryFragment;

/**
 * Date: 07.10.2017
 *
 * @author Michael Lang
 */
@Module
public abstract class FragmentBuilderModule {
    @Binds
    @IntoMap
    @FragmentKey(CityListFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> provideCityListFragmentComponent(CityListFragmentSubcomponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(CountryListDialog.class)
    abstract AndroidInjector.Factory<? extends Fragment> provideCountryListFragmentFactory(CountryListFragmentSubcomponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(SearchByCountryFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> provideSearchByCountryFragmentComponent(SearchByCountryFragmentSubcomponent.Builder builder);

}
