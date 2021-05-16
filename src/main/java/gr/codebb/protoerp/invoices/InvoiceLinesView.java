/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 05/05/2021 - Fixed exclude vat validator (better)
 * 05/05/2021 - Spinner alignment to right
 * 29/04/2021 - Initial
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.codebblib.validatorfx.Validator;
import gr.codebb.ctl.CbbBigDecimal;
import gr.codebb.ctl.CbbBigDecimalLabel;
import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.lib.util.AlertHelper;
import gr.codebb.lib.util.DecimalDigits;
import gr.codebb.lib.util.NumberUtil;
import gr.codebb.protoerp.items.ItemsEntity;
import gr.codebb.protoerp.items.ItemsQueries;
import gr.codebb.protoerp.mydata.masterdata.MasterDataQueries;
import gr.codebb.protoerp.mydata.masterdata.VatmdExemptionEntity;
import gr.codebb.protoerp.settings.SettingsHelper;
import gr.codebb.protoerp.tables.measurementUnits.MeasurementUnitsEntity;
import gr.codebb.protoerp.tables.measurementUnits.MeasurementUnitsQueries;
import gr.codebb.protoerp.tables.vat.VatEntity;
import gr.codebb.protoerp.tables.vat.VatQueries;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import org.controlsfx.control.SearchableComboBox;

public class InvoiceLinesView implements Initializable {

  @FXML @Getter private CbbClearableTextField textCode;
  @FXML private CbbClearableTextField textBarcode;
  @FXML private TextArea textDescription;
  @FXML private ComboBox<VatEntity> fpaCategoryCombo;
  @FXML private ComboBox<MeasurementUnitsEntity> monMetrisisCombo;
  @FXML private CbbBigDecimal quantField;
  @FXML private CbbBigDecimal priceOneField;
  @FXML private CbbBigDecimal priceOneWithVAT;
  @FXML private CbbBigDecimalLabel totalField;
  @FXML private CbbBigDecimalLabel totalWithVatField;
  @FXML private CbbBigDecimal discountPercentField;
  @FXML private CbbBigDecimalLabel discountPriceField;
  @FXML private CbbBigDecimalLabel totalDiscField;
  @FXML private CbbBigDecimalLabel totalDiscWithVatField;
  @FXML private Spinner<Integer> posSpinner;
  @FXML private SearchableComboBox<ItemsEntity> itemCombo;
  @FXML private SearchableComboBox<VatmdExemptionEntity> excludeVatCombo;
  private Validator validator = new Validator();

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // initialaze spinner
    // Value factory.
    SpinnerValueFactory<Integer> valueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10);
    posSpinner.setValueFactory(valueFactory);
    posSpinner.editorProperty().get().setAlignment(Pos.CENTER_RIGHT);

    // init bigdecimalfields
    intitializeBiDecimalFields();

    // fill comboboxes
    new ComboboxService<>(
            MeasurementUnitsQueries.getMeasurementsUnitsDatabase(true), monMetrisisCombo)
        .start();
    new ComboboxService<>(VatQueries.getVatDatabase(true), fpaCategoryCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(fpaCategoryCombo);
    DisplayableListCellFactory.setComboBoxCellFactory(monMetrisisCombo);
    new ComboboxService<>(MasterDataQueries.getMdVatExemptionDatabase(true), excludeVatCombo)
        .start();
    DisplayableListCellFactory.setComboBoxCellFactory(excludeVatCombo);
    new ComboboxService<>(ItemsQueries.getItemsDatabase(true), itemCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(itemCombo);

    // enable/disable vatexemption if vat is zero or anthing else
    fpaCategoryCombo
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (options, oldValue, newValue) -> {
              if (newValue != null) {
                if (newValue.getVatRate().compareTo(BigDecimal.ZERO) == 0) // activate vatexmp
                {
                  excludeVatCombo.setDisable(false);
                } else {
                  excludeVatCombo.setDisable(true);
                  excludeVatCombo.getSelectionModel().select(null);
                }
              }
            });

    discountPercentField
        .focusedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              CalcTotalValue();
            });
    quantField
        .focusedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              CalcTotalValue();
            });
    priceOneField
        .focusedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              forologisi();
            });
    priceOneWithVAT
        .focusedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              apoforologisi();
            });
    textBarcode
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.isEmpty()) {
                itemCombo.getSelectionModel().select(null);
              }
            });
    textCode
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.isEmpty()) {
                itemCombo.getSelectionModel().select(null);
              }
            });
    itemCombo
        .valueProperty()
        .addListener(
            (ObservableValue<? extends ItemsEntity> observable,
                ItemsEntity oldValue,
                ItemsEntity newValue) -> {
              if (newValue == null) {
                textBarcode.setText("");
                textDescription.setText("");
                textCode.setText("");
                quantField.setNumber(BigDecimal.ZERO);
                priceOneField.setNumber(BigDecimal.ZERO);
                fpaCategoryCombo.getSelectionModel().select(null);
                monMetrisisCombo.getSelectionModel().select(null);
                CalcTotalValue();
                apoforologisi();
              } else {
                textCode.setText(newValue.getCode());
                textBarcode.setText(newValue.getBarcode());
                textDescription.setText(newValue.getDescription());
                quantField.setNumber(BigDecimal.ONE);
                priceOneField.setNumber(newValue.getSellPrice());
                fpaCategoryCombo.getSelectionModel().select(newValue.getVatSell());
                monMetrisisCombo.getSelectionModel().select(newValue.getMeasureUnit());
                if (newValue.getVatExemp() != null) {
                  excludeVatCombo.getSelectionModel().select(newValue.getVatExemp());
                }
                CalcTotalValue();
                apoforologisi();
              }
            });
    fpaCategoryCombo
        .valueProperty()
        .addListener(
            (ObservableValue<? extends VatEntity> observable,
                VatEntity oldValue,
                VatEntity newValue) -> {
              if (newValue != null) {
                if (!priceOneField.getNumber().equals(BigDecimal.ZERO)) {
                  forologisi();
                } else {
                  apoforologisi();
                }
              }
            });
    textBarcode.setOnKeyPressed(
        (KeyEvent keyEvent) -> {
          if (keyEvent.getCode() == KeyCode.ENTER) {
            String barcode = textBarcode.getText();
            if (barcode.length() > 0) {
              ItemsEntity result = ItemsQueries.getItemByBarcode(barcode);
              if (result != null) {
                itemCombo.getSelectionModel().select(result);
              } else {
                AlertHelper.errorDialog(
                    textDescription.getScene().getWindow(),
                    "Το Είδος Δεν βρέθηκε\nΒεβαιωθείτε ότι το barcode υπάρχει στα είδη\n");
                itemCombo.getSelectionModel().select(null);
              }
            }
            keyEvent.consume();
          }
        });
    textCode.setOnKeyPressed(
        (KeyEvent keyEvent) -> {
          if (keyEvent.getCode() == KeyCode.ENTER) {
            String code = textCode.getText();
            if (code.length() > 0) {
              ItemsEntity result = ItemsQueries.getItemByCode(code);
              if (result != null) {
                itemCombo.getSelectionModel().select(result);
              } else {
                AlertHelper.errorDialog(
                    textDescription.getScene().getWindow(),
                    "Το Είδος Δεν βρέθηκε\nΒεβαιωθείτε ότι το barcode υπάρχει στα είδη\n");
                itemCombo.getSelectionModel().select(null);
              }
            }
            keyEvent.consume();
          }
        });
    validator
        .createCheck()
        .dependsOn("vatexemp", excludeVatCombo.disabledProperty())
        .dependsOn("vatexemp2", excludeVatCombo.valueProperty())
        .withMethod(
            c -> {
              boolean vatex = c.get("vatexemp");
              if (!vatex) {
                VatmdExemptionEntity exp = c.get("vatexemp2");
                if (exp == null) {
                  c.error("Η Αιτία εξαίρεσης ειναι υποχρεωτικη");
                }
              }
            })
        .decorates(excludeVatCombo);
    // .immediate();//can't do immediate throws an exception on edit

    validator
        .createCheck()
        .dependsOn("vat", fpaCategoryCombo.valueProperty())
        .withMethod(
            c -> {
              VatEntity vat = c.get("vat");
              if (vat == null) {
                c.error("Η κατηγορία φ.π.α. είναι υποχρεωτική");
              }
            })
        .decorates(fpaCategoryCombo)
        .immediate();

    validator
        .createCheck()
        .dependsOn("measureunit", monMetrisisCombo.valueProperty())
        .withMethod(
            c -> {
              MeasurementUnitsEntity measure = c.get("measureunit");
              if (measure == null) {
                c.error("Η μονάδα μέτρησης είναι υποχρεωτική");
              }
            })
        .decorates(monMetrisisCombo)
        .immediate();

    validator
        .createCheck()
        .dependsOn("item", itemCombo.valueProperty())
        .withMethod(
            c -> {
              ItemsEntity item = c.get("item");
              if (item == null) {
                c.error("Το είδος είναι υποχρεωτικό");
              }
            })
        .decorates(itemCombo)
        .immediate();

    validator
        .createCheck()
        .dependsOn("description", textDescription.textProperty())
        .withMethod(
            c -> {
              String description = c.get("description");
              if (description.isEmpty()) {
                c.error("Η περιγραφή δεν μπορεί να είναι κενή");
              }
            })
        .decorates(textDescription)
        .immediate();
  }

  /** This will initialize Big Decimal Fields using DecimalFormat configuration from Settings */
  public void intitializeBiDecimalFields() {
    totalField.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName())));
    totalWithVatField.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName())));
    discountPriceField.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName())));
    totalDiscField.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName())));
    totalDiscWithVatField.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(DecimalDigits.getDecimalFormat(DecimalDigits.VALUES.getSettingName())));
    quantField.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(DecimalDigits.getDecimalFormat(DecimalDigits.QUANTITY.getSettingName())));
    priceOneField.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(DecimalDigits.getDecimalFormat(DecimalDigits.UNIT.getSettingName())));
    priceOneWithVAT.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(DecimalDigits.getDecimalFormat(DecimalDigits.UNIT.getSettingName())));
    discountPercentField.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(
            DecimalDigits.getDecimalFormat(DecimalDigits.PERCENT_DISC.getSettingName())));
  }

  private void forologisi() {
    if (fpaCategoryCombo.getSelectionModel().getSelectedItem() != null) {
      VatEntity ent = fpaCategoryCombo.getSelectionModel().getSelectedItem();
      priceOneWithVAT.setNumber(
          NumberUtil.forologisi(
              priceOneField.getNumber(),
              ent.getVatRate(),
              SettingsHelper.loadIntegerSetting("unitDecimal")));
      CalcTotalValue();
    } else {
      priceOneWithVAT.setNumber(BigDecimal.ZERO);
    }
  }

  private void apoforologisi() {
    if (fpaCategoryCombo.getSelectionModel().getSelectedItem() != null) {
      VatEntity ent = fpaCategoryCombo.getSelectionModel().getSelectedItem();
      priceOneField.setNumber(
          NumberUtil.apoforologisi(
              priceOneWithVAT.getNumber(),
              ent.getVatRate(),
              SettingsHelper.loadIntegerSetting("unitDecimal")));
      CalcTotalValue();
    } else {
      priceOneField.setNumber(BigDecimal.ZERO);
      priceOneWithVAT.setNumber(BigDecimal.ZERO);
    }
  }

  private void CalcTotalValue() {
    totalField.setNumber(
        NumberUtil.multiply(
            quantField.getNumber(),
            priceOneField.getNumber(),
            SettingsHelper.loadIntegerSetting("valuesDecimal")));
    totalWithVatField.setNumber(
        NumberUtil.multiply(
            quantField.getNumber(),
            priceOneWithVAT.getNumber(),
            SettingsHelper.loadIntegerSetting("valuesDecimal")));
    discountPriceField.setNumber(
        NumberUtil.percent(
            totalField.getNumber(),
            discountPercentField.getNumber(),
            SettingsHelper.loadIntegerSetting("valuesDecimal")));
    totalDiscField.setNumber(
        NumberUtil.substract(
            totalField.getNumber(),
            discountPriceField.getNumber(),
            SettingsHelper.loadIntegerSetting("valuesDecimal")));
    if (fpaCategoryCombo.getSelectionModel().getSelectedItem() != null) {
      totalDiscWithVatField.setNumber(
          NumberUtil.forologisi(
              totalDiscField.getNumber(),
              fpaCategoryCombo.getSelectionModel().getSelectedItem().getVatRate(),
              SettingsHelper.loadIntegerSetting("valuesDecimal")));
    } else {
      totalDiscWithVatField.setNumber(BigDecimal.ZERO);
    }
  }

  /**
   * Set new value to spinner control
   *
   * @param value
   */
  public void setPos(int value) {
    posSpinner.getValueFactory().setValue(value);
  }

  @FXML
  private void searchItemAction(ActionEvent event) {}

  public InvoiceLinesEntity getResult() {
    InvoiceLinesEntity line = new InvoiceLinesEntity();
    line.setItem(itemCombo.getSelectionModel().getSelectedItem());
    line.setCode(textCode.getText());
    line.setBarcode(textBarcode.getText());
    line.setDescription(textDescription.getText());
    line.setQuantity(quantField.getNumber());
    line.setUnitPrice(priceOneField.getNumber());
    line.setTotalNoDisc(totalField.getNumber());

    line.setPercentDisc(discountPercentField.getNumber());

    line.setDiscount(discountPriceField.getNumber());
    line.setTotal(totalDiscField.getNumber());

    line.setVat(fpaCategoryCombo.getSelectionModel().getSelectedItem());
    line.setVatRate(fpaCategoryCombo.getSelectionModel().getSelectedItem().getVatRate());
    line.setVatExemp(excludeVatCombo.getSelectionModel().getSelectedItem());

    line.setMeasureUnit(monMetrisisCombo.getSelectionModel().getSelectedItem());
    line.setMeasureShortName(monMetrisisCombo.getSelectionModel().getSelectedItem().getShortName());

    line.setPosIndex(posSpinner.getValue());
    return line;
  }

  public void EditRecord(ObservableList<InvoiceLinesEntity> list, int row) {
    itemCombo.getSelectionModel().select(list.get(row).getItem());
    textBarcode.setText(list.get(row).getBarcode());
    textCode.setText(list.get(row).getCode());
    textDescription.setText(list.get(row).getDescription());
    quantField.setNumber(list.get(row).getQuantity());
    priceOneField.setNumber(list.get(row).getUnitPrice());
    totalField.setNumber(list.get(row).getTotalNoDisc());
    discountPercentField.setNumber(list.get(row).getPercentDisc());
    discountPriceField.setNumber(list.get(row).getDiscount());
    totalDiscField.setNumber(list.get(row).getTotal());
    posSpinner.getValueFactory().setValue(list.get(row).getPosIndex());
    fpaCategoryCombo.getSelectionModel().select(list.get(row).getVat());
    monMetrisisCombo.getSelectionModel().select(list.get(row).getMeasureUnit());
    excludeVatCombo.getSelectionModel().select(list.get(row).getVatExemp());
    forologisi();
    CalcTotalValue();
  }

  public InvoiceLinesEntity saveEdited(InvoiceLinesEntity line) {
    line.setItem(itemCombo.getSelectionModel().getSelectedItem());
    line.setCode(textCode.getText());
    line.setBarcode(textBarcode.getText());
    line.setDescription(textDescription.getText());
    line.setQuantity(quantField.getNumber());
    line.setUnitPrice(priceOneField.getNumber());
    line.setTotalNoDisc(totalField.getNumber());

    line.setPercentDisc(discountPercentField.getNumber());

    line.setDiscount(discountPriceField.getNumber());
    line.setTotal(totalDiscField.getNumber());

    line.setVat(fpaCategoryCombo.getSelectionModel().getSelectedItem());
    line.setVatRate(fpaCategoryCombo.getSelectionModel().getSelectedItem().getVatRate());
    line.setVatExemp(excludeVatCombo.getSelectionModel().getSelectedItem());

    line.setMeasureUnit(monMetrisisCombo.getSelectionModel().getSelectedItem());
    line.setMeasureShortName(monMetrisisCombo.getSelectionModel().getSelectedItem().getShortName());

    line.setPosIndex(posSpinner.getValue());
    return line;
  }

  public boolean validateControls() {
    validator.validate();
    if (validator.containsErrors()) {
      AlertHelper.errorDialog(textDescription.getScene().getWindow(), "Ελέξτε την φόρμα για λάθη");
      return false;
    }
    return true;
  }
}
