package com.example.tests.modules.login.web.loginTest2;

import org.testng.annotations.Test;

import com.example.base_test.BaseTest;
import com.example.pages.HomePage;
import com.example.pages.LoginPage;
import com.example.tests.executions.TestMeta;

public class LoginTest2 extends BaseTest {
    // test logic

    @Test(dataProvider = "loginData")
    @TestMeta(feature = "Login")
    public void testLogin1(String email, String password) {

        HomePage homePage = new HomePage();
        LoginPage loginPage = new LoginPage();

        log().info("aaa111");

        log().info("Navigating to login page");
        homePage.navigateToLogin();

        log().info("Logging in with email: " + email);
        loginPage.login(email, password);

        log().info("Verify login successful");
        loginPage.verifyLoginSuccess();
    }

    // @Test(dataProvider = "loginData", dependsOnMethods = "testLogin1")
    // public void testLogin2(String email, String password) {
    //     log().info("222");
    // }

    // @Test(dataProvider = "loginData", dependsOnMethods = "testLogin2")
    // public void testLogin3(String email, String password) {
    //     log().info("333");
    // }

    // @Test(dataProvider = "loginData", dependsOnMethods = "testLogin3")
    // public void testLogin4(String email, String password) {
    //     log().info("444");
    // }

    @Test(dataProvider = "loginData")
    public void testLogin5(String email, String password) {
        log().info("555");
    }

    @Test(dataProvider = "loginData")
    public void testLogin6(String email, String password) {
        log().info("666");
    }
}