package ua.dp.michaellang.weather.listener;

import ua.dp.michaellang.weather.network.model.Location.Region;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public interface OnCountrySelectedListener {
    void onCountrySelected(Region country);
}
