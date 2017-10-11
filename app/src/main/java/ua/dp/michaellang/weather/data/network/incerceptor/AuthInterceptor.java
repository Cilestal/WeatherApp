package ua.dp.michaellang.weather.data.network.incerceptor;

import android.support.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ua.dp.michaellang.weather.data.network.ApiConstants;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class AuthInterceptor implements Interceptor {

    private String mApiKey;

    @Inject
    public AuthInterceptor(@Named("public_key") String mApiKey) {
        this.mApiKey = mApiKey;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();

        HttpUrl.Builder urlBuilder = url.newBuilder();
        urlBuilder.addEncodedQueryParameter(ApiConstants.QUERY_PARAM_API_KEY, mApiKey);

        url = urlBuilder.build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
