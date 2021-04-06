/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 06/02/2021 (gmoralis) - Edit and delete actions
 * 06/04/2021 (gmoralis) - Fixed confirm after validation
 * 05/04/2021 (gmoralis) - Initial
 */
package gr.codebb.protoerp.items;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.lib.crud.AbstractListView;
import gr.codebb.lib.database.GenericDao;
import gr.codebb.lib.database.PersistenceManager;
import gr.codebb.lib.util.AlertDlgHelper;
import gr.codebb.lib.util.AlertHelper;
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
import javafx.scene.layout.StackPane;

public class ItemsListView extends AbstractListView implements Initializable {

  @FXML private StackPane mainStackPane;
  @FXML private Button refreshButton;
  @FXML private Button newButton;
  @FXML private Button openButton;
  @FXML private Button deleteButton;
  @FXML private CbbTableView<ItemsEntity> itemsTable;

  /** Initializes the controller class. */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void refreshAction(ActionEvent event) {
    selectWithService();
  }

  @FXML
  private void newAction(ActionEvent event) {
    FxmlUtil.LoadResult<ItemsDetailView> getDetailView =
        FxmlUtil.load("/fxml/items/ItemsDetailView.fxml");
    Alert alert =
        AlertDlgHelper.saveDialog(
            "Προσθήκη Είδους", getDetailView.getParent(), mainStackPane.getScene().getWindow());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.addEventFilter(
        ActionEvent.ACTION,
        (event1) -> {
          if (!getDetailView.getController().validateControls()) {
            event1.consume();
          } else {
            if (!(AlertHelper.SaveConfirm(
                        getDetailView.getController().getMainStackPane().getScene().getWindow())
                    .get()
                == ButtonType.OK)) {
              event1.consume();
            }
          }
        });
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().save();
        selectWithService();
      }
    }
  }

  @FXML
  protected void openAction(ActionEvent event) {
    FxmlUtil.LoadResult<ItemsDetailView> getDetailView =
        FxmlUtil.load("/fxml/items/ItemsDetailView.fxml");
    Alert alert =
        AlertDlgHelper.editDialog(
            "Άνοιγμα/Επεξεργασία Είδους",
            getDetailView.getParent(),
            mainStackPane.getScene().getWindow());
    getDetailView.getController().fillData(itemsTable.getSelectionModel().getSelectedItem());
    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.addEventFilter(
        ActionEvent.ACTION,
        (event1) -> {
          if (!getDetailView.getController().validateControls()) {
            event1.consume();
          } else {
            if (!(AlertHelper.EditConfirm(
                        getDetailView.getController().getMainStackPane().getScene().getWindow())
                    .get()
                == ButtonType.OK)) {
              event1.consume();
            }
          }
        });
    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      if (getDetailView.getController() != null) {
        getDetailView.getController().saveEdit();
        selectWithService();
      }
    }
  }

  @FXML
  private void deleteAction(ActionEvent event) {
    int row = itemsTable.getSelectionModel().getSelectedIndex();
    itemsTable.getSelectionModel().select(row);
    Optional<ButtonType> response =
        AlertHelper.DeleteConfirm(
            itemsTable.getScene().getWindow(),
            "Είστε σιγουροι ότι θέλετε να διαγράψετε το Είδος : "
                + itemsTable.getSelectionModel().getSelectedItem().getDescription());
    if (response.get() == ButtonType.OK) {
      GenericDao gdao = new GenericDao(ItemsEntity.class, PersistenceManager.getEmf());
      try {
        gdao.deleteEntity(itemsTable.getSelectionModel().getSelectedItem().getId());
      } catch (Exception e) {
        e.printStackTrace();
      }
      selectWithService();
    }
  }

  @Override
  protected CbbTableView getTableView() {
    return itemsTable;
  }

  @Override
  protected List getMainQuery() {
    return ItemsQueries.getItemsDatabase(false);
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
