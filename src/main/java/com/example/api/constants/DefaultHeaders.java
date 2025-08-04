package com.example.api.constants;


import java.util.HashMap;
import java.util.Map;

import com.example.configs.TestProperties;
import com.example.utils.ConfigLoader;

public class DefaultHeaders {

    public static Map<String, String> get() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        String token = ConfigLoader.get(TestProperties.API_TOKEN.toString()); // or from ConfigLoader
        if (token != null && !token.isEmpty()) {
            headers.put("Authorization", "Bearer " + token);
        }

        return headers;
    }
}
