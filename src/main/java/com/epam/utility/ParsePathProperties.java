package com.epam.utility;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that parse properties with file path
 *
 * @author Volodymyr Tytukh
 */

public class ParsePathProperties {
    private static final Logger LOGGER = LogManager.getLogger(ParsePathProperties.class);

    private static ParsePathProperties instance = null;

    private Properties properties;
    private static String propertiesFileName = "path.properties";

    private ParsePathProperties() {
        LOGGER.info("Initializing MappingProperties class");

        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

        try {

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                LOGGER.error("Mapping properties file not found on the classpath");
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        try {
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ParsePathProperties getInstance() {
        if (instance == null) {
            instance = new ParsePathProperties();
        }

        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
