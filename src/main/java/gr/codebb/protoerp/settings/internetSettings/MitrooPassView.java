/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 13/04/2021 (gmoralis) - Show passwords only if SHOW_PASSWORDS permission exists
 * 08/04/2021 (gmoralis) - Other way of saving / storing common passwords
 * 16/03/2021 (gmoralis) - Loading/saving specific username/passwords per company
 * 03/03/2021 (gmoralis) - Added load/saving of common passwords
 * 02/03/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.settings.internetSettings;

import eu.taxofficer.protoerp.company.entities.CompanyEntity;
import gr.codebb.codebblib.fxcontrols.CbbPasswordField;
import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.lib.crud.cellFactory.DisplayableListCellFactory;
import gr.codebb.lib.crud.services.ComboboxService;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.AuthUtil;
import gr.codebb.protoerp.settings.company.CompanyQueries;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.controlsfx.control.SearchableComboBox;

public class MitrooPassView implements Initializable {

  @FXML private CbbClearableTextField textusernamemitroou;
  @FXML private CbbPasswordField textpasswordmitroou;
  @FXML private CbbClearableTextField texVatrepresentant;
  @FXML private SearchableComboBox<CompanyEntity> companyCombo;
  @FXML private Button copyButton;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    new ComboboxService<>(CompanyQueries.getCompaniesWithMitrooCodes(true), companyCombo).start();
    DisplayableListCellFactory.setComboBoxCellFactory(companyCombo);
    copyButton.disableProperty().bind(companyCombo.valueProperty().isNull());
    if (!AuthUtil.isPermitted("SHOW_PASSWORDS")) {
      textpasswordmitroou.disableRightButton();
    }
  }

  public void companyLoad() {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    CompanyEntity selected = (CompanyEntity) session.getAttribute("company");
    if (selected.getMitroo_username() != null && selected.getMitroo_username().length() > 0) {
      textusernamemitroou.setText(selected.getMitroo_username());
      textpasswordmitroou.setText(selected.getMitroo_password());
      texVatrepresentant.setText(selected.getMitroo_vatRepresentant());
    }
  }

  public void save() {
    Subject currentUser = SecurityUtils.getSubject();
    Session session = currentUser.getSession();
    CompanyEntity selected = (CompanyEntity) session.getAttribute("company");
    selected.setMitroo_username(textusernamemitroou.getText());
    selected.setMitroo_password(textpasswordmitroou.getText());
    selected.setMitroo_vatRepresentant(texVatrepresentant.getText());
    GenericDao gdao = new GenericDao(CompanyEntity.class, PersistenceManager.getEmf());
    gdao.updateEntity(selected);
  }

  public void tempStore(String[] data) {
    data[0] = textusernamemitroou.getText();
    data[1] = textpasswordmitroou.getText();
    data[2] = texVatrepresentant.getText();
  }

  @FXML
  private void copyAction(ActionEvent event) {
    textusernamemitroou.setText(
        companyCombo.getSelectionModel().getSelectedItem().getMitroo_username());
    textpasswordmitroou.setText(
        companyCombo.getSelectionModel().getSelectedItem().getMitroo_password());
    texVatrepresentant.setText(
        companyCombo.getSelectionModel().getSelectedItem().getMitroo_vatRepresentant());
  }
}
