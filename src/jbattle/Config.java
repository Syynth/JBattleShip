/**
 *
 * @author Ben Cochrane
 */
package jbattle;

import java.net.URL;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 *
 * @author Ben
 */
public final class Config {
    
    private Config() {}
       
    public static boolean LoadConfig(URL config) {
        try {
            SAXReader reader = new SAXReader();
            Config.config = reader.read(config);
            return true;
        } catch (DocumentException ex) {
            System.out.println("Couldn't read from the config file: " + config);
            return false;
        }
    }
    
    private static Document config;
    
}
