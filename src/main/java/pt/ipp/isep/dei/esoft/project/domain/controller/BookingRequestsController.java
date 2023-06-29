package pt.ipp.isep.dei.esoft.project.domain.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.PersonRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.repository.VisitRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.Comparator;
import java.util.List;

/**
 * The type Booking requests controller.
 */
public class BookingRequestsController {
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
    private final VisitRepository visitRepository=Repositories.getInstance().getVisitRepository();

    /**
     * Instantiates a new Booking requests controller.
     */
    public BookingRequestsController() {
    }

    /**
     * Gets current person.
     *
     * @return the current person
     */
    public Employee getCurrentPerson() {
        Email idInSession = AuthenticationRepository.getCurrentUserSession().getUserId();
        return (Employee) PersonRepository.getPersonById(idInSession);
    }

    /**
     * Get visits by date list.
     *
     * @param employee the employee
     * @return the list
     */
    public List<Visit> getVisitsByDate(Employee employee){
        List<Visit> visitsByDate = visitRepository.getVisitByAgentList(employee);
            visitsByDate.sort(new Comparator<Visit>() {
                @Override
                public int compare(Visit visit, Visit otherVisit) {
                    return visit.getDate().compareTo(otherVisit.getDate());
                }
            });
            return visitsByDate;
    }
    // Doing the method to get visits by a specific date period
//    public  List<Visit> getVisitsBySpecificDate(Date startDate, Date endDate){
//        List<Visit> visitsBySpecificDate =visitRepository.getVisitList();
//
//
//
//        return visitsBySpecificDate;
//    }

    /**
     * Get observable list observable list.
     *
     * @param visits the visits
     * @return the observable list
     */
    public ObservableList<Visit> getObservableList(List<Visit> visits){
        return FXCollections.observableArrayList(visits);
    }

    /**
     * method to bubble sort the visit list
     *
     * @param visits the visits
     * @return list
     */
    public List<Visit> bubbleSort(List<Visit> visits) {
        int n = visits.size();
       for (int i = 0; i < n - 1; i++) {
           for (int j = 0; j < n - i - 1; j++) {
               if (visits.get(j).getDate() == visits.get(j + 1).getDate()) {
                    if(visits.get(j).getStartTime().compareTo(visits.get(j+1).getStartTime()) > 0){
                        Visit temp = visits.get(j);
                        visits.set(j, visits.get(j + 1));
                        visits.set(j + 1, temp);
                    }
               } else {
                   if (visits.get(j).getDate().compareTo(visits.get(j + 1).getDate()) > 0) {
                       Visit temp = visits.get(j);
                       visits.set(j, visits.get(j + 1));
                       visits.set(j + 1, temp);
                   }
               }
           }
       }
        return visits;
    }

    /**
     * method to bubble sort the visit list in descending order
     *
     * @param visits the visits
     * @return the list
     */
    public List<Visit> bubbleSortDescending(List<Visit> visits){
        int n = visits.size();
        for (int i = 0; i <n-1 ; i++) {
            for (int j = 0; j <n-i-1 ; j++) {
                if (visits.get(j + 1).getDate() == visits.get(j).getDate()) {
                    if(visits.get(j + 1).getStartTime().compareTo(visits.get(j).getStartTime()) > 0){
                        Visit temp = visits.get(j);
                        visits.set(j, visits.get(j + 1));
                        visits.set(j + 1, temp);
                    }
                } else {
                    if (visits.get(j + 1).getDate().compareTo(visits.get(j).getDate()) > 0) {
                        Visit temp = visits.get(j);
                        visits.set(j, visits.get(j + 1));
                        visits.set(j + 1, temp);
                    }
                }
            }
        }
        return visits;
    }
}
