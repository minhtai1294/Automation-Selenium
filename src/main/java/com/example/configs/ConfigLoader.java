package com.example.configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties props = new Properties();

    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            props.load(fis);
        } catch (IOException e) {
            System.out.println("No config.properties found, falling back to env/system props.");
        }
    }

    public static String get(String key) {
        // Order: System > Env > File
        String sys = System.getProperty(key);
        if (sys != null) return sys;

        String env = System.getenv(key.toUpperCase()); // match ENV_VAR style
        if (env != null) return env;

        return props.getProperty(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        String value = get(key);
        return (value != null) ? value : defaultValue;
    }
}

