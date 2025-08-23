package com.example.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteLogger implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        System.out.println(">>> TestNG running suite: " + suite.getXmlSuite().getFileName());
    }
}