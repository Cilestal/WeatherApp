package ua.dp.michaellang.weather.presentation.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import timber.log.Timber;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ua.dp.michaellang.weather.Contants.FLAGS_FILE_FORMAT;
import static ua.dp.michaellang.weather.Contants.WEATHER_ICONS_FILE_FORMAT;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
@Singleton
public class AssetsUtils {
    private static Map<Integer, Drawable> mWeatherIcons;
    private static Map<String, Drawable> mFlagIcons;

    public AssetsUtils() {
        mWeatherIcons = new ConcurrentHashMap<>();
        mFlagIcons = new ConcurrentHashMap<>();
    }

    @Nullable
    public Drawable getWeatherIcon(Context context, final int code) {
        if (mWeatherIcons.containsKey(code)) {
            return mWeatherIcons.get(code);
        }

        @SuppressLint("DefaultLocale")
        String path = String.format(WEATHER_ICONS_FILE_FORMAT, code);
        AssetManager assets = context.getAssets();

        try {
            Drawable drawable = Drawable.createFromStream(assets.open(path), null);
            mWeatherIcons.put(code, drawable);
            return drawable;
        } catch (IOException e) {
            Timber.d("Weather iconx %s not found.", code);
            return null;
        }
    }

    @Nullable
    public Drawable getFlag(Context context, final String code) {
        if (code == null || code.length() != 2) {
            return null;
        }

        if (mFlagIcons.containsKey(code)) {
            return mFlagIcons.get(code);
        }

        String flagPath = String.format(FLAGS_FILE_FORMAT, code.toLowerCase());
        AssetManager assets = context.getAssets();

        try {
            Drawable drawable = Drawable.createFromStream(assets.open(flagPath), null);
            mFlagIcons.put(code, drawable);
            return drawable;
        } catch (IOException e) {
            Timber.d("Flag %s not found.", code);
            return null;
        }
    }
}
