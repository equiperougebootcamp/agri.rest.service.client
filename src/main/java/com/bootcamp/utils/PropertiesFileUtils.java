package com.bootcamp.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by darextossa on 11/30/17.
 */
public class PropertiesFileUtils {

    public String getAppUrl(String url) throws IOException {
        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/app-url.properties"));
        return props.getProperty(url);
    }
}
