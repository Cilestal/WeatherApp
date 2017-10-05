package ua.dp.michaellang.weather.data.repository.entity.mapper;

import ua.dp.michaellang.weather.data.entity.Location.AdministrativeArea;
import ua.dp.michaellang.weather.data.entity.Location.City;
import ua.dp.michaellang.weather.data.entity.Location.Details;
import ua.dp.michaellang.weather.data.repository.entity.FavoriteCity;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
public class FavoriteCityMapper implements Mapper<FavoriteCity, City> {
    @Override
    public City map(FavoriteCity favoriteCity) {
        City city = new City();
        city.setLocalizedName(favoriteCity.getName());
        city.setKey(favoriteCity.getKey());
        Details details = new Details();
        details.setPopulation(favoriteCity.getPopulation());
        city.setDetails(details);

        AdministrativeArea administrativeArea = new AdministrativeArea();
        administrativeArea.setLocalizedName(favoriteCity.getAreaName());
        city.setAdministrativeArea(administrativeArea);

        return city;
    }
}
