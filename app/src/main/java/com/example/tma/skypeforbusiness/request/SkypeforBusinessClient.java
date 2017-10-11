package com.example.tma.skypeforbusiness.request;

import com.example.tma.skypeforbusiness.utils.InternalDataUtil;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by tmvien on 2/15/17.
 */

public class SkypeforBusinessClient {
    public static final String BASE_URL = "http://192.168.17.26:8080";
    public static final String SKYPE_BASE_URL = InternalDataUtil.getInstance().getServerAddress();
    private static OkHttpClient httpClient = new OkHttpClient();

    private static Retrofit retrofit = null;
    private static Retrofit retrofitSkype = null;

    static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    public static Retrofit getClient() {
        if (retrofit == null) {
            httpClient.setReadTimeout(1, TimeUnit.MINUTES);
            httpClient.setConnectTimeout(1, TimeUnit.MINUTES);
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
        }
        return retrofit;
    }

    public static Retrofit getServerSkype() {
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (retrofitSkype == null) {
            httpClient.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + InternalDataUtil.getInstance().getToken()).build();
                    return chain.proceed(request);
                }
            });
            httpClient.setReadTimeout(190, TimeUnit.SECONDS);
            httpClient.setConnectTimeout(190, TimeUnit.SECONDS);

            retrofitSkype = new Retrofit.Builder().baseUrl(SKYPE_BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient).build();
        }

        return retrofitSkype;
    }
}
