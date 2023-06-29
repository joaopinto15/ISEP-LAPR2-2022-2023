package pt.ipp.isep.dei.esoft.project.application.session;

import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The ApplicationSession class represents the session of the application.
 * It provides methods for retrieving the current user session and managing properties.
 */
public class ApplicationSession {
    /**
     * The Authentication repository.
     */
    private AuthenticationRepository authenticationRepository = null;
    /**
     * The constant CONFIGURATION_FILENAME.
     */
    private static final String CONFIGURATION_FILENAME = "src/main/resources/config.properties";
    /**
     * The constant COMPANY_DESIGNATION.
     */
    private static final String COMPANY_DESIGNATION = "Company.Designation";

    /**
     * Constructs a new ApplicationSession object.
     * Initializes the authentication repository and loads properties.
     */
    public ApplicationSession() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        Properties props = getProperties();
    }

    /**
     * Retrieves the current user session.
     *
     * @return The UserSession object representing the current session.
     */
    public UserSession getCurrentSession() {
        pt.isep.lei.esoft.auth.UserSession userSession = this.authenticationRepository.getCurrentUserSession();
        return new UserSession(userSession);
    }

    /**
     * Loads and returns the properties from the configuration file.
     *
     * @return The Properties object containing the loaded properties.
     */
    public Properties getProperties() {
        Properties props = new Properties();

        // Add default properties and values
        props.setProperty(COMPANY_DESIGNATION, "Real Estate USA");

        // Read configured values
        try {
            InputStream in = new FileInputStream(CONFIGURATION_FILENAME);
            props.load(in);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return props;
    }


    /**
     * The constant singleton.
     */
// Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static ApplicationSession singleton = null;

    /**
     * Retrieves the singleton instance of ApplicationSession.
     *
     * @return The ApplicationSession singleton instance.
     */
    public static ApplicationSession getInstance() {
        if (singleton == null) {
            synchronized (ApplicationSession.class) {
                singleton = new ApplicationSession();
            }
        }
        return singleton;
    }
}