/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */

/**
 * Να γίνουν 1)Ελεγχεται αν ο αριθμός εγκατάστασης υπάρχει σε άλλη εγγραφη. Συμφωνα με τις οδηγίες
 * για τα mydata αν δεν ξέρουμε το αριθμό εγκαταστασης βάζουμε 0. Αρα θα πρέπει να επιτρέπεται
 * διπλοεγγραφή
 */
package gr.codebb.protoerp.settings.company;

import eu.taxofficer.protoerp.company.entities.CompanyPlantsEntity;
import gr.codebb.codebblib.validatorfx.Validator;
import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.crud.DetailCrud;
import gr.codebb.lib.crud.annotation.CheckBoxProperty;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.protoerp.settings.countries.CountriesEntity;
import gr.codebb.protoerp.settings.countries.CountriesQueries;
import gr.codebb.protoerp.settings.doy.DoyEntity;
import gr.codebb.protoerp.settings.doy.DoyQueries;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import lombok.Getter;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.validation.ValidationSupport;

public class PlantsDetailView implements Initializable {

  @FXML @Getter private TextField textId;
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

  @FXML private SearchableComboBox<DoyEntity> doyCombo;

  private final DetailCrud<CompanyPlantsEntity> detailCrud = new DetailCrud<>(this);
  private ValidationSupport validation;
  private Validator validator = new Validator();

  public void setNewPlant(boolean empty) {
    if (empty) {
      textCode.setText("0");
      textDescription.setText("'Εδρα");
    }
    checkBoxActive.setSelected(true);
    CountryCombo.getSelectionModel()
        .select(CountriesQueries.getCountryByCode("GR")); // add greece by default
  }

  public boolean validateControls() {
    validator.validate();
    if (validator.containsErrors()) {
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
    new ComboboxService<>(DoyQueries.getDoyDatabase(true), doyCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(doyCombo);
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
    validator
        .createCheck()
        .dependsOn("code", textCode.textProperty())
        .withMethod(
            c -> {
              String code = c.get("code");
              if (code.isEmpty()) {
                c.error("O Κωδικός εγκατάστασης δεν μπορεί να είναι κενός");
              } else if (!code.matches("[0-9 -]+")) {
                c.error("O Κωδικός εγκατάστασης πρέπει να είναι αριθμός");
              }
            })
        .decorates(textCode)
        .immediate();
    validator
        .createCheck()
        .dependsOn("address", textAddress.textProperty())
        .withMethod(
            c -> {
              String address = c.get("address");
              if (address.isEmpty()) {
                c.error("Η διεύθυνση δεν μπορεί να είναι κενή");
              }
            })
        .decorates(textAddress)
        .immediate();
    validator
        .createCheck()
        .dependsOn("tk", textTk.textProperty())
        .withMethod(
            c -> {
              String tk = c.get("tk");
              if (tk.isEmpty()) {
                c.error("Ο ταχυδρομικός κώδικας δεν μπορεί να είναι κενός");
              }
            })
        .decorates(textTk)
        .immediate();
    validator
        .createCheck()
        .dependsOn("city", textCity.textProperty())
        .withMethod(
            c -> {
              String city = c.get("city");
              if (city.isEmpty()) {
                c.error("Η πόλη δεν μπορεί να είναι κενή");
              }
            })
        .decorates(textCity)
        .immediate();
    validator
        .createCheck()
        .dependsOn("phone", textPhone.textProperty())
        .withMethod(
            c -> {
              String phone = c.get("phone");
              if (!phone.matches("[0-9 -]+")) {
                c.warn("Το Τηλέφωνο πρέπει να είναι αριθμός");
              }
            })
        .decorates(textPhone)
        .immediate();
    validator
        .createCheck()
        .dependsOn("fax", textFax.textProperty())
        .withMethod(
            c -> {
              String fax = c.get("fax");
              if (!fax.matches("[0-9 -]+")) {
                c.warn("Το Fax πρέπει να είναι αριθμός");
              }
            })
        .decorates(textFax)
        .immediate();

    validator
        .createCheck()
        .dependsOn("doy", doyCombo.valueProperty())
        .withMethod(
            c -> {
              DoyEntity doy = c.get("doy");
              if (doy == null) {
                c.error("Η ΔΟΥ είναι υποχρεωτική");
              }
            })
        .decorates(doyCombo)
        .immediate();

    validator
        .createCheck()
        .dependsOn("code", textCode.textProperty())
        .withMethod(
            c -> {
              String code = c.get("code");
              if (code == null || code.isEmpty()) {
                return;
              }
              CompanyPlantsEntity codef = CompanyQueries.getPlantByCode(Integer.parseInt(code));
              if (codef != null) // if exists
              {
                if (!textId.getText().isEmpty()) { // if it is not a new entry
                  if (codef.getId()
                      != Long.parseLong(textId.getText())) // check if found id is the same
                  {
                    c.error("H εγκατάσταση με αριθμό " + code + " υπάρχει ήδη");
                  }
                } else {
                  c.error("H εγκατάσταση με αριθμό " + code + " υπάρχει ήδη");
                }
              }
            })
        .decorates(textCode);
  }

  public void fillData(CompanyPlantsEntity e) {
    detailCrud.loadModel(e);
    if (e.getId() != null) {
      textId.setText(e.getId().toString());
    }
    CountryCombo.getSelectionModel().select(e.getCountry());
    doyCombo.getSelectionModel().select(e.getDoy());
  }

  public CompanyPlantsEntity saveNewPlant() {
    detailCrud.saveModel(new CompanyPlantsEntity());
    CompanyPlantsEntity plant = detailCrud.getModel();
    // non-support on detailCrud
    plant.setCountry(CountryCombo.getSelectionModel().getSelectedItem());
    plant.setDoy(doyCombo.getSelectionModel().getSelectedItem());
    return plant;
  }

  public CompanyPlantsEntity saveEdit(CompanyPlantsEntity splant) {
    detailCrud.saveModel(splant);
    CompanyPlantsEntity plant = detailCrud.getModel();
    // non-support on detailCrud
    plant.setCountry(CountryCombo.getSelectionModel().getSelectedItem());
    plant.setDoy(doyCombo.getSelectionModel().getSelectedItem());
    return plant;
  }
}
