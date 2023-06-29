package pt.ipp.isep.dei.esoft.project.ui.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;


/**
 * The type Hello controller.
 */
public class HelloController {

    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Scene scene;
    /**
     * The Root.
     */
    Parent root;


    /**
     * Switch to.
     *
     * @param event    the event
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void switchTo(ActionEvent event,String filename) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/" + filename + ".fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switch to.
     *
     * @param event    the event
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void switchTo(MouseEvent event, String filename) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/" + filename + ".fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Open modal window fxml loader.
     *
     * @param event        the event
     * @param filename     the filename
     * @param parentWindow the parent window
     * @return the fxml loader
     * @throws IOException the io exception
     */
    public FXMLLoader openModalWindow(ActionEvent event,String filename, Window parentWindow ) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+filename+".fxml"));
        root = loader.load();
        stage = new Stage();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initOwner(parentWindow);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.show();

        return loader;

    }

}
