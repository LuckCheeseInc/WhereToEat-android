package com.luckcheese.wheretoeat.network;

import java.util.HashMap;
import java.util.Map;

public class FourSquareRequestParams {

    private String clientId = "4TIYFJREMLI1RTSLCRNOR4N5B3RZFPOUDYUIDXOKFFFZQKEW";
    private String clientSecret = "N3T43KXZHEQZUWDURVLCWU4BNPITGQIRXAMOUWSORDYVT4UT";
    private String version = "20151012";

    public Map<String, String> getParamsMap() {
        Map<String, String> map = new HashMap<>();

        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("v", version);

        return map;
    }
}
