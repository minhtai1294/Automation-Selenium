package com.example.tests.api;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.example.api.ApiRequestFactory;
import com.example.api.constants.ApiEndpoints;
import com.example.utils.WaitUtils;

import io.restassured.response.Response;

public class TestAPISample extends BaseAPITest {

    @Test
    public void testGetPokemonAbility() {

        ApiRequestFactory apiClient = new ApiRequestFactory();
        log().info("Testing GET Pokemon Ability API");
        apiClient.setBaseUrl("https://pokeapi.co");
        Response response = apiClient.call(ApiEndpoints.GET_POKEMON_ABILITY, "battle-armor");

        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test
    public void testLocalHostApiGetTasks() {

        ApiRequestFactory apiClient = new ApiRequestFactory();
        log().info("Testing GET tasks API on localhost");
        Response response = apiClient.call(ApiEndpoints.GET_TASKS);
        log().info(response.asPrettyString());
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Test(dependsOnMethods = "testLocalHostApiGetTasks")
    public void testLocalHostApiGetTasksWithRateLimit5Requests() {

        ApiRequestFactory apiClient = new ApiRequestFactory();
        WaitUtils.waitForSeconds(20);
        log().info("Testing GET tasks API on localhost with rate limit of 5 requests");
        for (int i = 0; i < 6; i++) {
            Response response = apiClient.call(ApiEndpoints.GET_TASKS);
            log().info(response.getStatusCode() + " " + response.asPrettyString());
            if (i == 5) {
                log().info("This is the 6th request, expecting rate limit exceeded");
                Assert.assertEquals(response.statusCode(), 429);
            } else
                Assert.assertEquals(response.statusCode(), 200);
        }
    }

}
