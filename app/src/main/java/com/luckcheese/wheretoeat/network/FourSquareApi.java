package com.luckcheese.wheretoeat.network;

import com.luckcheese.wheretoeat.model.FourSquareResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.QueryMap;

public interface FourSquareApi {

    @GET("/v2/venues/explore")
    Call<FourSquareResponse> getNearRestaurants(@QueryMap Map<String, String> options);
}
