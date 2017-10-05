package ua.dp.michaellang.weather.data.network;

import ua.dp.michaellang.weather.BuildConfig;

/**
 * Date: 24.09.2017
 *
 * @author Michael Lang
 */
public interface ApiConstants {
    int AUTH_ERROR_CODE = 503;
    String API_URL = "http://apidev.accuweather.com/";

    String QUERY_PARAM_API_KEY = "apikey";

    String API_KEY = BuildConfig.ACCUWEATHER_API_KEY;
    String API_VERSION = "v1";
}
