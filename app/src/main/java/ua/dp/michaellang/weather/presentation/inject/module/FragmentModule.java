package ua.dp.michaellang.weather.presentation.inject.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import dagger.Module;
import dagger.Provides;
import ua.dp.michaellang.weather.presentation.inject.ActivityScope;

import javax.inject.Named;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @ActivityScope
    Fragment provideFragment(){
        return mFragment;
    }

    @Provides
    @ActivityScope
    @Named("CONTEXT")
    Context provideContext(){
        return mFragment.getContext();
    }
}
