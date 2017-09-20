package ua.dp.michaellang.weather.presenter;

/**
 * Date: 18.09.2017
 *
 * @author Michael Lang
 */
public interface WeatherDetailsPresenter extends BasePresenter {
    void loadWeather();

    void addToFavorite();
    void checkIsFavorite();
    void removeFromFavorite();
}
