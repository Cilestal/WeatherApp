package ua.dp.michaellang.weather;

/**
 * Date: 13.09.2017
 *
 * @author Michael Lang
 */
public interface Contants {
    String API_URL = "http://apidev.accuweather.com/";

    String API_KEY = BuildConfig.ACCUWEATHER_API_KEY;
    String API_VERSION = "v1";

    String WEATHER_ICONS_FILE_FORMAT = "weather_icons/%02d.png";
    String FLAGS_FILE_FORMAT = "flags/%s.png";

    int AUTH_ERROR_CODE = 503;
}
