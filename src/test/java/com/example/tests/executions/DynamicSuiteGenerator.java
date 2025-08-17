package com.example.tests.executions;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.example.configs.ConfigLoader;
import com.example.utils.LogUtils;

/**
 * Dynamically builds testng.xml based on env vars and @TestMeta annotation.
 */
public class DynamicSuiteGenerator {

    public static void main(String[] args) throws Exception {
        LogUtils log = new LogUtils();
        // ✅ Read filters from environment variables
        String suiteFilter = ConfigLoader.get("SUITE_NAME"); // e.g., "Login"
        String platformFilter = ConfigLoader.get("PLATFORM"); // e.g., "web"
        String featureFiter = ConfigLoader.get("FEATURE"); // e.g., "LoginFeature"
        String testFilePath = "src\\test\\resources\\dynamic-testng.xml";
        boolean featureMatch = false;
        List<XmlClass> xmlClasses = new ArrayList<>();
        String className;

        if (suiteFilter == null)
            suiteFilter = "ALL";
        if (platformFilter == null)
            platformFilter = "ALL";

        log.info("=== DynamicSuiteGenerator ===");
        log.info("Filter: Suite=" + suiteFilter + ", Platform=" + platformFilter);

        // ✅ Create new TestNG suite
        XmlSuite suite = new XmlSuite();
        suite.setName("DynamicSuite");
        suite.setParallel(XmlSuite.ParallelMode.CLASSES);
        suite.setThreadCount(3);

        XmlTest test = new XmlTest(suite);
        test.setName("DynamicGeneratedTests");

        // ✅ Scan test package
        String basePackage = "com.example.tests.modules." + suiteFilter.toLowerCase() + "."
                + platformFilter.toLowerCase();
        String basePath = "src/test/java/" + basePackage.replace(".", "/");
        File folder = new File(basePath);

        if (!folder.exists()) {
            throw new RuntimeException("Test folder not found: " + basePath);
        }

        List<File> javaFiles = new ArrayList<>();
        collectJavaFiles(folder, javaFiles);

        for (File file : javaFiles) {
            String relativePath = file.getPath().replace("src\\test\\java\\", "").replace(File.separator, ".");
            className = relativePath.substring(0, relativePath.length() - 5); // remove ".java"
            Class<?> clazz = Class.forName(className);
            XmlClass xmlClass = new XmlClass(className);

            // ✅ Check methods for @TestMeta
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(TestMeta.class)) {
                    TestMeta meta = method.getAnnotation(TestMeta.class);

                    featureMatch = featureFiter.equals("ALL") || featureFiter.equalsIgnoreCase(meta.feature());

                    if (featureMatch) {
                        xmlClass.getIncludedMethods().add(new XmlInclude(method.getName()));
                    }
                }
            }

            if (!xmlClass.getIncludedMethods().isEmpty()) {
                xmlClasses.add(xmlClass);
            }
        }

        test.setXmlClasses(xmlClasses);

        // ✅ Write testng.xml
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write(suite.toXml());
        }

        log.info("✅ dynamic-testng.xml generated successfully!");

        TestNG testng = new TestNG();
        testng.setTestSuites(Collections.singletonList(testFilePath));
        testng.run();
    }

    private static void collectJavaFiles(File folder, List<File> result) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                collectJavaFiles(file, result);
            } else if (file.isFile() && file.getName().endsWith(".java")) {
                result.add(file);
            }
        }
    }
}
