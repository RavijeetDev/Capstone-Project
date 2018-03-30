package com.ravijeet.teleportal.api;

import com.ravijeet.teleportal.util.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ravijeet on 2/27/18.
 */

public class Api {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.API_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
