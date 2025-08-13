package com.example.tests.runner;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

public class DynamicTestNGBuilder {
    public static void main(String[] args) throws IOException {
        XmlSuite suite = new XmlSuite();
        suite.setName("DynamicSuite");
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        suite.setThreadCount(3);

        XmlTest test = new XmlTest(suite);
        test.setName("DynamicTest");
        test.setXmlClasses(Collections.singletonList(new XmlClass("tests.LoginTest")));

        String filePath = "./dynamic-testng.xml";
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(suite.toXml());
        }

        TestNG testng = new TestNG();
        testng.setTestSuites(Collections.singletonList(filePath));
        testng.run();
    }
}

