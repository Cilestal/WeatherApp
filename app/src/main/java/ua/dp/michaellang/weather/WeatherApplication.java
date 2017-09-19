package ua.dp.michaellang.weather;

import android.app.Application;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
