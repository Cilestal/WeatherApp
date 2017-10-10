package ua.dp.michaellang.weather.presentation.inject.app;

import android.app.Activity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;
import ua.dp.michaellang.weather.presentation.inject.activity.MainActivitySubcomponent;
import ua.dp.michaellang.weather.presentation.inject.activity.WeatherDetailsActivitySubcomponent;
import ua.dp.michaellang.weather.presentation.ui.activity.MainActivity;
import ua.dp.michaellang.weather.presentation.ui.activity.WeatherDetailsActivity;

/**
 * Date: 07.10.2017
 *
 * @author Michael Lang
 */
@Module
public abstract class ActivityBuilderModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivity(MainActivitySubcomponent.Builder builder);

    @Binds
    @IntoMap
    @ActivityKey(WeatherDetailsActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindDetailActivity(WeatherDetailsActivitySubcomponent.Builder builder);
}
