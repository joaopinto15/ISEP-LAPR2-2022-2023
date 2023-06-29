package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The type Send information gui.
 */
public class SendInformationGUI implements Initializable {

    /**
     * The Insert url.
     */
    @FXML
    private Button insertURL;

    /**
     * The My label.
     */
    @FXML
    private Label myLabel;

    /**
     * The Street.
     */
    @FXML
    private TextField street, /**
     * The City.
     */
    city, /**
     * The District.
     */
    district, /**
     * The State.
     */
    state, /**
     * The Zip code.
     */
    zipCode, /**
     * The Area.
     */
    area, /**
     * The Disctance from city center.
     */
    disctanceFromCityCenter, /**
     * The Price.
     */
    price;

    /**
     * The Photo url.
     */
    @FXML
    private ArrayList<String> photoURL;

    /**
     * The Deal type.
     */
    @FXML
    private ChoiceBox<String> dealType;

    /**
     * The Ctrl.
     */
    private final HelloController ctrl = new HelloController();

    /**
     * The Client.
     */
    private Person client;
    /**
     * The Type.
     */
    private String type;

    /**
     * The Error.
     */
    private final Alert error = new Alert(AlertType.ERROR);

    /**
     * The Contract duration.
     */
    private int contractDuration;


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
     * Close window.
     */
    public void closeWindow(){
        Stage closeStage = (Stage) insertURL.getScene().getWindow();

        closeStage.close();
    }

    /**
     * Diplays data.
     *
     * @param client the client
     * @param type   the type
     */
    public void diplaysData(Person client, String type) {

       
    }

    /**
     * Next page.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void nextPage(ActionEvent event) throws IOException {
        if(street.getText().isEmpty() || city.getText().isEmpty() || district.getText().isEmpty() || state.getText().isEmpty() || zipCode.getText().isEmpty() || area.getText().isEmpty() || disctanceFromCityCenter.getText().isEmpty() || photoURL.isEmpty() || dealType.getValue().isEmpty() || price.getText().isEmpty()){
            String error1 = "Please fill all the fields";
            error.setContentText(error1);
            error.setHeaderText("Error");
            error.show();
        }else{
            Address address = new Address(street.getText(),city.getText(),district.getText(),state.getText(),zipCode.getText());
            Deal deal = createDeal();

            Land land = new Land(Double.parseDouble(area.getText()),Double.parseDouble(disctanceFromCityCenter.getText()),photoURL,address,deal);
            switch(type){
                case("Land"): 
                    Employee employeeInSession = Utils.getEmployeeInSession();  
                    Request request = new Request(employeeInSession, client, land, false);
                    break;
                    
                case("Apartment"):

                    break;

                case("House"):

                    break;
            }
        }
    }

    /**
     * Open multiple files.
     *
     * @param event the event
     */
    public void openMultipleFiles(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Multiple Files");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        for (File file : files) {
            photoURL.add(file.getAbsolutePath());
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
        dealType.getItems().addAll("Sale","Rent");

        dealType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if(dealType.getSelectionModel().getSelectedIndex() == 1){
                    TextInputDialog dialog = new TextInputDialog("Enter the contract duration");
                    dialog.setTitle("Contract Duration");
                    dialog.showAndWait();
                    contractDuration = Integer.parseInt(dialog.getResult());

                }
                
            }
        });
    }

    /**
     * Create deal deal.
     *
     * @return the deal
     */
    public Deal createDeal(){
        Deal deal;
        if(dealType.getValue().equals("Sale")){
            deal = new Sale(Double.parseDouble(price.getText()));
        }
        else{deal = new Rent(Double.parseDouble(price.getText()),contractDuration);}

        return deal;
    }

    
}
