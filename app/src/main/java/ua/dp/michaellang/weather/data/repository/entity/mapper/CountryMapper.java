package ua.dp.michaellang.weather.data.repository.entity.mapper;

import ua.dp.michaellang.weather.data.entity.Location.Region;
import ua.dp.michaellang.weather.data.repository.entity.RealmCountry;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
public class CountryMapper implements Mapper<RealmCountry, Region> {

    @Override
    public Region map(RealmCountry realmCountry) {
        Region region = new Region();
        region.setId(realmCountry.getId());
        region.setLocalizedName(realmCountry.getLocalizedName());
        region.setEnglishName(realmCountry.getEnglishName());
        return region;
    }
}
