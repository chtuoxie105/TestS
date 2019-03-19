package com.example.testwifi.task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITestRetrofit {
    @GET("userid/{id}/token")
    Callback<String> getDataRequest(@Path("id") String id, @Query("token") String token);


    @GET("info/")
    Callback<ScrollAdapter> getInfo(@Path("id") String id);

    @GET(API.HOST + API.ABOUT_VERSION_UPDATE)
    Call<AboutVersionBean> getAbout();
}
