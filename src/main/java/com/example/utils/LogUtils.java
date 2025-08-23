package com.example.utils;

import com.aventstack.extentreports.Status;
import com.example.utils.reports.ExtentTestListener;
import com.aventstack.extentreports.ExtentTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    private static final Logger log = LogManager.getLogger(LogUtils.class);

    private static ExtentTest getExtentTest() {
        return ExtentTestListener.getTest(); // expose this as public
    }

    public void info(String message) {
        log.info(message);
        if (getExtentTest() != null) {
            getExtentTest().log(Status.INFO, message);
        }
    }

    public void warn(String message) {
        log.warn(message);
        if (getExtentTest() != null) {
            getExtentTest().log(Status.WARNING, message);
        }
    }

    public void pass(String message) {
        log.info("[PASS] " + message);
        if (getExtentTest() != null) {
            getExtentTest().log(Status.PASS, message);
        }
    }

    public void fail(String message) {
        log.error("[FAIL] " + message);
        if (getExtentTest() != null) {
            getExtentTest().log(Status.FAIL, message);
        }
    }

    public void error(String message, Throwable t) {
        log.error(message, t);
        if (getExtentTest() != null) {
            getExtentTest().log(Status.FAIL, message);
        }
        StackTraceElement[] trace = t.getStackTrace();
        for (int i = 0; i < Math.min(5, trace.length); i++) {
            getExtentTest().log(Status.FAIL, "at " + trace[i]);
        }
    }

    public void skip(String message) {
        log.warn("[SKIP] " + message);
        if (getExtentTest() != null) {
            getExtentTest().log(Status.SKIP, message);
        }
    }
}
