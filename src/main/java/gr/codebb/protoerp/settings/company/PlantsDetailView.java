/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 08/03/3021 (georgemoralis) - Added save/load/edit actions
 * 07/03/2021 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.settings.company;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.lib.crud.DetailCrud;
import gr.codebb.lib.crud.annotation.CheckBoxProperty;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.protoerp.settings.countries.CountriesEntity;
import gr.codebb.protoerp.settings.countries.CountriesQueries;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

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

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    new ComboboxService<>(CountriesQueries.getCountriesDatabase(true), CountryCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(CountryCombo);
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
