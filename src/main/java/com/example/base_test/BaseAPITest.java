package com.example.base_test;

import java.lang.reflect.Method;

import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.example.utils.LogUtils;

//@Listeners(com.example.reports.ExtentTestListener.class)
public class BaseAPITest {
    public static LogUtils log = new LogUtils();

    @BeforeMethod
    public void setup(Method method) {
        String testName = method.getName(); // or use result.getMethod().getMethodName()
        ThreadContext.put("testName", testName);
        log.info("===== Test started: " + method.getName() + " =====");
    }

    @AfterMethod
    public void teardown(Method method) {
        log.info("===== Test finished: " + method.getName() + " =====");
        ThreadContext.clearAll();
    }

    @DataProvider(name = "randomeTaskName")
    public Object[] loginData() {
            return new Object[] {"Random Task " + System.currentTimeMillis()}; 
        };
    
}
