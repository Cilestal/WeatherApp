package ua.dp.michaellang.weather.view;

import ua.dp.michaellang.weather.network.model.Location.Region;

import java.util.List;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public interface CountryListView extends BaseView {
    void onCountryListLoaded(List<Region> data);
}
