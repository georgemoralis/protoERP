/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.ctl.cbbDateTimePicker.CbbDateTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author giorg
 */
public class Invoice1DetailView implements Initializable {

    @FXML
    private Label invoiceTypeLabel;
    @FXML
    private Label plantLabel;
    @FXML
    private CbbDateTimePicker dateTimePicker;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
