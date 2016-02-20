package com.luckcheese.wheretoeat.network;

import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import okio.Buffer;
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
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                String body = "";
                if (request.body() != null) {
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    body = buffer.readUtf8();
                }
                Log.d("NetworkLog", String.format("Url: %s\n\theaders: %s\n\trequest: %s", request.urlString(), request.headers(), body));


                Response response = chain.proceed(request);

                String bodyString = response.body().string();
                Log.d("NetworkLog", String.format("Url: %s\n\tresponse: %s", request.urlString(), bodyString));
                return response.newBuilder()
                        .body(ResponseBody.create(response.body().contentType(), bodyString))
                        .build();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.foursquare.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        fourSquareApi = retrofit.create(FourSquareApi.class);
    }

    public FourSquareApi getFourSquareApi() {
        return fourSquareApi;
    }
}
