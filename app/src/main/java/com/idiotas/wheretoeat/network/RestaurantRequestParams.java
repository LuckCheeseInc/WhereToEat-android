package com.idiotas.wheretoeat.network;

import com.idiotas.wheretoeat.model.GeoPosition;

import java.util.Map;

public class RestaurantRequestParams extends FourSquareRequestParams {

    private String section = "food";
    private boolean openNow = true;

    private GeoPosition position;

    public RestaurantRequestParams(GeoPosition position) {
        this.position = position;
    }

    @Override
    public Map<String, String> getParamsMap() {
        Map<String, String> map = super.getParamsMap();

        map.put("section", section);
        map.put("openNow", openNow ? "1" : "0");

        map.put("ll", String.format("%s,%s", position.getLat(), position.getLng()));

        return map;
    }
}
