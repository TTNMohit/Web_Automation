package com.ttn.automation.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Contains property file read helper methods
 *
 * @author abhishek sharma
 */

public class PropertyFileUtils {

    final static Logger logger = LoggerFactory.getLogger(PropertyFileUtils.class);

    /**
     * Read Properties File specified in path
     *
     * @param propertyFilePath
     * @return prop
     */
    public static Properties readProperty(String propertyFilePath) {
        Properties prop = new Properties();
        try {
            FileInputStream input = new FileInputStream(propertyFilePath);
            prop.load(input);
        } catch (IOException e) {
            logger.error("Failed to read properties file at: " + propertyFilePath + " Associated Error:" + e.getMessage());
        }
        return prop;
    }

}
