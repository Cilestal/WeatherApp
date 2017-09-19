package ua.dp.michaellang.weather.view;

import android.support.annotation.StringRes;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public interface BaseView {
    void onError(@StringRes int stringResId);
}
