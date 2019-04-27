package me.kingtux.tuxmvc;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class BetterProperties extends Properties {

    @Override
    public String getProperty(String key, String defaultValue) {
        String s = super.getProperty(key, defaultValue);
        if (StringUtils.isBlank(s)) {
            s = defaultValue;
        }
        return s;
    }

    public static Properties ptobp(Properties properties) {
        Properties properties1 = new BetterProperties();
        properties.keySet().forEach(o -> properties1.setProperty((String) o, properties.getProperty((String) o)));
        return properties1;
    }
}
