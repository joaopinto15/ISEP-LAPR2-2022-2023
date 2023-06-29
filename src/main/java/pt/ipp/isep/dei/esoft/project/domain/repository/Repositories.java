package pt.ipp.isep.dei.esoft.project.domain.repository;

import java.io.Serializable;

/**
 * The Repositories class represents a singleton object that holds references to
 * various repository objects.
 * These repositories provide data access and manipulation services for
 * different domain objects in the application.
 */
public class Repositories implements Serializable {

    /**
     * The constant instance.
     */
// The instance variable holds the singleton instance of the Repositories class
    private static Repositories instance = new Repositories();

    /**
     * The Authentication repository.
     */
// Repository objects for different domain objects in the application
    private final AuthenticationRepository authenticationRepository = new AuthenticationRepository();
    /**
     * The Property repository.
     */
    private final PropertyRepository propertyRepository = new PropertyRepository();
    /**
     * The Announcement repository.
     */
    private final AnnouncementRepository announcementRepository = new AnnouncementRepository();
    /**
     * The Request repository.
     */
    private final RequestRepository requestRepository = new RequestRepository();
    /**
     * The Agency repository.
     */
    private final AgencyRepository agencyRepository = new AgencyRepository();
    /**
     * The Person repository.
     */
    private final PersonRepository personRepository = new PersonRepository();
    /**
     * The Visit repository.
     */
    private final VisitRepository visitRepository= new VisitRepository();

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private Repositories() {
    }

    /**
     * Returns the singleton instance of the Repositories class.
     *
     * @return the singleton instance of the Repositories class
     */
    public static Repositories getInstance() {
        return instance;
    }

    /**
     * Reset.
     */
    public static void reset() {
        instance = new Repositories();
    }

    /**
     * Returns the AuthenticationRepository object.
     *
     * @return the AuthenticationRepository object
     */
    public AuthenticationRepository getAuthenticationRepository() {
        return authenticationRepository;
    }

    /**
     * Returns the PropertyRepository object.
     *
     * @return the PropertyRepository object
     */
    public PropertyRepository getPropertyRepository() {
        return propertyRepository;
    }

    /**
     * Returns the EmployeeRepository object.
     *
     * @return the EmployeeRepository object
     */
    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    /**
     * Returns the AgencyRepository object.
     *
     * @return the AgencyRepository object
     */
    public AgencyRepository getAgencyRepository() {
        return agencyRepository;
    }

    /**
     * Returns the AnnouncementRepository object.
     *
     * @return the AnnouncementRepository object
     */
    public AnnouncementRepository getAnnouncementRepository() {
        return announcementRepository;
    }

    /**
     * Returns the VisitRepository object
     *
     * @return the VisitRepository object
     */
    public VisitRepository getVisitRepository(){return visitRepository;}

    /**
     * Returns the requestRepository object
     *
     * @return the RequestRepository object
     */
    public RequestRepository getRequestRepository(){return requestRepository;}

}
