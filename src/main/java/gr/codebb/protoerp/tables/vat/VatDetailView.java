/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 02/04/2021 (gmoralis) - Initial full implementation
 */
package gr.codebb.protoerp.tables.vat;

import gr.codebb.ctl.CbbBigDecimal;
import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.crud.DetailCrud;
import gr.codebb.lib.crud.annotation.CheckBoxProperty;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.DecimalDigits;
import gr.codebb.protoerp.mydata.masterdata.MasterDataQueries;
import gr.codebb.protoerp.mydata.masterdata.VatmdEntity;
import gr.codebb.protoerp.settings.company.CompanyUtil;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import lombok.Getter;
import net.synedra.validatorfx.Validator;
import org.controlsfx.control.MaskerPane;

public class VatDetailView implements Initializable {

  @FXML @Getter private StackPane mainStackPane;
  @FXML private TextField textId;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textDescription;

  @FXML @CheckBoxProperty CheckBox checkBoxActive;
  @FXML private ComboBox<VatmdEntity> mydataCombo;
  @FXML private CbbBigDecimal bVatRate;

  private MaskerPane masker = new MaskerPane();
  private final DetailCrud<VatEntity> detailCrud = new DetailCrud<>(this);
  private Validator validator = new Validator();

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    mainStackPane.getChildren().add(masker);
    masker.setVisible(false);
    new ComboboxService<>(MasterDataQueries.getMdVatDatabase(true), mydataCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(mydataCombo);
    bVatRate.initBigDecimal(
        BigDecimal.ZERO,
        new DecimalFormat(
            DecimalDigits.getDecimalFormat(DecimalDigits.PERCENT_VAT.getSettingName())));

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
        .dependsOn("vatmydata", mydataCombo.valueProperty())
        .withMethod(
            c -> {
              VatmdEntity mydata = c.get("vatmydata");
              if (mydata == null) {
                c.error("Η αντιστοίχιση με mydata είναι υποχρεωτική");
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

  public void fillData(VatEntity e) {
    detailCrud.loadModel(e);
    textId.setText(e.getId().toString());
    bVatRate.setNumber(e.getVatRate());
    mydataCombo.getSelectionModel().select(e.getMydata_vat());
  }

  public boolean save() {
    GenericDao gdao = new GenericDao(VatEntity.class, PersistenceManager.getEmf());
    detailCrud.saveModel(new VatEntity());
    VatEntity vat = detailCrud.getModel();
    // non-support on detailCrud
    vat.setVatRate(bVatRate.getNumber());
    vat.setMydata_vat(mydataCombo.getSelectionModel().getSelectedItem());
    vat.setCompany(CompanyUtil.getCurrentCompany());
    VatEntity saved = (VatEntity) gdao.createEntity(vat);
    textId.setText(Long.toString(saved.getId()));
    return true;
  }

  public void saveEdit() {
    GenericDao gdao = new GenericDao(VatEntity.class, PersistenceManager.getEmf());
    detailCrud.saveModel((VatEntity) gdao.findEntity(Long.valueOf(textId.getText())));
    VatEntity vat = detailCrud.getModel();
    // non-support on detailCrud
    vat.setVatRate(bVatRate.getNumber());
    vat.setMydata_vat(mydataCombo.getSelectionModel().getSelectedItem());
    vat.setCompany(CompanyUtil.getCurrentCompany());
    gdao.updateEntity(vat);
  }
}
