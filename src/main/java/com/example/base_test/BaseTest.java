package com.example.base_test;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import com.example.utils.LogUtils;
import com.example.web.DriverFactory;

@Listeners(com.example.reports.ExtentTestListener.class)
public class BaseTest {
    public static ThreadLocal<LogUtils> log = new ThreadLocal<>();
    public static final Logger log1 = LogManager.getLogger(BaseTest.class);

    public LogUtils log() {
        return log.get();
    }

    // public Logger log1() {
    //     return log1.get();
    // }

    @BeforeMethod
    public void setup(Method method) {
        String testName = method.getName(); // or use result.getMethod().getMethodName()
        ThreadContext.put("testName", testName);
        log.set(new LogUtils());
        log().info("===== Test started: " + method.getName() + " =====");
        // log1.set(LogManager.getLogger(BaseTest.class));
        // log1().info("===== Test started: " + method.getName() + " =====");
        DriverFactory.init();
    }

    @AfterMethod
    public void teardown(Method method) {
        ThreadContext.clearAll();
        // log1().info("===== Test finished: " + method.getName() + " =====");
        DriverFactory.quit();
    }

    @DataProvider(name = "loginData", parallel = true)
    public Object[][] loginData() {
        return new Object[][] {
            { "mtaihrtnf12@gmail.com", "12345678Ac!" }
        };
    }
}
