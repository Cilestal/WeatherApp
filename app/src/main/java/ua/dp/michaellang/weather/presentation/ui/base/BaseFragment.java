package ua.dp.michaellang.weather.presentation.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import ua.dp.michaellang.weather.WeatherApplication;
import ua.dp.michaellang.weather.presentation.inject.component.AppComponent;
import ua.dp.michaellang.weather.presentation.inject.module.FragmentModule;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArgs();
        inject();
    }

    protected abstract void loadArgs();

    protected AppComponent getApplicationComponent() {
        return ((WeatherApplication) getActivity().getApplication()).getApplicationComponent();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected abstract void inject() throws IllegalStateException;
}
