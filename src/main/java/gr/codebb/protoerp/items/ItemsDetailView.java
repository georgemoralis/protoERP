/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 05/04/2021 - Initial
 */
package gr.codebb.protoerp.items;

import gr.codebb.ctl.CbbBigDecimal;
import gr.codebb.ctl.CbbClearableTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.SearchableComboBox;

public class ItemsDetailView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private TextField textId;
  @FXML private CbbClearableTextField textCode;
  @FXML private CbbClearableTextField textBarcode;
  @FXML private CbbClearableTextField textDescription;
  @FXML private CheckBox checkBoxActive;
  @FXML private ComboBox<?> comboItemType;
  @FXML private ComboBox<?> comboMeasureUnit;
  @FXML private ComboBox<?> comboVatSell;
  @FXML private CbbBigDecimal bdecSellPrice;
  @FXML private CbbBigDecimal bdecSellPriceWithVat;
  @FXML private TextArea textAreaNotes;
  @FXML private Label stockLabel;
  @FXML private SearchableComboBox<?> VatExempCombo;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }
}
