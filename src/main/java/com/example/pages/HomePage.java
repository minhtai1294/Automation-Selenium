package com.example.pages;

import org.openqa.selenium.By;

public class HomePage extends BasePage {

    // Locators
    private final By myAccountDropdown = By.xpath("//span[text()='My Account']");
    private final By logoutLink = By.linkText("Logout");
    private final By searchBox = By.name("search");
    private final By searchButton = By.cssSelector("div[id='search'] button");
    private final By loginButton = By.linkText("Login");

    // Constructor
    public HomePage() {
        super(); // calls BasePage constructor
    }

    // Page Actions
    public void openMyAccountMenu() {
        actions.click(myAccountDropdown);
    }

    public void navigateToLogin() {
        openMyAccountMenu();
        actions.click(loginButton);
    }

    public void logout() {
        openMyAccountMenu();
        actions.click(logoutLink);
    }

    public void searchForProduct(String keyword) {
        actions.inputText(searchBox, keyword);
        actions.click(searchButton);
    }
}
