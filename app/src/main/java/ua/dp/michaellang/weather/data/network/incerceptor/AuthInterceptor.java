/**
 * Copyright (C) 2016 Arturo Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
