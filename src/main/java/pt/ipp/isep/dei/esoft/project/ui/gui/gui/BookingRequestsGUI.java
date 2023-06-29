package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import io.jsonwebtoken.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import pt.ipp.isep.dei.esoft.project.domain.controller.BookingRequestsController;
import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.repository.VisitRepository;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.gui.Context;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Booking requests gui.
 */
public class BookingRequestsGUI implements Initializable {

    /**
     * The Btn list a.
     */
    @FXML
    private Button btnListA;

    /**
     * The Btn list d.
     */
    @FXML
    private Button btnListD;

    /**
     * The List view visits.
     */
    @FXML
    private ListView<Visit> listViewVisits;

    /**
     * The Date picker s.
     */
    @FXML
    private DatePicker datePickerS;

    /**
     * The Date picker e.
     */
    @FXML
    private DatePicker datePickerE;

    /**
     * The Ctrl.
     */
//Controllers
    private HelloController ctrl;
    /**
     * The Controller.
     */
    private BookingRequestsController controller;
    /**
     * The Repository.
     */
    private VisitRepository repository;

    /**
     * The Visit list.
     */
//GUI
    private List<Visit> visitList;

    /**
     * The Chosen visit.
     */
    private Visit chosenVisit;

    /**
     * The Is ascending.
     */
    private boolean isAscending = true;

    /**
     * Gets controller.
     *
     * @return the controller
     */
    public BookingRequestsController getController() {
        return controller;
    }

    /**
     * Switch to agent menu.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToAgentMenu(ActionEvent event) throws IOException, java.io.IOException {

        ctrl = new HelloController();
        ctrl.switchTo(event, "agentMenu");
    }

    /**
     * Initialize.
     *
     * @param url            the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controller = new BookingRequestsController();
        repository = Repositories.getInstance().getVisitRepository();
        datePickerS.setValue(LocalDate.now());
        datePickerE.setValue(LocalDate.now().plusDays(365));
        showBookingRequestListBubbleSort();
        listViewVisits.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
            {
                Visit visit = listViewVisits.getSelectionModel().getSelectedItem();
                ctrl = new HelloController();
                try {
                    Context ctx= Context.getInstance();
                    ctx.setVisit(visit);
                    ctrl.switchTo(event, "RespondVisitRequest");
                } catch (java.io.IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * method to show the booking request list sorted by the algorithm "bubble sort"
     */
    public void showBookingRequestListBubbleSort(){
        isAscending = true;
        refreshList();
        }

    /**
     * method to show the booking request list sorted but the algorithm "bubble sort" but descending
     */
    public void showBookingRequestListBubbleSortDescending(){
        isAscending = false;
        refreshList();
    }

    /**
     * Refresh list.
     */
    public void refreshList(){
        LocalDate startDate = datePickerS.getValue();
        LocalDate endDate =  datePickerE.getValue();
        Date dateS = new Date(startDate.getYear(),startDate.getMonthValue(),startDate.getDayOfMonth());
        Date dateE = new Date(endDate.getYear(),endDate.getMonthValue(),endDate.getDayOfMonth());
        List<Visit> visits = new ArrayList<>();
        for(Visit visit : repository.getVisitByAgentList(Utils.getEmployeeInSession())){
            //dateS is before visit date and dateE is after visit date
            if(visit.getDate().compareTo(dateS) >= 0 && visit.getDate().compareTo(dateE) <= 0){
                visits.add(visit);
            }
        }
        if(isAscending) {
            listViewVisits.setItems(controller.getObservableList(controller.bubbleSort(visits)));
        }
        else{
            listViewVisits.setItems(controller.getObservableList(controller.bubbleSortDescending(visits)));
        }
    }
}
