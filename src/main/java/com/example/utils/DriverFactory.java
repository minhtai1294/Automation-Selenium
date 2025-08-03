package com.example.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String BASE_URL = "https://opencart.abstracta.us";
    private static final boolean IS_ACCEPT_INSECURE_CERTS = true; // temporary solution to handle insecure certs

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void init() {
        String browser = System.getProperty("browser", "chrome"); // default to chrome

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setAcceptInsecureCerts(IS_ACCEPT_INSECURE_CERTS);
                driver.set(new ChromeDriver(chromeOptions));
                break;
            case "firefox":

                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(IS_ACCEPT_INSECURE_CERTS);
                driver.set(new FirefoxDriver(firefoxOptions));
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        getDriver().manage().window().maximize();
        getDriver().get(BASE_URL); // open the URL here
    }

    public static void quit() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
