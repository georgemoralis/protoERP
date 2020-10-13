/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 13/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.protoerp.settings;

import gr.codebb.protoerp.settings.appSettings.appSettingsView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class SettingsMainView implements Initializable {

  @FXML private TreeView<TreeCategoryModel> CategoryTreeView;
  @FXML private BorderPane SettingsBorderPane;

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    TreeCategoryModel root = new TreeCategoryModel("root", null, null);
    TreeCategoryModel generic = new TreeCategoryModel("Γενικές ρυθμίσεις", null, null);
    TreeCategoryModel genApp =
        new TreeCategoryModel(
            "Ρυθμίσεις εφαρμογής",
            appSettingsView.class,
            "/fxml/settings/appSettings/AppSettings.fxml");

    TreeItem<TreeCategoryModel> root_item = new TreeItem<>(root);

    TreeItem<TreeCategoryModel> gen_item = new TreeItem<>(generic);
    TreeItem<TreeCategoryModel> gen_app_item = new TreeItem<>(genApp);
    gen_item.getChildren().add(gen_app_item);

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
                  if (!SettingsBorderPane.getChildren().isEmpty()) {
                    SettingsBorderPane.getChildren().remove(0);
                  }
                  SettingsBorderPane.setCenter(newValue.getValue().loadFxml());
                }
              }
            });
  }
}
