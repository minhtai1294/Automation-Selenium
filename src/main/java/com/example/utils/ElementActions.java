package com.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.example.web.DriverFactory;

public class ElementActions {

    // private static final LogUtils log = new LogUtils();
    private static final Logger log = LogManager.getLogger(ElementActions.class);
    private WebDriver driver;

    public ElementActions() {
        this.driver = DriverFactory.getDriver();
    }

    public void click(By locator) {
        try {
            WaitUtils.waitUntilVisible(locator);
            WaitUtils.waitUntilClickable(locator);
            driver.findElement(locator).click();
            log.info(String.format("Clicked element: %s", locator));
        } catch (Exception e) {
            log.error(String.format("Failed to %s element: %s", "click", locator), e);
            throw e;
        }
    }

    public void inputText(By locator, String text) {
        try {
            WaitUtils.waitUntilVisible(locator);
            WaitUtils.waitUntilClickable(locator);
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
            log.info(String.format("Entered text '%s' into element: %s", text, locator));
        } catch (Exception e) {
            log.error(String.format("Failed to %s element: %s", "input text", locator), e);
            throw e;
        }
    }

    public String getText(By locator) {
        try {
            WaitUtils.waitUntilVisible(locator);
            String text = driver.findElement(locator).getText();
            log.info(String.format("Retrieved text '%s' from element: %s", text, locator));
            return text;
        } catch (Exception e) {
            log.error(String.format("Failed to %s element: %s", "get text", locator), e);
            throw e;
        }
    }

    public boolean isVisible(By locator) {
        try {
            WaitUtils.waitUntilVisible(locator);
            log.info(String.format("Visibility of element %s: %s", locator, true));
            return true;
        } catch (Exception e) {
            log.error(String.format("Failed to %s element: %s", "find visibility of", locator), e);
            return false;
        }
    }

    public void switchToFrame(By locator) {
        WebElement iframe = driver.findElement(locator);
        driver.switchTo().frame(iframe);
        log.info(String.format("Switched to iframe: %s", locator));
    }

    public void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
        log.info(String.format("Switched to iframe with name or ID: %s", nameOrId));
    }

    public void switchToFrame(int index) {
        driver.switchTo().frame(index);
        log.info(String.format("Switched to iframe by index: %s", index));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        log.info("Switched back to default content.");
    }

    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
        log.info("Switched to parent frame.");
    }
}
