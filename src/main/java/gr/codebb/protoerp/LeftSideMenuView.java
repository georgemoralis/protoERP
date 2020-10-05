/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 04/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp;

import gr.codebb.ctl.CbbDetachableTab;
import gr.codebb.ctl.CbbDetachableTabPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class LeftSideMenuView implements Initializable {

  @FXML private VBox mainVBox;
  @FXML private HBox searchBox;
  @FXML private TextField search;
  @FXML private Button clear;
  @FXML private SVGPath searchIcon;
  @FXML private ScrollPane scroll;
  @FXML private VBox views;
  @FXML private Button dashboard;
  @FXML private TitledPane settings_title;
  private CbbDetachableTabPane mainDetachPane;

  public void setMainDetachPane(CbbDetachableTabPane mainDetachPane) {
    this.mainDetachPane = mainDetachPane;
  }
  /**
   * Initializes the controller class.
   *
   * @param url
   * @param rb
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
  }

  @FXML
  private void clearText(MouseEvent event) {}

  @FXML
  private void dashBoardAction(ActionEvent event) {}

  @FXML
  private void settingsAction(ActionEvent event) {}

  public void showAsTab(AnchorPane frm, String label) {
    final CbbDetachableTab tab = new CbbDetachableTab(label);
    tab.setClosable(true);
    tab.setContent(frm);
    mainDetachPane.getTabs().add(tab);
    mainDetachPane.getSelectionModel().select(tab);

    /** Workaround for TabPane memory leak */
    tab.setOnClosed(
        (Event t) -> {
          tab.setContent(null);
        });
    mainDetachPane.getSelectionModel().selectLast();
  }
}
