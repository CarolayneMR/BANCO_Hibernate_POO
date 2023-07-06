package org.exemplo.persistencia.database.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    public static Properties loadConfig() {
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("configFile.txt")) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
