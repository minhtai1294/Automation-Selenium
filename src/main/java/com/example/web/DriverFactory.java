package com.example.web;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.example.configs.ConfigLoader;
import com.example.configs.TestProperties;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String BASE_URL = ConfigLoader.getOrDefault(TestProperties.BASE_URL.toString(), "http://localhost:8080");
    private static final boolean IS_ACCEPT_INSECURE_CERTS = true; // temporary solution to handle insecure certs

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void init() {
        String browser = ConfigLoader.getOrDefault(TestProperties.BROWSER.toString(), "chrome"); 
        String execution = ConfigLoader.getOrDefault(TestProperties.EXECUTION_TYPE.toString(), "local");                                                                                                   
        String headless = ConfigLoader.getOrDefault(TestProperties.IS_HEADLESS.toString(), "true");
        String hubUrl = ConfigLoader.getOrDefault(TestProperties.HUB_URL.toString(), "http://localhost:4444/wd/hub");


        boolean isRemote = execution.equalsIgnoreCase("remote");
        boolean isHeadless = Boolean.parseBoolean(headless);

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setAcceptInsecureCerts(IS_ACCEPT_INSECURE_CERTS);
                chromeOptions.setAcceptInsecureCerts(true);
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new"); // or "--headless" for older versions
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("window-size=1920,1080");
                }

                if (isRemote) {
                    try {
                        driver.set(new RemoteWebDriver(new URI(hubUrl).toURL(), chromeOptions));
                    } catch (URISyntaxException | MalformedURLException e) {
                        throw new RuntimeException("Invalid Selenium Grid URL", e);
                    }
                } else {
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver(chromeOptions));
                }
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setAcceptInsecureCerts(IS_ACCEPT_INSECURE_CERTS);

                if (isRemote) {
                    try {
                        driver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), firefoxOptions));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException("Invalid Selenium Grid URL", e);
                    }
                } else {
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver(firefoxOptions));
                }
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        getDriver().manage().window().maximize();
        getDriver().get(BASE_URL);
    }

    public static void quit() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
