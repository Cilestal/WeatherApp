package ua.dp.michaellang.weather.presentation.inject.activity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import ua.dp.michaellang.weather.presentation.ui.activity.MainActivity;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
@Subcomponent(modules = {
        MainActivityModule.class,
        ActivityModule.class,
        FragmentBuilderModule.class
})
public interface MainActivitySubcomponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {}
}
