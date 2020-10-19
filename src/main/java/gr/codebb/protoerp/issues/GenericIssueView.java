/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 19/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp.issues;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;


public class GenericIssueView implements Initializable {

    @FXML
    private StackPane stackPaneMain;
    @FXML
    private TextArea detailsTextArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void sendAction(ActionEvent event) {
    }
    
}
