/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 01/04/2021 (gmoralis) - Added edited entry
 * 01/04/2021 (gmoralis) - Added saving new entry
 * 31/03/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.tables.measurementUnits;

import gr.codebb.codebblib.validatorfx.Validator;
import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.crud.DetailCrud;
import gr.codebb.lib.crud.annotation.CheckBoxProperty;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.mydata.masterdata.MasterDataQueries;
import gr.codebb.protoerp.mydata.masterdata.MeasureUnitmdEntity;
import gr.codebb.protoerp.settings.company.CompanyUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import lombok.Getter;
import org.controlsfx.control.MaskerPane;

public class MeasurementUnitsDetailView implements Initializable {

  @FXML @Getter private StackPane mainStackPane;
  @FXML private TextField textId;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textShortName;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textDescription;

  @FXML @CheckBoxProperty private CheckBox checkBoxActive;
  @FXML private ComboBox<MeasureUnitmdEntity> mydataCombo;

  private MaskerPane masker = new MaskerPane();
  private final DetailCrud<MeasurementUnitsEntity> detailCrud = new DetailCrud<>(this);
  private Validator validator = new Validator();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    mainStackPane.getChildren().add(masker);
    masker.setVisible(false);
    new ComboboxService<>(MasterDataQueries.getMdMeasureDatabase(true), mydataCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(mydataCombo);

    validator
        .createCheck()
        .dependsOn("description", textDescription.textProperty())
        .withMethod(
            c -> {
              String rolename = c.get("description");
              if (rolename.isEmpty()) {
                c.error("Η περιγραφή δεν μπορεί να είναι κενή");
              }
            })
        .decorates(textDescription)
        .immediate();

    validator
        .createCheck()
        .dependsOn("measuremydata", mydataCombo.valueProperty())
        .withMethod(
            c -> {
              MeasureUnitmdEntity mydata = c.get("measuremydata");
              if (mydata == null) {
                c.error("Η αντιστοίχηση με mydata είναι υποχρεωτική");
              }
            })
        .decorates(mydataCombo)
        .immediate();

    checkBoxActive.setSelected(true); // if new then it is active
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

  public void fillData(MeasurementUnitsEntity e) {

    detailCrud.loadModel(e);
    textId.setText(Long.toString(e.getId()));
    mydataCombo.getSelectionModel().select(e.getMydata_measureUnit());
  }

  public boolean save() {
    GenericDao gdao = new GenericDao(MeasurementUnitsEntity.class, PersistenceManager.getEmf());
    detailCrud.saveModel(new MeasurementUnitsEntity());
    MeasurementUnitsEntity measure = detailCrud.getModel();
    measure.setMydata_measureUnit(mydataCombo.getSelectionModel().getSelectedItem());
    measure.setCompany(CompanyUtil.getCurrentCompany());
    MeasurementUnitsEntity saved = (MeasurementUnitsEntity) gdao.createEntity(measure);
    textId.setText(Long.toString(saved.getId()));
    return true;
  }

  public void SaveEdit() {
    GenericDao gdao = new GenericDao(MeasurementUnitsEntity.class, PersistenceManager.getEmf());
    detailCrud.saveModel((MeasurementUnitsEntity) gdao.findEntity(Long.valueOf(textId.getText())));
    MeasurementUnitsEntity ms = detailCrud.getModel();
    ms.setCompany(CompanyUtil.getCurrentCompany());
    gdao.updateEntity(ms);
  }
}
