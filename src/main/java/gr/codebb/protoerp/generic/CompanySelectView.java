/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 09/03/2021 (gmoralis) - Enable validation on createNewCompany
 * 05/03/2021 (gmoralis) - Delete of company now works
 * 04/03/2021 (gmoralis) - Saving of company data
 * 26/02/2021 (gmoralis) - Initial commit
 */
package gr.codebb.protoerp.generic;

import gr.codebb.ctl.CbbSearchableTextField;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.FxmlUtil;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.settings.company.CompanyQueries;
import gr.codebb.protoerp.settings.company.CompanyView;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import org.controlsfx.control.MaskerPane;

public class CompanySelectView implements Initializable {

  @FXML private TableView<CompanyEntity> selectTable;
  @FXML private Button selectButton;
  @FXML private Button createButton;
  @FXML private Button EditButton;
  @FXML private Button DeleteButton;
  @FXML private TableColumn<CompanyEntity, Long> codeColumn;
  @FXML private TableColumn<CompanyEntity, String> vatColumn;
  @FXML private TableColumn<CompanyEntity, String> nameColumn;
  @FXML private CbbSearchableTextField searchTextField;
  @FXML private StackPane mainStackPane;

  ObservableList<CompanyEntity> companyList;
  FilteredList<CompanyEntity> filteredList;
  private MaskerPane masker = new MaskerPane();

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // disable buttons if nothing is selected
    DeleteButton.disableProperty()
        .bind(Bindings.isEmpty(selectTable.getSelectionModel().getSelectedItems()));
    EditButton.disableProperty()
        .bind(Bindings.isEmpty(selectTable.getSelectionModel().getSelectedItems()));
    selectButton
        .disableProperty()
        .bind(Bindings.isEmpty(selectTable.getSelectionModel().getSelectedItems()));
    selectTable.setPlaceholder(new Label("Δεν υπάρχουν εταιρίες"));
    mainStackPane.getChildren().add(masker);
    masker.setVisible(true);

    codeColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    vatColumn.setCellValueFactory(new PropertyValueFactory<>("vatNumber"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    searchTextField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              filteredList.setPredicate(
                  t -> {
                    if (newValue == null || newValue.isEmpty()) {
                      return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (t.getName().toLowerCase().contains(lowerCaseFilter)) {
                      return true; // Filter matches name.
                    }
                    if (t.getVatNumber().toLowerCase().contains(lowerCaseFilter)) {
                      return true; // Filter matches vat
                    }

                    if (Long.toString(t.getId()).contains(lowerCaseFilter)) {
                      return true; // Filter matches id
                    }
                    return false;
                  });
            });
    loadService();
  }

  @FXML
  private void onSelect(ActionEvent event) {}

  @FXML
  private void onCreate(ActionEvent event) {
    FxmlUtil.LoadResult<CompanyView> getDetailView =
        FxmlUtil.load("/fxml/settings/company/Company.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Προσθήκη Εταιρείας", getDetailView.getParent(), mainStackPane.getScene().getWindow());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.addEventFilter(
        ActionEvent.ACTION,
        (event1) -> {
          if (!getDetailView.getController().validateControls()) {
            event1.consume();
          }
        });
    getDetailView.getController().initNewCompany();
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().SaveNewCompany();
        loadService();
      }
    }
  }

  @FXML
  private void onEdit(ActionEvent event) 
  {
  FxmlUtil.LoadResult<CompanyView> getDetailView =
        FxmlUtil.load("/fxml/settings/company/Company.fxml");
    Alert alert =
        AlertDlgHelper.editDialog(
            "Άνοιγμα/Επεξεργασία Εταιρείας",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    getDetailView.getController().fillData(selectTable.getSelectionModel().getSelectedItem());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.addEventFilter(
        ActionEvent.ACTION,
        (event1) -> {
          if (!getDetailView.getController().validateControls()) {
            event1.consume();
          }
        });
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        /*int row = tablePlants.getSelectionModel().getSelectedIndex();
        PlantsEntity selected = tablePlants.getSelectionModel().getSelectedItem();
        PlantsEntity p = getDetailView.getController().saveEdit(plantrow.get(row));
        plantrow.remove(selected);
        plantrow.add(p);*/
      }
    }
  }

  @FXML
  private void onDelete(ActionEvent event) {
    int row = selectTable.getSelectionModel().getSelectedIndex();
    selectTable.getSelectionModel().select(row);
    ButtonType response =
        AlertDlg.create()
            .message(
                "Είστε σιγουροι ότι θέλετε να διαγράψετε την εταιρεία : \n"
                    + selectTable.getSelectionModel().getSelectedItem().getName())
            .title("Διαγραφή")
            .modality(Modality.APPLICATION_MODAL)
            .owner(selectTable.getScene().getWindow())
            .showAndWaitConfirm();
    if (response == ButtonType.OK) {
      GenericDao gdao = new GenericDao(CompanyEntity.class, PersistenceManager.getEmf());
      try {
        gdao.deleteEntity(selectTable.getSelectionModel().getSelectedItem().getId());
      } catch (Exception e) {
        e.printStackTrace();
      }
      loadService();
    }
  }

  private void loadService() {
    masker.setVisible(true);
    masker.setText("Φόρτωση εταιρειών\nΠαρακαλώ περιμένετε");
    Task<String> service =
        new Task<String>() {
          @Override
          protected String call() throws Exception {
            selectTable.setItems(null);
            loadTable(CompanyQueries.getCompanies());
            return null;
          }
        };
    service.setOnSucceeded(
        new EventHandler<WorkerStateEvent>() {
          @Override
          public void handle(WorkerStateEvent t) {
            masker.setVisible(false);
          }
        });
    Thread thread = new Thread(service);
    thread.start();
  }

  private void loadTable(List<CompanyEntity> b) {

    companyList = FXCollections.observableArrayList(b);
    filteredList = new FilteredList<>(companyList, p -> true);
    SortedList<CompanyEntity> sortedData = new SortedList<>(filteredList);

    sortedData.comparatorProperty().bind(selectTable.comparatorProperty());
    selectTable.setItems(sortedData);
  }
}
