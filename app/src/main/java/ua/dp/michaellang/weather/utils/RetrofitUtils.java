package ua.dp.michaellang.weather.utils;

import android.support.annotation.NonNull;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static ua.dp.michaellang.weather.Contants.API_URL;

/**
 * Date: 13.09.2017
 *
 * @author Michael Lang
 */
public class RetrofitUtils {
    private volatile static Retrofit sBaseRetrofit;

    private static Retrofit getBaseRetrofit() {
        if (sBaseRetrofit == null) {
            OkHttpClient client = new OkHttpClient();
            sBaseRetrofit = initRetrofit(client, API_URL);
        }

        return sBaseRetrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return getBaseRetrofit().create(serviceClass);
    }

    @NonNull
    private static Retrofit initRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }
}
