package com.luckcheese.wheretoeat.model.app;

import com.luckcheese.wheretoeat.model.FourSquareResponse;
import com.luckcheese.wheretoeat.model.GeoPosition;
import com.luckcheese.wheretoeat.network.FourSquareApi;
import com.luckcheese.wheretoeat.network.FourSquareRequestParams;
import com.luckcheese.wheretoeat.network.NetworkManager;
import com.luckcheese.wheretoeat.network.RestaurantRequestParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class FourSquareManager {

    private Random chooser = new Random();
    private List<FourSquareResponse.GroupItem> allItems;

    private Listener listener;

    private FourSquareManager() {
    }

    public void getChosenRestaurant(Listener listener, GeoPosition position) {
        this.listener = listener;

        if (allItems == null) {
            fetchRestaurants(position);
        }
        else if (listener != null) {
            listener.setChosenRestaurant(chooseOne());
        }
    }

    private void fetchRestaurants(GeoPosition position) {
        FourSquareRequestParams restaurantRequestParams = new RestaurantRequestParams(position);
        FourSquareApi fourSquareApi = NetworkManager.getInstance().getFourSquareApi();
        Call<FourSquareResponse> request = fourSquareApi.getNearRestaurants(restaurantRequestParams.getParamsMap());

        request.enqueue(new Callback<FourSquareResponse>() {
            @Override
            public void onResponse(Response<FourSquareResponse> response, Retrofit retrofit) {
                allItems = mergeAllItems(response.body());

                if (listener != null) {
                    listener.setChosenRestaurant(chooseOne());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private List<FourSquareResponse.GroupItem> mergeAllItems(FourSquareResponse response) {
        List<FourSquareResponse.GroupItem> allItems = new ArrayList<>();
        List<FourSquareResponse.Group> groups = response.getResponse().getGroups();
        for (FourSquareResponse.Group group : groups) {
            allItems.addAll(group.getItems());
        }
        return allItems;
    }

    private FourSquareResponse.GroupItem chooseOne() {
        int random = Math.abs(chooser.nextInt()) % allItems.size();
        return allItems.get(random);
    }

    // ----- Singleton pattern ------------------------------------------------

    private static FourSquareManager instance;

    public static FourSquareManager getInstance() {
        if (instance == null) {
            instance = new FourSquareManager();
        }
        return instance;
    }

    // ----- Related classes --------------------------------------------------

    public interface Listener {
        void setChosenRestaurant(FourSquareResponse.GroupItem item);
    }
}
