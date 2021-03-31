/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 31/03/2021 (gmoralis) -Initial
 */
package gr.codebb.protoerp.tables.measurementUnits;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.crud.DetailCrud;
import gr.codebb.lib.crud.annotation.CheckBoxProperty;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.protoerp.mydata.masterdata.MasterDataQueries;
import gr.codebb.protoerp.mydata.masterdata.MeasureUnitmdEntity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import net.synedra.validatorfx.Validator;
import org.controlsfx.control.MaskerPane;

public class MeasurementUnitsDetailView implements Initializable {

  @FXML private StackPane mainStackPane;
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
}
