/**
 *
 * @author Ben Cochrane
 */
package jbattle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;



/**
 *
 * @author Ben
 */
public final class Config {
    
    private Config() {}
       
    public static boolean LoadConfig(String config) {
        try {
            configLocation = config;
            SAXReader reader = new SAXReader();
            Config.config = reader.read(new File(config).toURI().toURL());
            return true;
        } catch (MalformedURLException | DocumentException ex) {
            System.out.println("Couldn't read from the config file: " + config);
            return false;
        }
    }
    
    public static String getProperty(String name) {
        return getProperty("global", name);
    }
    
    public static String getProperty(String group, String name) {
        if (Config.config == null) {
            return "";
        } else {
            return Config.config.selectSingleNode("//propertyGroup[@name='" +
                   group + "']/property[@name='" + name + "']").getText();
        }
    }
    
    public static boolean getBoolean(String name) {
        return Boolean.parseBoolean(getProperty(name));
    }
    
    public static boolean getBoolean(String group, String name) {
        return Boolean.parseBoolean(getProperty(group, name));
    }
    
    public static int getInt(String name) {
        return Integer.parseInt(getProperty(name));
    }
    
    public static int getInt(String group, String name) {
        return Integer.parseInt(getProperty(group, name));
    }
    
    public static double getDouble(String name) {
        return Double.parseDouble(getProperty(name));
    }
    
    public static double getDouble(String group, String name) {
        return Double.parseDouble(getProperty(group, name));
    }
    
    public static boolean setProperty(String name, String value) {
        return setProperty("global", name, value);
    }
    
    public static boolean setProperty(String group, String name, String value) {
        try {
            Config.config.selectSingleNode("//propertyGroup[@name='" + group +
                                            "']/property[@name='" + name + "']").setText(value);
            try (FileWriter output = new FileWriter(Config.configLocation)) {
                Config.config.write(output);
            }
            return true;
        } catch (IOException ex) {
            System.out.println("Couldn't write to the config file: " + config);
        }
        return false;
    }
    
    private static Document config;
    private static String configLocation;
    
}
