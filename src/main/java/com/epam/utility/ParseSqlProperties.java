package com.epam.utility;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that parse properties with sql queries
 *
 * @author Volodymyr Tytukh
 */

public class ParseSqlProperties {
    private static final Logger LOGGER = LogManager.getLogger(ParseSqlProperties.class);

    private static ParseSqlProperties instance = null;

    private Properties properties;
    private static String propertiesFileName = "sql.properties";

    private ParseSqlProperties() {
        LOGGER.info("Initializing MysqlQueryProperties class");

        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

        try {

            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                LOGGER.error("Mysql queries property file not found on the classpath");
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        try {
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            LOGGER.error("Can't close input stream");
        }
    }

    public static synchronized ParseSqlProperties getInstance() {
        if (instance == null) {
            instance = new ParseSqlProperties();
        }

        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
