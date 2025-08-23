package com.example.executions;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.example.configs.ConfigLoader;
import com.example.configs.TestProperties;

public class DynamicSuiteGenerator {

    public static void main(String[] args) throws Exception {
        String suiteFilter = ConfigLoader.get(TestProperties.SUITE_NAME.toString());
        String platformFilter = ConfigLoader.get(TestProperties.PLATFORM.toString());
        String featureFilter = ConfigLoader.get(TestProperties.FEATURE.toString());

        // ✅ Cross-platform safe paths
        String testngFilePath = Paths.get("src", "test", "resources", "dynamic-testng.xml").toString();
        String basePath = Paths.get("src", "test", "java", "com", "example", "modules").toString() + File.separator;

        List<XmlClass> xmlClasses = new ArrayList<>();

        if (suiteFilter == null)
            suiteFilter = "ALL";
        if (platformFilter == null)
            platformFilter = "ALL";

        XmlSuite suite = new XmlSuite();
        suite.setName("DynamicSuite");
        suite.setParallel(XmlSuite.ParallelMode.CLASSES);
        suite.setThreadCount(3);

        XmlTest test = new XmlTest(suite);
        test.setName("DynamicGeneratedTests");

        // ✅ Build test module path
        String testModulePath = basePath + suiteFilter.toLowerCase() + File.separator + platformFilter.toLowerCase();
        File testFolder = new File(testModulePath);
        if (!testFolder.exists()) {
            throw new RuntimeException("Test folder not found: " + testModulePath);
        }

        List<File> javaFiles = new ArrayList<>();
        collectJavaFilesInFolderAndItsSubFolders(testFolder, javaFiles);
        collectTestClassesAndMethodsByFeatureName(javaFiles, featureFilter, xmlClasses);
        test.setXmlClasses(xmlClasses);

        // ✅ Write dynamic-testng.xml
        try (FileWriter writer = new FileWriter(testngFilePath)) {
            writer.write(suite.toXml());
        }
    }

    private static void collectTestClassesAndMethodsByFeatureName(List<File> javaFiles, String featureFilter,
            List<XmlClass> xmlClasses) throws ClassNotFoundException {
        for (File file : javaFiles) {
            // ✅ Use Paths and relative pathing instead of hardcoding
            String relativePath = file.getPath()
                    .replace(Paths.get("src", "test", "java").toString() + File.separator, "")
                    .replace(File.separator, ".");
            String className = relativePath.substring(0, relativePath.length() - 5); // strip ".java"

            Class<?> clazz = Class.forName(className);
            XmlClass xmlClass = new XmlClass(className);

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(TestMeta.class)) {
                    TestMeta meta = method.getAnnotation(TestMeta.class);

                    boolean featureMatch = featureFilter.equals("ALL")
                            || featureFilter.equalsIgnoreCase(meta.feature());

                    if (featureMatch) {
                        xmlClass.getIncludedMethods().add(new XmlInclude(method.getName()));
                    }
                }
            }

            if (!xmlClass.getIncludedMethods().isEmpty()) {
                xmlClasses.add(xmlClass);
            }
        }
    }

    private static void collectJavaFilesInFolderAndItsSubFolders(File folder, List<File> result) {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                collectJavaFilesInFolderAndItsSubFolders(file, result);
            } else if (file.isFile() && file.getName().endsWith(".java")) {
                result.add(file);
            }
        }
    }
}
