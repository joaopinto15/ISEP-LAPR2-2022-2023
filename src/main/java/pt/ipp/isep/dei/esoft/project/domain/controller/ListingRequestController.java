package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.repository.RequestRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;
import java.util.Random;

/**
 * The type Listing request controller.
 */
public class ListingRequestController {
    /**
     * Initiates the instance repositories of the class Repositories
     */
    private final Repositories repositories = Repositories.getInstance();

    /**
     * Instantiates a new Listing request controller.
     */
    public ListingRequestController() {
    }

    /**
     * Submit property.
     *
     * @param property the property
     * @param person   the person
     * @param employee the employee
     */
    public void submitProperty(Property property, Person person, Employee employee) {
        Request request = new Request(employee, person, property, true);
        RequestRepository requestRepository = Repositories.getInstance().getRequestRepository();
        requestRepository.addRequest(request);
    }

    /**
     * Generates a random agent from the list of employees based on the given
     * agency.
     *
     * @param agencyId the agency id
     * @return a random agent from the list of employees
     */
    public Employee randomAgent(String agencyId) {
        List<Employee> employees = Utils.getEmployeeByAgency(agencyId);
        Random random = new Random();
        assert employees != null;
        int index = random.nextInt(employees.size());
        return employees.get(index);
    }

}
