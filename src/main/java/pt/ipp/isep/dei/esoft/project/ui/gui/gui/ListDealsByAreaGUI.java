package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import io.jsonwebtoken.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;
import pt.ipp.isep.dei.esoft.project.ui.gui.us017.orderSales;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type List deals by area gui.
 */
public class ListDealsByAreaGUI extends Application implements Initializable, Runnable {
    /**
     * Instantiates a new List deals by area gui.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public ListDealsByAreaGUI() throws FileNotFoundException {
}

    /**
     * Run.
     */
    public void run() {
        launch();
    }

    /**
     * Start.
     *
     * @param stage the stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/fxml/" + "listDealsByArea" + ".fxml");
        loader.setLocation(xmlUrl);
        ResourceBundle bundle = ResourceBundle.getBundle("config");
        loader.setResources(bundle);

        stage.setScene(new Scene(loader.load()));
        stage.setTitle("List Deals by Area");
        stage.show();
    }

    /**
     * The File chooser.
     */
    final FileChooser fileChooser = new FileChooser();
    /**
     * The My list view.
     */
    @FXML
    private ListView<String> myListView;
    /**
     * The File path name.
     */
    @FXML
    private Label filePathName;

    /**
     * The My text flow.
     */
    @FXML
    private TextArea myTextFlow;

    /**
     * Gets file deal.
     *
     * @param event the event
     */
    @FXML
    void getFileDeal(MouseEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            filePath = file.getAbsolutePath();
            filePathName.setText(filePath);

            try {
                dealsComplete = orderSales.deafault(filePath);
            } catch (FileNotFoundException | ParseException e) {
                throw new RuntimeException(e);
            }

            properties = getdealProperty(dealsComplete);
            myListView.getItems().clear();
            myListView.getItems().addAll(getdealTitle(dealsComplete));

        }

    }

    /**
     * Select ascending.
     *
     * @param event the event
     */
    @FXML
    void selectAscending(MouseEvent event) {
        try {
            dealsComplete = orderSales.selectionSort(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        properties = getdealProperty(dealsComplete);
        myListView.getItems().clear();
        myListView.getItems().addAll(getdealTitle(dealsComplete));
    }

    /**
     * Select descending.
     *
     * @param event the event
     */
    @FXML
    void selectDescending(MouseEvent event) {
        try {
            dealsComplete = orderSales.selectionSortDescending(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        properties = getdealProperty(dealsComplete);
        myListView.getItems().clear();
        myListView.getItems().addAll(getdealTitle(dealsComplete));

    }

    /**
     * Bublle ascending.
     *
     * @param event the event
     */
    @FXML
    void bublleAscending(MouseEvent event) {
        try {
            dealsComplete = orderSales.bubbleSort(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        properties = getdealProperty(dealsComplete);
        myListView.getItems().clear();
        myListView.getItems().addAll(getdealTitle(dealsComplete));

    }

    /**
     * Bublle descending.
     *
     * @param event the event
     */
    @FXML
    void bublleDescending(MouseEvent event) {
        try {
            dealsComplete = orderSales.bubbleSortDescending(filePath);
        } catch (FileNotFoundException | ParseException e) {
            throw new RuntimeException(e);
        }

        properties = getdealProperty(dealsComplete);
        myListView.getItems().clear();
        myListView.getItems().addAll(getdealTitle(dealsComplete));

    }

    /**
     * The File path.
     */
    private String filePath;
    /**
     * The Order sales.
     */
    private final orderSales orderSales = new orderSales();
    /**
     * The Deals complete.
     */
    private List<Announcement> dealsComplete;
    /**
     * The Properties.
     */
    private List<String> properties;
    /**
     * The Current deal.
     */
    private String currentDeal;
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

        HelloController ctrl = new HelloController();
        ctrl.switchTo(event, "mainMenu");
    }

    /**
     * Initialize.
     *
     * @param arg0 the arg 0
     * @param arg1 the arg 1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        myListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentDeal = myListView.getSelectionModel().getSelectedItem();
                currentIndex = myListView.getSelectionModel().getSelectedIndex();
                String propertyText = properties.get(currentIndex);
                myTextFlow.setText(propertyText);
            }
        });
    }

    /**
     * Gets title.
     *
     * @param announcementList the announcement list
     * @return the title
     */
    public List<String> getdealTitle(List<Announcement> announcementList) {
        List<String> dealTitle = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            dealTitle.add("Area: " + announcement.getRequest().getProperty().getArea() + " ft²");
        }
        return dealTitle;
    }

    /**
     * Gets property.
     *
     * @param announcementList the announcement list
     * @return the property
     */
    public List<String> getdealProperty(List<Announcement> announcementList) {

        List<String> dealTitle = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            dealTitle.add(announcement.getRequest().getProperty().toString());
        }
        return dealTitle;
    }


}
