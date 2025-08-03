package com.example.api.constants;

public class ApiRoute {
    private final String method;
    private final String path;

    public ApiRoute(String method, String path) {
        this.method = method.toUpperCase();
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String pathWithParams(String... values) {
        String result = path;
        for (String value : values) {
            result = result.replaceFirst("\\{[^}]+\\}", value);
        }
        return result;
    }

    @Override
    public String toString() {
        return method + " " + path;
    }
}

