package com.example.utils.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.*;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent = ExtentReportManager.getReporter();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
        removeTest();
    }

    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test failed: " + result.getThrowable());
        removeTest();
    }

    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped");
        removeTest();
    }

    public void onFinish(ITestContext context) {
        extent.flush(); // write everything to HTML
    }

    private void removeTest() {
        test.remove();
    }
}
