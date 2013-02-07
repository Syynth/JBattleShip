/**
 *
 * @author Ben Cochrane
 */
package jbattle;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class Config {
    
    public Config(URL configLocation) {
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(configLocation);
        } catch (DocumentException ex) {
            System.out.println("Couldn't read from the config file: " + configLocation);
        }
        
    }
    
}
