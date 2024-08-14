package org.opencart.Zones;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;

    public ConfigReader(String fileName) {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + fileName);
                return;
            }
            // Load properties file
            properties.load(input);
        } catch (IOException ex) {
            
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        System.out.println("Property " + key + ": " + value);  // Debug statement
        return value;
    }
}
