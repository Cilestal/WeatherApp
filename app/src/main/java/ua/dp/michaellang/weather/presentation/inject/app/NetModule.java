package ua.dp.michaellang.weather.presentation.inject.app;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.dp.michaellang.weather.BuildConfig;
import ua.dp.michaellang.weather.data.network.AccuWeatherService;
import ua.dp.michaellang.weather.data.network.incerceptor.AuthInterceptor;

import javax.inject.Singleton;

import static ua.dp.michaellang.weather.data.network.ApiConstants.API_URL;

/**
 * Date: 23.09.2017
 *
 * @author Michael Lang
 */
@Module
public class NetModule {

    @Provides
    @Singleton
    Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    CallAdapter.Factory provideCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(AuthInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideBaseRetrofit(OkHttpClient client,
            Converter.Factory converter, CallAdapter.Factory callAdapter) {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(converter)
                .addCallAdapterFactory(callAdapter)
                .baseUrl(API_URL)
                .build();
    }

    @Provides
    @Singleton
    AuthInterceptor provideAuthInterceptor() {
        return new AuthInterceptor(BuildConfig.ACCUWEATHER_API_KEY);
    }

    @Provides
    @Singleton
    AccuWeatherService provideAcctuWeatherService(Retrofit retrofit) {
        return retrofit.create(AccuWeatherService.class);
    }

}
