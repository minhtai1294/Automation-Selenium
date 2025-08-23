package com.example.base_test;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import com.example.web.DriverFactory;

@Listeners(com.example.utils.reports.ExtentTestListener.class)
public class BaseTest {
    public Logger log = LogManager.getLogger(this.getClass());

    @BeforeMethod
    public void setup(Method method) {
        String testName = method.getName(); // or use result.getMethod().getMethodName()
        ThreadContext.put("testName", testName);
        log.info("===== Test started: " + method.getName() + " =====");
        DriverFactory.init();
    }

    @AfterMethod
    public void teardown(Method method) {
        ThreadContext.clearAll();
        log.info("===== Test finished: " + method.getName() + " =====");
        DriverFactory.quit();
    }

    @DataProvider(name = "loginData", parallel = true)
    public Object[][] loginData() {
        return new Object[][] {
            { "mtaihrtnf12@gmail.com", "12345678Ac!" }
        };
    }
}
