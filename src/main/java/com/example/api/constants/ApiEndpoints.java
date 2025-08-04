package com.example.api.constants;

public class ApiEndpoints {

    // Google APIs
    public static final ApiRoute GET_SUPPORTED_LANGUAGES = new ApiRoute("GET", "/v3beta1/{parent}/supportedLanguages");

    // pokeapi.co APIs
    public static final ApiRoute GET_POKEMON_ABILITY = new ApiRoute("GET", "/api/v2/ability/{abilityName}");

    
    
    //Localhost Task APIs
    public static final ApiRoute GET_TASKS = new ApiRoute("GET", "/tasks");
    public static final ApiRoute POST_TASKS = new ApiRoute("POST", "/tasks");

    // Auth APIs
    //public static final ApiRoute LOGIN = new ApiRoute("POST", "/auth/login");
}