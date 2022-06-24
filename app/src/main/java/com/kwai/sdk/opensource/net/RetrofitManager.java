package com.kwai.sdk.opensource.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
  private final HttpServiceApi mHttpServiceApi;
  private static final String SERVER_URL = "https://api.dev.al-array.com/demo/1.0/";

  private RetrofitManager() {
    Gson gson = new GsonBuilder()
        .setLenient()
        .create();
    Retrofit retrofit = new Retrofit.Builder().
        baseUrl(SERVER_URL).
        addConverterFactory(GsonConverterFactory.create(gson)).
        build();
    mHttpServiceApi = retrofit.create(HttpServiceApi.class);
  }

  private static class Holder {
    static final RetrofitManager INSTANCE = new RetrofitManager();
  }

  public static RetrofitManager getInstance() {
    return Holder.INSTANCE;
  }

  public HttpServiceApi getApi() {
    return mHttpServiceApi;
  }
}
