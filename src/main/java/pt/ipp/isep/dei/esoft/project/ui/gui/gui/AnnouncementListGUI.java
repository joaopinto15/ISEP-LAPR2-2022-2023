package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import io.jsonwebtoken.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import pt.ipp.isep.dei.esoft.project.domain.controller.ListPropertyController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Announcement list gui.
 */
public class AnnouncementListGUI implements Initializable {
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
     * The Properties.
     */
//GUI
    private List<String> properties;
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

        //Controllers
        HelloController ctrl = new HelloController();
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

        ListPropertyController ctrl1 = new ListPropertyController();
        List<Announcement> announcementList = ctrl1.getAnnouncementList();

        properties = getProperties(announcementList);
        myListView.getItems().addAll(getPropertiesTitle(announcementList));

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                myTextFlow.getChildren().clear();
                currentIndex = myListView.getSelectionModel().getSelectedIndex();
                Text text = new Text(properties.get(currentIndex));
                myTextFlow.getChildren().add(text);
            }
        });

    }

    /**
     * Get properties list.
     *
     * @param announcementList the announcement list
     * @return the list
     */
    public List<String> getProperties(List<Announcement> announcementList){

        List<String> properties = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            properties.add(announcement.getRequest().getProperty().toString());
        }
        return properties;
    }

    /**
     * Get properties title list.
     *
     * @param announcementList the announcement list
     * @return the list
     */
    public List<String> getPropertiesTitle(List<Announcement> announcementList){
        List<String> propertiesTitle = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            propertiesTitle.add("Announcement #" + announcement.getRequest().getUser().getName());
        }
        return propertiesTitle;
    }
}