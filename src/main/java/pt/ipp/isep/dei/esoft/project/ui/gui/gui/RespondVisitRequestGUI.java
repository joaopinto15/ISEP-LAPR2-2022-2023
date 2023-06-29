package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import pt.ipp.isep.dei.esoft.project.domain.controller.RespondVisitRequestController;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.ui.gui.Context;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Respond visit request gui.
 */
public class RespondVisitRequestGUI implements Initializable {

    /**
     * The Lbl visit data.
     */
    @FXML
    private Label lblVisitData;

    /**
     * The Txt area reply.
     */
    @FXML
    private TextArea txtAreaReply;

    /**
     * The Visit.
     */
    private Visit visit;

    /**
     * The Controller us 16.
     */
    private RespondVisitRequestController controllerUs16;

    /**
     * The Alert.
     */
    private final Alert alert = new Alert(AlertType.INFORMATION);

    /**
     * The Ctrl.
     */
    private  HelloController ctrl = new HelloController();

    /**
     * Instantiates a new Respond visit request gui.
     */
    public RespondVisitRequestGUI() {
    }

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Context context = Context.getInstance();
        lblVisitData.setText(context.getVisit().toString());
        visit = context.getVisit();
     }

    /**
     * Accept booking.
     *
     * @param event the event
     */
    @FXML
    void acceptBooking(ActionEvent event) {
    
        controllerUs16 = new RespondVisitRequestController();
        try{
            if(controllerUs16.notifyClient(visit, txtAreaReply.getText(), true)){
                System.out.println("Email has been send and saved.");
            }
        }
        catch (Exception e){
            System.out.println("Error sending email");
        }
        controllerUs16.deleteVisit(visit);

        alert.setTitle( "Message was send" );
        alert.setHeaderText( "Message was send" );
        alert.setContentText( "Message was send" );  

        // Add event handler to the alert's onCloseRequest
        alert.setOnCloseRequest(e -> {
            try {
                ctrl.switchTo(event,"agentMenu");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        // Show the alert
        alert.showAndWait();

    
    }

    /**
     * Reject booking.
     *
     * @param event the event
     */
    @FXML
    void rejectBooking(ActionEvent event) {
        
        controllerUs16 = new RespondVisitRequestController();
        try{
            if(controllerUs16.notifyClient(visit, txtAreaReply.getText(), false)){
                System.out.println("Email has been send and saved.");
            }
        }
        catch (Exception e){
            System.out.println("Error sending email");
        }
        controllerUs16.deleteVisit(visit); 
        
        alert.setTitle( "Message was send" );
        alert.setHeaderText( "Message was send" );
        alert.setContentText( "Message was send" );
        
         // Add event handler to the alert's onCloseRequest
        alert.setOnCloseRequest(e -> {
            try {
                ctrl.switchTo(event,"agentMenu");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        // Show the alert
        alert.showAndWait();

        
    }

}