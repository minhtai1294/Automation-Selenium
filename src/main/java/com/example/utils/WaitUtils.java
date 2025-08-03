package com.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static final int DEFAULT_TIMEOUT = 10;
    private static final int VISIBLE_TIMEOUT = 10;

    // Wait until element located by 'By' is visible
    public static WebElement waitUntilVisible(By locator) {
        WebDriver driver = DriverFactory.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(VISIBLE_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Overloaded: Wait until element located by 'By' is visible within custom timeout
    public static WebElement waitUntilVisible(By locator, int timeoutInSeconds) {
        WebDriver driver = DriverFactory.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait until element is clickable
    public static WebElement waitUntilClickable(By locator) {
        WebDriver driver = DriverFactory.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Overloaded: Wait until clickable with custom timeout
    public static WebElement waitUntilClickable(By locator, int timeoutInSeconds) {
        WebDriver driver = DriverFactory.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Optional: Wait until presence in DOM, not just visible
    public static WebElement waitUntilPresent(By locator) {
        WebDriver driver = DriverFactory.getDriver();
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
