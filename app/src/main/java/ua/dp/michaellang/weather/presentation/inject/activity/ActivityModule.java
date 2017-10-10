package ua.dp.michaellang.weather.presentation.inject.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

import static ua.dp.michaellang.weather.Contants.ACTIVITY_CONTEXT;

/**
 * Date: 07.10.2017
 *
 * @author Michael Lang
 */
@Module
public class ActivityModule {
    @Provides
    @Named(ACTIVITY_CONTEXT) Context provideContext(AppCompatActivity activity){
        return activity;
    }

    @Provides
    FragmentManager provideSupportFragmentManager(AppCompatActivity activity){
        return activity.getSupportFragmentManager();
    }
}
