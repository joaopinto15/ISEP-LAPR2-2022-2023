package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Create announcement gui.
 */
public class CreateAnnouncementGUI implements Initializable {

    /**
     * The My label.
     */
    @FXML
    private Label myLabel;

    /**
     * The My list view.
     */
    @FXML
    private ListView<String> myListView;

    /**
     * The Client.
     */
    private Person client;

    /**
     * The Ctrl.
     */
    private final HelloController ctrl = new HelloController();


    /**
     * Display data.
     *
     * @param client the client
     */
    public void displayData(Person client) {
        this.client = client;
    }

    /**
     * Switch to publish announcement.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToPublishAnnouncement(ActionEvent event) throws IOException {

        ctrl.switchTo(event,"US002/publishAnnouncement");
    }

    /**
     * Switch to create announcement.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToCreateAnnouncement(ActionEvent event) throws IOException {

        ctrl.switchTo(event,"US002/createAnnouncement");
    }

    /**
     * Handle new window.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void handleNewWindow(ActionEvent event) throws IOException {

        int currentIndex = myListView.getSelectionModel().getSelectedIndex();
        
        switch(currentIndex){
            case(0):
                
                FXMLLoader loader = ctrl.openModalWindow(event,"US002/sendInformation", myListView.getScene().getWindow());
                SendInformationGUI ctrl1 = loader.getController();
                ctrl1.diplaysData(client, "Land");
                break;
            case(1):
                FXMLLoader loader1 = ctrl.openModalWindow(event,"US002/sendInformation", myListView.getScene().getWindow());
                SendInformationGUI ctrl2 = loader1.getController();
                ctrl2.diplaysData(client, "Apartment");
                break;
            case(2):
                FXMLLoader loader2 = ctrl.openModalWindow(event,"US002/sendInformation", myListView.getScene().getWindow());
                SendInformationGUI ctr2 = loader2.getController();
                ctr2.diplaysData(client, "House");
                break;
        }

       
    }

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        List<String> options = List.of("Land", "Apartment", "House");

        myListView.getItems().addAll(options);

    }

    
}
