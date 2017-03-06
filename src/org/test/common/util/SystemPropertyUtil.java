package org.test.common.util;

import org.apache.commons.configuration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SystemPropertyUtil {
    private static Logger log = LoggerFactory.getLogger(SystemPropertyUtil.class);

    public static final String PROPERTY_FILE_NAME = "system.properties";

    private static CompositeConfiguration config = null;
    private static PropertiesConfiguration propConfig = null;

    static {
        try {
            config = new CompositeConfiguration();
            propConfig = new PropertiesConfiguration(PROPERTY_FILE_NAME);
            config.addConfiguration(propConfig);
        } catch (ConfigurationException e) {
            log.error("config file load errors.", e);
        }

    }
    
    public static String getProperty(String property, String defaultValue) {
        String retStr = "";
        if (config != null) {
            retStr = config.getString(property, defaultValue);
        }
        return retStr;
    }

    public static String getProperty(String property) {
        String retStr = "";
        if (config != null) {
            retStr = config.getString(property, "");
        }
        return retStr;
    }

    public static Integer getInt(String property) {
        Integer result = 0;
        if (config != null) {
            result = config.getInt(property, 0);
        }
        return result;
    }

    public static Integer getInt(String property, Integer defaultValue) {
        Integer result = 0;
        if (config != null) {
            result = config.getInt(property, defaultValue);
        }
        return result;
    }

    public static void setProperty(String strKey, String strValue) {
        if (config != null) {
            config.setProperty(strKey, strValue);
        }
    }

    public static List getList(String property, List defaultValue) {
        List result = new ArrayList();
        if (config != null) {
            result = config.getList(property, defaultValue);
        }
        return result;
    }

    public static void save() throws ConfigurationException {
        if (config != null) {
            PropertiesConfiguration propConfig = (PropertiesConfiguration)config.getConfiguration(0);
            propConfig.save();
        }
    }
}
