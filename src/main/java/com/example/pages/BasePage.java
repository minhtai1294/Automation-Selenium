package com.example.pages;

import com.example.utils.ElementActions;
import com.example.web.DriverFactory;

import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;
    protected ElementActions actions;

    // Base constructor — shared across all page objects
    public BasePage() {
        this.driver = DriverFactory.getDriver();        // get thread-safe driver
        this.actions = new ElementActions();            // init actions wrapper
    }

    // Optional: overload if you want to pass a custom WebDriver (rare)
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new ElementActions(); // still uses thread-local driver
    }


}
