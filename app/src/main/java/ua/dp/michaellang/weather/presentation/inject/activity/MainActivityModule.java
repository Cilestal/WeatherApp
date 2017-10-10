package ua.dp.michaellang.weather.presentation.inject.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.presentation.inject.fragment.CityListFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.inject.fragment.CountryListFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.inject.fragment.SearchByCountryFragmentSubcomponent;
import ua.dp.michaellang.weather.presentation.ui.activity.MainActivity;
import ua.dp.michaellang.weather.presentation.ui.adapter.SectionsPagerAdapter;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
@Module(subcomponents = {
        CityListFragmentSubcomponent.class,
        CountryListFragmentSubcomponent.class,
        SearchByCountryFragmentSubcomponent.class
})
public class MainActivityModule {
    @Provides
    SectionsPagerAdapter provideSectionsPagerAdapter(FragmentManager fm) {
        return new SectionsPagerAdapter(fm);
    }

    @Provides AppCompatActivity provideAppCompatActivity(MainActivity activity){
        return activity;
    }
}
