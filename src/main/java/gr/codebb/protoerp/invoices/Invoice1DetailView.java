/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.ctl.CbbSearchableTextField;
import gr.codebb.protoerp.tables.InvoiceTypes.InvoiceTypesEntity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class Invoice1DetailView implements Initializable {

    @FXML
    private Label invoiceTypeLabel;
    @FXML
    private Label plantLabel;
    @FXML
    private CbbSearchableTextField traderCombo;

    private InvoiceTypesEntity invoiceType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setInvoiceType(InvoiceTypesEntity invoiceType) // only if it is new entry
    {
        this.invoiceType = invoiceType;
        if (invoiceType.getSeira() == null) {
            invoiceTypeLabel.setText(invoiceType.getShortName() + " - " + invoiceType.getName());
        } else {
            invoiceTypeLabel.setText(
                    invoiceType.getShortName()
                    + " - "
                    + invoiceType.getName()
                    + " (ΣΕΙΡΑ : "
                    + invoiceType.getSeira()
                    + " )");
        }
        plantLabel.setText(invoiceType.getPlantS());
    }
}
