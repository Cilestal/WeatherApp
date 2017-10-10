package ua.dp.michaellang.weather.presentation.presenter;

/**
 * Date: 15.09.2017
 *
 * @author Michael Lang
 */
public interface CityListPresenter extends BasePresenter {
    void loadCityList(String countryId);
    void loadCitiesWeather();

    void filterData(String query);
}
