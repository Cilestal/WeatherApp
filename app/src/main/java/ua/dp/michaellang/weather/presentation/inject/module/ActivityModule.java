package ua.dp.michaellang.weather.presentation.inject.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;
import ua.dp.michaellang.weather.presentation.ui.adapter.SectionsPagerAdapter;

import javax.inject.Named;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
@Module
public class ActivityModule {
    private final AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    @Named("CONTEXT")
    Context provideContext(){
        return mActivity;
    }

    @Provides
    @ActivityScope
    AppCompatActivity provideActivity() {
        return this.mActivity;
    }

    @Provides
    @ActivityScope
    FragmentManager provideFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Provides
    @ActivityScope
    SectionsPagerAdapter provideSectionsPagerAdapter(FragmentManager fm) {
        return new SectionsPagerAdapter(fm);
    }
}
