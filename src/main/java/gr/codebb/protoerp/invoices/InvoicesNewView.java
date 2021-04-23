/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 22/04/2020 (gmoralis) - Initial
 */
package gr.codebb.protoerp.invoices;

import gr.codebb.ctl.CbbSearchableTextField;
import gr.codebb.protoerp.tables.InvoiceTypes.InvoiceTypesEntity;
import gr.codebb.protoerp.tables.InvoiceTypes.InvoiceTypesQueries;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.MaskerPane;

public class InvoicesNewView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private TableView<InvoiceTypesEntity> selectTable;
  @FXML private TableColumn<InvoiceTypesEntity, String> codeColumn;
  @FXML private TableColumn<InvoiceTypesEntity, String> shortColumn;
  @FXML private TableColumn<InvoiceTypesEntity, String> nameColumn;
  @FXML private TableColumn<InvoiceTypesEntity, Integer> lastNumColumn;
  @FXML private TableColumn<InvoiceTypesEntity, String> plantColumn;
  @FXML private TableColumn<InvoiceTypesEntity, String> seiraColumn;
  @FXML private CbbSearchableTextField searchTextField;

  ObservableList<InvoiceTypesEntity> typesList;
  FilteredList<InvoiceTypesEntity> filteredList;
  private MaskerPane masker = new MaskerPane();

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    selectTable.setPlaceholder(new Label("Δεν υπάρχουν σειρές παραστατικών"));
    mainStackPane.getChildren().add(masker);
    masker.setVisible(true);

    codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
    codeColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
    shortColumn.setCellValueFactory(new PropertyValueFactory<>("shortName"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    seiraColumn.setCellValueFactory(new PropertyValueFactory<>("seira"));
    lastNumColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
    lastNumColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
    plantColumn.setCellValueFactory(new PropertyValueFactory<>("plantS"));
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
                    if (t.getSeira() != null
                        && t.getSeira().toLowerCase().contains(lowerCaseFilter)) {
                      return true; // Filter matches name.
                    }
                    if (t.getCode().toLowerCase().contains(lowerCaseFilter)) {
                      return true; // Filter matches code
                    }
                    if (t.getShortName().toLowerCase().contains(lowerCaseFilter)) {
                      return true; // Filter matches shortname.
                    }
                    if (Integer.toString(t.getNumber()).contains(lowerCaseFilter)) {
                      return true; // Filter matches number
                    }
                    return false;
                  });
            });
    loadService();
  }

  private void loadService() {
    masker.setVisible(true);
    masker.setText("Φόρτωση σειρών παραστατικών\nΠαρακαλώ περιμένετε");
    Task<String> service =
        new Task<String>() {
          @Override
          protected String call() throws Exception {
            selectTable.setItems(null);
            loadTable(InvoiceTypesQueries.getInvoiceTypesDatabasePerCompany(true));
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

  private void loadTable(List<InvoiceTypesEntity> b) {

    typesList = FXCollections.observableArrayList(b);
    filteredList = new FilteredList<>(typesList, p -> true);
    SortedList<InvoiceTypesEntity> sortedData = new SortedList<>(filteredList);

    sortedData.comparatorProperty().bind(selectTable.comparatorProperty());
    selectTable.setItems(sortedData);
  }

  public TableView getTableView() {
    return selectTable;
  }
}
