package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.PersonRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.repository.VisitRepository;


import java.io.IOException;
import java.util.List;

/**
 * Visit controller.
 */
public class VisitController {
    /**
     * The Authentication repository.
     */
    private final AuthenticationRepository AuthenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    /**
     * The Person repository.
     */
    private final PersonRepository PersonRepository = Repositories.getInstance().getPersonRepository();
    /**
     * The Visit repository.
     */
    private final VisitRepository visitRepository;

    /**
     * Constructs a new VisitRepository object and initializes the visit repository.
     */
    public VisitController() {
        this.visitRepository = Repositories.getInstance().getVisitRepository();
    }

    /**
     * Retrieves all visits from the visit repository.
     *
     * @return a list of all visits in the repository.
     */
    public List<Visit> getVistiList() {
        return visitRepository.getVisitList();
    }

    /**
     * Displays a list of valid visits on the console.
     *
     * @param lis the list of properties to display.
     */
    public static void displayVisits(List<Property> lis) {
        for (int i = 0; i < lis.size(); i++) {
            System.out.println(lis.get(i).toString());
        }
    }

    /**
     * this function saves the visit if is validated
     *
     * @param visit represents an visit request.
     * @return save the instance of visit in visitList
     */
    public boolean saveVisit(Visit visit){
        return visitRepository.saveVisit(visit);
    }

    /**
     * Gets current person.
     *
     * @return the current person
     */
    public Person getCurrentPerson() {
        Person person = PersonRepository.getPersonById(AuthenticationRepository.getCurrentUserSession().getUserId());
        if (person == null) {
            throw new IllegalStateException("Current person not found.");
        }
        return person;
    }

    /**
     * Serealize visit.
     */
    public void serealizeVisit() {
        visitRepository.serializeVisit();
    }

    /**
     * Desserialize visit.
     */
    public void desserializeVisit(){
        visitRepository.desserializeVisit();
    }

}
