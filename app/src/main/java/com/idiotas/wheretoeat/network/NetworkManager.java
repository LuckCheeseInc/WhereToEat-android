package com.idiotas.wheretoeat.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class NetworkManager {

    private static NetworkManager sInstance;

    private final FourSquareApi fourSquareApi;

    public static NetworkManager getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkManager();
        }
        return sInstance;
    }

    private NetworkManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fourSquareApi = retrofit.create(FourSquareApi.class);
    }

    public FourSquareApi getFourSquareApi() {
        return fourSquareApi;
    }
}
