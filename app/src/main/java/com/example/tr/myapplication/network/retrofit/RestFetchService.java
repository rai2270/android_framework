package com.example.tr.myapplication.network.retrofit;

import com.example.tr.myapplication.model.data.serverjson.InfoJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestFetchService {

    @GET("/posts")
    Call<List<InfoJson>> getPosts();
}
