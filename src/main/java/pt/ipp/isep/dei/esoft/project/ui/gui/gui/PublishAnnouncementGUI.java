package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import io.jsonwebtoken.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;
import pt.ipp.isep.dei.esoft.project.domain.controller.CreateAnnouncementController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import javafx.scene.Node;


/**
 * The type Publish announcement gui.
 */
public class PublishAnnouncementGUI implements Initializable {
    /**
     * The My list view.
     */
    @FXML
    private ListView<String> myListView;

    /**
     * The My text flow.
     */
    @FXML
    private TextFlow myTextFlow;

    /**
     * The Ctrl.
     */
//Controllers
    private HelloController ctrl;
    /**
     * The Ctrl 2.
     */
    private CreateAnnouncementController ctrl2;

    /**
     * The Clients.
     */
//GUI
    private List<String> clients;
    /**
     * The Client list.
     */
    private List<Person> clientList;
    /**
     * The Current index.
     */
    private int currentIndex;


    /**
     * Switch to main.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToMain(ActionEvent event) throws IOException, java.io.IOException {

        ctrl = new HelloController();
        ctrl.switchTo(event, "mainMenu");
    }


    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

       ctrl = new HelloController();
        clientList = Utils.getClient();

        clients = getClients(clientList);
        myListView.getItems().addAll(getClientName(clientList));

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                myTextFlow.getChildren().clear();
                currentIndex = myListView.getSelectionModel().getSelectedIndex();
                Text text = new Text(clients.get(currentIndex));
                myTextFlow.getChildren().add(text);
            }
        });

    }

    /**
     * Get clients list.
     *
     * @param clients the clients
     * @return the list
     */
    public List<String> getClients(List<Person> clients){

        List<String> properties = new ArrayList<>();
        for (Person client : clients) {
            properties.add(client.toString());
        }
        return properties;
    }

    /**
     * Get client name list.
     *
     * @param clients the clients
     * @return the list
     */
    public List<String> getClientName(List<Person> clients){
        List<String> propertiesTitle = new ArrayList<>();
        for (Person client : clients) {
            propertiesTitle.add("Person #" + client.getName());
        }
        return propertiesTitle;
    }

    /**
     * Next page.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void nextPage(ActionEvent event) throws IOException, java.io.IOException {

        int clientIndex = myListView.getSelectionModel().getSelectedIndex();
        Person client = clientList.get(clientIndex);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/US002/createAnnouncement.fxml"));
        Parent root = loader.load();
        CreateAnnouncementGUI ctrl = loader.getController();
        ctrl.displayData(client);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    
    }
}