package com.example.tests.api;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.api.constants.ApiEndpoints;

import io.restassured.response.Response;

public class TestSample extends BaseAPITest {

    @Test
    public void testGetPokemonAbility() {

        log().info("Testing GET Pokemon Ability API");
        Response response = apiClient.get().call(ApiEndpoints.GET_POKEMON_ABILITY, "battle-armor");

        Assert.assertEquals(response.statusCode(), 200);
    }

}
