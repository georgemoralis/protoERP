/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.protoerp.generic;

import gr.codebb.ctl.CbbSearchableTextField;
import gr.codebb.protoerp.settings.company.CompanyEntity;
import gr.codebb.protoerp.settings.company.CompanyQueries;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.MaskerPane;

public class CompanySelectView implements Initializable {

    @FXML
    private TableView<CompanyEntity> selectTable;
    @FXML
    private Button selectButton;
    @FXML
    private Button createButton;
    @FXML
    private Button EditButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private TableColumn<CompanyEntity, Long> codeColumn;
    @FXML
    private TableColumn<CompanyEntity, String> vatColumn;
    @FXML
    private TableColumn<CompanyEntity, String> nameColumn;
    @FXML
    private CbbSearchableTextField searchTextField;
    @FXML
    private StackPane mainStackPane;

    ObservableList<CompanyEntity> companyList;
    FilteredList<CompanyEntity> filteredList;
    private MaskerPane masker = new MaskerPane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    private void onSelect(ActionEvent event) {
    }

    @FXML
    private void onCreate(ActionEvent event) {
    }

    @FXML
    private void onEdit(ActionEvent event) {
    }

    @FXML
    private void onDelete(ActionEvent event) {
    }

    private void loadService() {
        masker.setVisible(true);
        masker.setText("Φόρτωση εταιριών\nΠαρακαλώ περιμένετε");
        Task<String> service
                = new Task<String>() {
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
