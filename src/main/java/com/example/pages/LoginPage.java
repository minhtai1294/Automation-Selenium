package com.example.pages;

import org.openqa.selenium.By;

import com.example.utils.WaitUtils;

public class LoginPage extends BasePage {

    // Locators
    private final By emailInput = By.id("input-email");
    private final By passwordInput = By.id("input-password");
    private final By loginButton = By.cssSelector("input[type='submit']");
    private final By accountInfo = By.id("account-account");


    public LoginPage() {
        super();
    }

    public void login(String email, String password) {
        actions.inputText(emailInput, email);
        actions.inputText(passwordInput, password);
        actions.click(loginButton);
    }

    public void verifyLoginSuccess() {
        WaitUtils.waitUntilVisible(accountInfo);
    }

    // login logic
}