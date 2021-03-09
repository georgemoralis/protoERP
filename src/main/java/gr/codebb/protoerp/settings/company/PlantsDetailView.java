/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 09/03/2021 (georgemoralis) - Added controlsfx validation (todo change it with validatorFX sometime later)
 * 08/03/3021 (georgemoralis) - Added save/load/edit actions
 * 07/03/2021 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.crud.DetailCrud;
import gr.codebb.lib.crud.annotation.CheckBoxProperty;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.protoerp.settings.countries.CountriesEntity;
import gr.codebb.protoerp.settings.countries.CountriesQueries;
import gr.codebb.protoerp.util.validation.CustomValidationDecoration;
import gr.codebb.protoerp.util.validation.Validators;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;

public class PlantsDetailView implements Initializable {

  @FXML private TextField textId;
  @FXML @CheckBoxProperty private CheckBox checkBoxActive;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.INT)
  private CbbClearableTextField textCode;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textDescription;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textAddress;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textArea;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textTk;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textCity;

  @FXML private SearchableComboBox<CountriesEntity> CountryCombo;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textPhone;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textFax;

  private final DetailCrud<PlantsEntity> detailCrud = new DetailCrud<>(this);
  private ValidationSupport validation;

  /** Initializes the controller class. */
  public void setNewPlant(boolean empty) {
    if (empty) {
      textCode.setText("0");
      textDescription.setText("'Εδρα");
    }
    checkBoxActive.setSelected(true);
    CountryCombo.getSelectionModel()
        .select(CountriesQueries.getCountryByCode("GR")); // add greece by default
  }

  private void registerValidators() {

    validation.registerValidator(textDescription, Validators.notEmptyValidator());
    validation.registerValidator(
        textCode,
        Validators.combine(
            Validators.notEmptyValidator(), Validators.onlyNumbersValidator(Severity.ERROR)));
    validation.registerValidator(textTk, Validators.notEmptyValidator());
    validation.registerValidator(textCity, Validators.notEmptyValidator());
    validation.registerValidator(
        textPhone, false, Validators.onlyNumbersValidator(Severity.WARNING));
    validation.registerValidator(textFax, false, Validators.onlyNumbersValidator(Severity.WARNING));
  }

  public boolean validateControls() {
    if (validation.isInvalid()) {
      Validators.showValidationResult(validation);
      AlertDlg.create()
          .type(AlertDlg.Type.ERROR)
          .message("Ελέξτε την φόρμα για λάθη")
          .title("Πρόβλημα")
          .owner(textDescription.getScene().getWindow())
          .modality(Modality.APPLICATION_MODAL)
          .showAndWait();
      return false;
    }
    return true;
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    new ComboboxService<>(CountriesQueries.getCountriesDatabase(true), CountryCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(CountryCombo);
    Platform.runLater(
        () -> {
          this.validation = new ValidationSupport();
          this.validation.setValidationDecorator(new CustomValidationDecoration());
          Validators.createValidators();
          registerValidators();
        });
  }

  public void fillData(PlantsEntity e) {
    detailCrud.loadModel(e);
    if (e.getId() != null) {
      textId.setText(e.getId().toString());
    }
    CountryCombo.getSelectionModel().select(e.getCountry());
  }

  public PlantsEntity saveNewPlant() {
    detailCrud.saveModel(new PlantsEntity());
    PlantsEntity plant = detailCrud.getModel();
    // non-support on detailCrud
    plant.setCountry(CountryCombo.getSelectionModel().getSelectedItem());
    return plant;
  }

  public PlantsEntity saveEdit(PlantsEntity splant) {
    detailCrud.saveModel(splant);
    PlantsEntity plant = detailCrud.getModel();
    // non-support on detailCrud
    plant.setCountry(CountryCombo.getSelectionModel().getSelectedItem());
    return plant;
  }
}
