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
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import org.controlsfx.control.SearchableComboBox;

public class InvoiceLinesView implements Initializable {

  @FXML private CbbClearableTextField textCode;
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

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // initialaze spinner
    // Value factory.
    SpinnerValueFactory<Integer> valueFactory =
        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10);
    posSpinner.setValueFactory(valueFactory);

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
                CalcTotalValue();
                apoforologisi();
              }
            });
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

  @FXML
  private void searchItemAction(ActionEvent event) {}
}
