package com.example.tests.web;

import com.example.utils.LogUtils;
import com.example.web.DriverFactory;

import java.lang.reflect.Method;

import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

@Listeners(com.example.reports.ExtentTestListener.class)
public class BaseTest {
    public static ThreadLocal<LogUtils> log = new ThreadLocal<>();

    public LogUtils log() {
        return log.get();
    }

    @BeforeMethod
    public void setup(Method method) {
        String testName = method.getName(); // or use result.getMethod().getMethodName()
        ThreadContext.put("testName", testName);
        log.set(new LogUtils());
        log().info("===== Test started: " + method.getName() + " =====");
        DriverFactory.init();
    }

    @AfterMethod
    public void teardown(Method method) {
        ThreadContext.clearAll();
        log().info("===== Test finished: " + method.getName() + " =====");
        DriverFactory.quit();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            { "mtaihrtnf12@gmail.com", "12345678Ac!" }
        };
    }
}
