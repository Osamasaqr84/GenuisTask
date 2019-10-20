package com.noname.genuisplaza.datalayer.apidata;

import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;


public class ApiClient {

    private static final String BASE_URL = "https://api.github.com/users/";
    private static final int TIMEOUT = 30;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Request originalRequest = chain.request();
                        Request.Builder builders = originalRequest.newBuilder();
                        builders.addHeader("Accept", "application/json");
                        builders.addHeader("Content-Type", "application/json");
                        Request newRequest = builders.build();
                        return chain.proceed(newRequest);
                    });

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(interceptor);
                httpClient.addNetworkInterceptor(new StethoInterceptor());
            }

            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit;
    }
    public static <S> S logService(Class<S> serviceClass) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
            httpClient.addNetworkInterceptor(new StethoInterceptor());
        }

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}