package ua.dp.michaellang.weather.repository;

/**
 * Date: 19.09.2017
 *
 * @author Michael Lang
 */
public interface Mapper<From, To> {
    To map(From from);
}
