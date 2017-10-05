package ua.dp.michaellang.weather;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;
import io.realm.Realm;
import timber.log.Timber;
import ua.dp.michaellang.weather.presentation.inject.component.AppComponent;
import ua.dp.michaellang.weather.presentation.inject.component.DaggerAppComponent;
import ua.dp.michaellang.weather.presentation.inject.module.AppModule;
import ua.dp.michaellang.weather.presentation.inject.module.NetModule;
import ua.dp.michaellang.weather.presentation.inject.module.UtilsModule;

import static ua.dp.michaellang.weather.data.network.ApiConstants.API_URL;

/**
 * Date: 14.09.2017
 *
 * @author Michael Lang
 */
public class WeatherApplication extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        initializeInjector();
        initializeLeakDetection();
        initializeTimber();
    }

    private void initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initializeInjector() {
        this.mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(API_URL))
                .utilsModule(new UtilsModule())
                .build();
    }

    public AppComponent getApplicationComponent() {
        return mAppComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
    }
}
