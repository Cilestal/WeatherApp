package ua.dp.michaellang.weather.presentation.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

/**
 * Date: 25.09.2017
 *
 * @author Michael Lang
 */
public abstract class BaseDialogFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inject();
    }

/*    protected AppComponent getApplicationComponent() {
        return ((WeatherApplication) getActivity().getApplication()).getApplicationComponent();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected abstract void inject() throws IllegalStateException;*/
}
