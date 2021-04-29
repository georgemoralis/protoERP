/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.ctl.CbbBigDecimal;
import gr.codebb.ctl.CbbBigDecimalLabel;
import gr.codebb.ctl.CbbClearableTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author snow
 */
public class InvoiceLinesView implements Initializable {

  @FXML private CbbClearableTextField textCode;
  @FXML private CbbClearableTextField textBarcode;
  @FXML private TextArea textDescription;
  @FXML private ComboBox<?> fpaCategoryCombo;
  @FXML private ComboBox<?> monMetrisisCombo;
  @FXML private CbbBigDecimal quantField;
  @FXML private CbbBigDecimal priceOneField;
  @FXML private CbbBigDecimal priceOneWithVAT;
  @FXML private CbbBigDecimalLabel totalField;
  @FXML private CbbBigDecimalLabel totalWithVatField;
  @FXML private CbbBigDecimal discountPercentField;
  @FXML private CbbBigDecimalLabel discountPriceField;
  @FXML private CbbBigDecimalLabel totalDiscField;
  @FXML private CbbBigDecimalLabel totalDiscWithVatField;
  @FXML private Spinner<?> posSpinner;
  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }
}
