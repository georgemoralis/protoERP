/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 06/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.userManagement;

import gr.codebb.lib.util.TreeCategoryModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class UserManagementMainView implements Initializable {

  @FXML private TreeView<TreeCategoryModel> CategoryTreeView;
  @FXML private BorderPane UserManagementBorderPane;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    TreeCategoryModel root = new TreeCategoryModel("root", null, null);
    TreeCategoryModel generic = new TreeCategoryModel("Ρυθμίσεις χρηστών", null, null);
    TreeCategoryModel users =
        new TreeCategoryModel("Χρήστες", UsersView.class, "/fxml/userManagement/Users.fxml");
    TreeCategoryModel roles =
        new TreeCategoryModel(
            "Ρόλοι & δικαιώματα", RolesView.class, "/fxml/userManagement/Roles.fxml");

    TreeItem<TreeCategoryModel> root_item = new TreeItem<>(root);

    TreeItem<TreeCategoryModel> gen_item = new TreeItem<>(generic);
    TreeItem<TreeCategoryModel> users_item = new TreeItem<>(users);
    TreeItem<TreeCategoryModel> roles_item = new TreeItem<>(roles);
    gen_item.getChildren().add(users_item);
    gen_item.getChildren().add(roles_item);

    root_item.getChildren().addAll(gen_item);
    CategoryTreeView.setRoot(root_item);
    CategoryTreeView.setShowRoot(false);

    CategoryTreeView.getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<TreeItem<TreeCategoryModel>>() {
              @Override
              public void changed(
                  ObservableValue<? extends TreeItem<TreeCategoryModel>> observable,
                  TreeItem<TreeCategoryModel> oldValue,
                  TreeItem<TreeCategoryModel> newValue) {
                if (newValue.getValue().getFxmlPath() != null) {
                  if (!UserManagementBorderPane.getChildren().isEmpty()) {
                    UserManagementBorderPane.getChildren().remove(0);
                  }
                  UserManagementBorderPane.setCenter(newValue.getValue().loadFxml());
                }
              }
            });
  }
}
