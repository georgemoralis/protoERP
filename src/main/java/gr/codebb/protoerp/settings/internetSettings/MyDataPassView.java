/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 19/03/2021 (gmoralis) - First version finished
 * 18/03/2021 (gmoralis) - Initial commit
 */
package gr.codebb.protoerp.settings.internetSettings;

import gr.codebb.ctl.CbbClearableTextField;
import gr.codebb.lib.crud.DetailCrud;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.settings.company.CompanyUtil;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.ToggleSwitch;

/**
 * FXML Controller class
 *
 * @author snow
 */
public class MyDataPassView implements Initializable {

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textUserMyData;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textPassMyData;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textDemoUserMyData;

  @FXML
  @TextFieldProperty(type = TextFieldProperty.Type.STRING)
  private CbbClearableTextField textDemoPassMyData;

  @FXML private ToggleSwitch toggleDemoMyDataEnabled;

  private final DetailCrud<CompanyEntity> detailCrud = new DetailCrud<>(this);

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    textUserMyData.disableProperty().bind(toggleDemoMyDataEnabled.selectedProperty());
    textPassMyData.disableProperty().bind(toggleDemoMyDataEnabled.selectedProperty());
    textDemoUserMyData.disableProperty().bind(toggleDemoMyDataEnabled.selectedProperty().not());
    textDemoPassMyData.disableProperty().bind(toggleDemoMyDataEnabled.selectedProperty().not());
  }

  public void companyPassLoad() {
    CompanyEntity c = CompanyUtil.getCurrentCompany();
    detailCrud.loadModel(c);
    toggleDemoMyDataEnabled.setSelected(c.getDemoMyDataEnabled());
  }

  public void save() {
    GenericDao gdao = new GenericDao(CompanyEntity.class, PersistenceManager.getEmf());
    detailCrud.saveModel(CompanyUtil.getCurrentCompany());
    CompanyEntity cp = detailCrud.getModel();
    cp.setDemoMyDataEnabled(toggleDemoMyDataEnabled.isSelected());
    gdao.updateEntity(cp);
  }
}
