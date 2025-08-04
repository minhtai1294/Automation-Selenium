package com.example.api;

import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import com.example.api.constants.ApiRoute;
import com.example.configs.*;
import com.example.utils.LogUtils;

public class ApiRequestFactory {

    private String token;
    private Object body;
    private Map<String, String> headers;
    private Map<String, Object> queryParams;
    LogUtils log = new LogUtils();

    private String API_BASE_URL = ConfigLoader.get(TestProperties.API_BASE_URL.toString()) != null ? ConfigLoader.get(TestProperties.API_BASE_URL.toString())
            : "https://pokeapi.co";

    public void setBaseUrl(String baseUrl) {
        API_BASE_URL = baseUrl;
    }
    
            public ApiRequestFactory withToken(String token) {
        this.token = token;
        return this;
    }

    public ApiRequestFactory withBody(Object body) {
        this.body = body;
        return this;
    }

    public ApiRequestFactory withHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public ApiRequestFactory withQueryParams(Map<String, Object> params) {
        this.queryParams = params;
        return this;
    }

    private RequestSpecification build() {
        RequestSpecification req = RestAssured
                .given()
                .baseUri(API_BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        if (token != null) {
            req.header("Authorization", "Bearer " + token);
        }

        if (headers != null) {
            req.headers(headers);
        }

        if (body != null) {
            req.body(body);
        }

        if (queryParams != null) {
            req.queryParams(queryParams);
        }

        return req;
    }

    // 🔥 Call using ApiRoute
    public Response call(ApiRoute route, String... pathParams) {
        String finalPath = route.pathWithParams(pathParams);
        log.info("Calling API: \n" + toCurl(route.getMethod(), API_BASE_URL + finalPath));
        return call(route.getMethod(), finalPath);
    }

    // Raw method + path
    public Response call(String method, String endpoint) {
        RequestSpecification request = build();

        switch (method.toUpperCase()) {
            case "GET":
                return request.get(endpoint);
            case "POST":
                return request.post(endpoint);
            case "PUT":
                return request.put(endpoint);
            case "DELETE":
                return request.delete(endpoint);
            case "PATCH":
                return request.patch(endpoint);
            default:
                throw new IllegalArgumentException("Unsupported method: " + method);
        }
    }

    public String toCurl(String method, String fullUrl) {
        StringBuilder curl = new StringBuilder("curl -X ").append(method.toUpperCase()).append(" '").append(fullUrl)
                .append("'");

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                curl.append(" \\\n  -H '").append(entry.getKey()).append(": ").append(entry.getValue()).append("'");
            }
        }

        if (body != null) {
            String bodyStr = body.toString().replace("'", "\\'");
            curl.append(" \\\n  -d '").append(bodyStr).append("'");
        }

        return curl.toString();
    }

    public String getBaseUrl() {
        return API_BASE_URL;
    }
}
