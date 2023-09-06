package ru.ibs.practice.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesConfig {
    private final Properties properties;

    public PropertiesConfig() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try {
            InputStream inputStream = Files.newInputStream(
                    Path.of("src/test/resources/config.properties"));

            properties.load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}