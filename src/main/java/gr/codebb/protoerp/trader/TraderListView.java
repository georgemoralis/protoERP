/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 25/03/2021 (gmoralis) - Initial finish
 * 23/03/2021 (gmoralis) - Startup work on adding synallasomeno
 * 06/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.trader;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.ctl.cbbTableView.columns.CbbBooleanTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbLongTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbStringTableColumn;
import gr.codebb.ctl.cbbTableView.columns.CbbTableColumn;
import gr.codebb.dlg.AlertDlg;
import gr.codebb.lib.crud.AbstractListView;
import gr.codebb.lib.crud.annotation.ColumnProperty;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.FxmlUtil;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

public class TraderListView extends AbstractListView implements Initializable {

  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button deleteButton;
  @FXML private Button openButton;
  @FXML private CbbTableView<TraderEntity> transactorTable;
  @FXML private StackPane mainStackPane;

  @ColumnProperty(prefWidth = "100.0d", align = ColumnProperty.Align.RIGHT)
  CbbTableColumn<TraderEntity, Long> columnId;

  @ColumnProperty(prefWidth = "250.0d")
  CbbTableColumn<TraderEntity, String> columnName;

  @ColumnProperty(prefWidth = "100.0d")
  CbbTableColumn<TraderEntity, Boolean> columnActive;

  @ColumnProperty(prefWidth = "150.0d", align = ColumnProperty.Align.RIGHT)
  CbbTableColumn<TraderEntity, String> columnVatNumber;

  @ColumnProperty(prefWidth = "250.0d")
  CbbTableColumn<TraderEntity, String> columnJob;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    columnId = new CbbLongTableColumn<>("Id");
    columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
    columnName = new CbbStringTableColumn<>("Επωνυμία");
    columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    columnVatNumber = new CbbStringTableColumn<>("ΑΦΜ");
    columnVatNumber.setCellValueFactory(new PropertyValueFactory<>("vatNumber"));
    columnJob = new CbbStringTableColumn<>("Επάγγελμα");
    columnJob.setCellValueFactory(new PropertyValueFactory<>("job"));
    columnActive = new CbbBooleanTableColumn<>("Ενεργός");
    columnActive.setCellValueFactory(new PropertyValueFactory<>("active"));

    transactorTable
        .getColumns()
        .addAll(columnId, columnActive, columnVatNumber, columnName, columnJob);

    init(this);
    selectWithService();
    transactorTable.setUserData("traderTable"); // for use with savesettings
  }

  @FXML
  private void refreshAction(ActionEvent event) {
    selectWithService();
  }

  @FXML
  private void newAction(ActionEvent event) {
    FxmlUtil.LoadResult<TraderDetailView> getDetailView =
        FxmlUtil.load("/fxml/trader/TraderDetailView.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Προσθήκη Συναλλασσόμενου",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.addEventFilter(
        ActionEvent.ACTION,
        (event1) -> {
          if (!getDetailView.getController().validateControls()) {
            event1.consume();
          }
        });
    getDetailView.getController().newTrader();
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().SaveNewTrader();
        selectWithService();
      }
    }
  }

  @FXML
  protected void openAction(ActionEvent event) {
    FxmlUtil.LoadResult<TraderDetailView> getDetailView =
        FxmlUtil.load("/fxml/trader/TraderDetailView.fxml");
    Alert alert =
        AlertDlgHelper.editDialog(
            "Άνοιγμα/Επεξεργασία Συναλλασσόμενου",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    getDetailView.getController().fillData(transactorTable.getSelectionModel().getSelectedItem());
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
        getDetailView.getController().SaveEditTrader();
        selectWithService();
      }
    }
  }

  @FXML
  private void deleteAction(ActionEvent event) {
    int row = transactorTable.getSelectionModel().getSelectedIndex();
    transactorTable.getSelectionModel().select(row);
    ButtonType response =
        AlertDlg.create()
            .message(
                "Είστε σιγουροι ότι θέλετε να διαγράψετε τον Συναλλασσόμενο : "
                    + transactorTable.getSelectionModel().getSelectedItem().getName())
            .title("Διαγραφή")
            .modality(Modality.APPLICATION_MODAL)
            .owner(transactorTable.getScene().getWindow())
            .showAndWaitConfirm();
    if (response == ButtonType.OK) {
      GenericDao gdao = new GenericDao(TraderEntity.class, PersistenceManager.getEmf());
      try {
        gdao.deleteEntity(transactorTable.getSelectionModel().getSelectedItem().getId());
      } catch (Exception e) {
        e.printStackTrace();
      }
      selectWithService();
    }
  }

  @Override
  protected CbbTableView getTableView() {
    return transactorTable;
  }

  @Override
  protected List getMainQuery() {
    return TraderQueries.getTradersPerCompany();
  }

  @Override
  protected StackPane getMainStackPane() {
    return mainStackPane;
  }

  @Override
  protected Button getDeleteButton() {
    return deleteButton;
  }

  @Override
  protected Button getOpenButton() {
    return openButton;
  }
}
