package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import io.jsonwebtoken.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.service.PropertySubsetService;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;
import pt.ipp.isep.dei.esoft.project.ui.gui.us017.orderSales;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The type Divide stores gui.
 */
public class DivideStoresGUI implements Initializable {

    /**
     * The Ctrl.
     */
    private HelloController ctrl;
    @FXML
    private Label filePathName;
    private final pt.ipp.isep.dei.esoft.project.ui.gui.us017.orderSales orderSales = new orderSales();
    final FileChooser fileChooser = new FileChooser();
    /**
     * The File path.
     */
    private String filePath;

    public DivideStoresGUI() throws FileNotFoundException {
    }

    @FXML
    void getFileDeal(MouseEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            filePath = file.getAbsolutePath();

        }

    }

    /**
     * The Property subset service.
     */
    PropertySubsetService propertySubsetService;
    /**
     * The Number txt.
     */
    @FXML
    private TextField numberTxt;

    /**
     * The Error lbl.
     */
    @FXML
    private Label errorLbl;

    /**
     * The First subset.
     */
    @FXML
    private ListView<String> firstSubset;

    /**
     * The Second subset.
     */
    @FXML
    private ListView<String> secondSubset;

    /**
     * Initialize.
     *
     * @param url            the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Calculate action.
     *
     * @param event the event
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    @FXML
    void calculateAction(ActionEvent event) throws FileNotFoundException, ParseException {
        System.out.println(Repositories.getInstance().getAgencyRepository().getAgencyList().size());
        if(numberTxt.getText().isEmpty()) {
            errorLbl.setText("Please insert a number!");
            errorLbl.setTextFill(Color.web("#FF0000"));
        }else {
            Integer number = Integer.parseInt(numberTxt.getText());
            if (number == null || number < 3 || number > 30) {
                errorLbl.setText("Invalid number of stores! n= 3,6,9, ... , 30");
                errorLbl.setTextFill(Color.web("#FF0000"));
            } else {
                firstSubset.getSelectionModel().clearSelection();
                secondSubset.getSelectionModel().clearSelection();
                propertySubsetService = new PropertySubsetService(filePath);
                if (propertySubsetService == null) {
                    errorLbl.setText("No agencies found!");
                    errorLbl.setTextFill(Color.web("#FF0000"));
                } else {
                    Map.Entry<ArrayList<Agency>, ArrayList<Agency>> bestSubset = propertySubsetService.getBestSubset(number,filePath);

                    if (bestSubset != null) {

                        //divide into two lists
                        if (bestSubset != null) {
                            firstSubset.getItems().clear();
                            secondSubset.getItems().clear();

                            ArrayList<Agency> firstSub = bestSubset.getKey();
                            ArrayList<Agency> secondSub = bestSubset.getValue();
                            for (Agency agency : firstSub) {
                                firstSubset.getItems().add("Store Id: " + agency.getId());
                                firstSubset.getItems().add("Number of Properties: " + propertySubsetService.getpropertiesByID(agency.getId(),filePath));
                            }
                            for(Agency agencyList : secondSub){
                                secondSubset.getItems().add("Store Id: " + agencyList.getId());
                                secondSubset.getItems().add("Number of Properties: " + propertySubsetService.getpropertiesByID(agencyList.getId(),filePath));
                            }
                            /**for (Agency agency : secondSub){

                                secondSubset.getItems().add("Number of Properties: "+Integer.toString(propertySubsetService.getpropertiesByID(agency.getId())));
                            }*/

                            /**firstSubset.getItems().setAll(String.valueOf(firstSub));
                            /**secondSubset.getItems().setAll(secondSub);*/

                        }
                    } else {
                        errorLbl.setText("No subset found!");
                        errorLbl.setTextFill(Color.web("#FF0000"));
                    }
                }
            }
        }
    }

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

}