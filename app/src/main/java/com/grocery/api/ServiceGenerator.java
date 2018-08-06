package com.grocery.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ServiceGenerator {
    public static APICommon.Grocery GetInstance() {
        final Gson gson = new GsonBuilder().serializeNulls().create();

        //HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
       // logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient();

        //okHttpClient.interceptors().add(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APICommon.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


        return retrofit.create(APICommon.Grocery.class);
    }

}
