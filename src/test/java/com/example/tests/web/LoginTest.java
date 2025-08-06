package com.example.tests.web;

import org.testng.annotations.Test;

import com.example.pages.HomePage;
import com.example.pages.LoginPage;

public class LoginTest extends BaseTest {
    // test logic

    @Test(dataProvider = "loginData")
    public void testLogin1(String email, String password) {

        HomePage homePage = new HomePage();
        LoginPage loginPage = new LoginPage();

        log1().info("111");

        log1().info("Navigating to login page");
        homePage.navigateToLogin();

        log1().info("Logging in with email: " + email);
        loginPage.login(email, password);

        log1().info("Verify login successful");
        loginPage.verifyLoginSuccess();
    }

    // @Test(dataProvider = "loginData", dependsOnMethods = "testLogin1")
    // public void testLogin2(String email, String password) {
    //     log1().info("222");
    // }

    // @Test(dataProvider = "loginData", dependsOnMethods = "testLogin2")
    // public void testLogin3(String email, String password) {
    //     log1().info("333");
    // }

    // @Test(dataProvider = "loginData", dependsOnMethods = "testLogin3")
    // public void testLogin4(String email, String password) {
    //     log1().info("444");
    // }

    @Test(dataProvider = "loginData")
    public void testLogin5(String email, String password) {
        log1().info("555");
    }

    @Test(dataProvider = "loginData")
    public void testLogin6(String email, String password) {
        log1().info("666");
    }
}