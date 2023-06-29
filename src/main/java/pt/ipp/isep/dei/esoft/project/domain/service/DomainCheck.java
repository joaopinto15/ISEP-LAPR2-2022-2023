package pt.ipp.isep.dei.esoft.project.domain.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Domain check.
 */
public class DomainCheck {


    /**
     * Instantiates a new Domain check.
     */
    public DomainCheck() {
    }

    /**
     * Check domain boolean.
     *
     * @return the boolean
     */
    public static boolean checkDomain() {
        try (InputStream input = DomainCheck.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();
            prop.load(input);

        String domain = prop.getProperty("Domain.Inuse") ;
            if (domain.equals("dei") || domain.equals("gmail") || domain.equals("hotmail") || domain.equals("yahoo")){
                return true;
            }else{
                throw new IllegalArgumentException("Unsupported email domain: " + domain);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        
    }
 }