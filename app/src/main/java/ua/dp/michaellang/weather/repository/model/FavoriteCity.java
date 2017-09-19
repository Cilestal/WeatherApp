package ua.dp.michaellang.weather.repository.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Date: 19.09.2017
 *
 * @author Michael Lang
 */
public class FavoriteCity extends RealmObject {
    @PrimaryKey
    private String key;
    private String name;
    private String areaName;
    private int population;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
