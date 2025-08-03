package com.example.jmeter;

import org.apache.jmeter.NewDriver;

public class JMeterRunner {
    public static void run(String jmxPath, String resultPath) {
        String[] jmeterArgs = {
            "-n",                         // non-GUI mode
            "-t", jmxPath,                // test plan
            "-l", resultPath              // result output
        };
        NewDriver.main(jmeterArgs);
    }
}
