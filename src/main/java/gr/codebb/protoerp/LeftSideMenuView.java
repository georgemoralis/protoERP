/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
 /*
 * Changelog
 * =========
 * 06/10/2020 (georgemoralis) - Functional search and prototype menu
 * 04/10/2020 (georgemoralis) - Initial commit
 */
package gr.codebb.protoerp;

import gr.codebb.ctl.CbbDetachableTab;
import gr.codebb.ctl.CbbDetachableTabPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class LeftSideMenuView implements Initializable {

    @FXML
    private VBox mainVBox;
    @FXML
    private HBox searchBox;
    @FXML
    private TextField search;
    @FXML
    private Button clear;
    @FXML
    private SVGPath searchIcon;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox views;
    @FXML
    private Button dashboard;
    @FXML
    private TitledPane settings_title;

    @FXML
    private TitledPane partners_title;
    @FXML
    private TitledPane invoices_title;
    @FXML
    private TitledPane cash_title;
    @FXML
    private TitledPane items_title;
    @FXML
    private TitledPane reports_title;
    @FXML
    private TitledPane util_title;

    private CbbDetachableTabPane mainDetachPane;
    private FilteredList<Button> filteredList = null;

    //each title pane has a list of buttons
    private ObservableList<Button> all_items = FXCollections.observableArrayList();
    private ObservableList<Button> partners_items = FXCollections.observableArrayList();
    private ObservableList<Button> invoices_items = FXCollections.observableArrayList();
    private ObservableList<Button> cash_items = FXCollections.observableArrayList();
    private ObservableList<Button> items_items = FXCollections.observableArrayList();
    private ObservableList<Button> settings_items = FXCollections.observableArrayList();
    private ObservableList<Button> reports_items = FXCollections.observableArrayList();
    private ObservableList<Button> util_items = FXCollections.observableArrayList();

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
        HBox.setHgrow(search, Priority.ALWAYS);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        populateItems();
        filteredList = new FilteredList<>(all_items, s -> true);

        search.textProperty().addListener(obs -> {
            String filter = search.getText();
            if (filter == null || filter.length() == 0) {
                barInitial();
                searchIcon.setContent("M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
            } else {
                barFiltered(filter);
                searchIcon.setContent("M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z");
            }
        });
    }

    private void populateItems() {

        for (Node node : views.getChildren()) {
            if (node instanceof Button) {
                all_items.add((Button) node);
            }
        }

        for (Node node : ((VBox) partners_title.getContent()).getChildren()) {
            partners_items.add((Button) node);
            all_items.add((Button) node);
        }

        for (Node node : ((VBox) invoices_title.getContent()).getChildren()) {
            invoices_items.add((Button) node);
            all_items.add((Button) node);
        }

        for (Node node : ((VBox) cash_title.getContent()).getChildren()) {
            cash_items.add((Button) node);
            all_items.add((Button) node);
        }
        for (Node node : ((VBox) items_title.getContent()).getChildren()) {
            items_items.add((Button) node);
            all_items.add((Button) node);
        }
        for (Node node : ((VBox) settings_title.getContent()).getChildren()) {
            settings_items.add((Button) node);
            all_items.add((Button) node);
        }
        for (Node node : ((VBox) reports_title.getContent()).getChildren()) {
            reports_items.add((Button) node);
            all_items.add((Button) node);
        }
        for (Node node : ((VBox) util_title.getContent()).getChildren()) {
            util_items.add((Button) node);
            all_items.add((Button) node);
        }
    }

    private void barInitial() {
        filteredList.setPredicate(s -> true);
        scroll.setContent(views);
        ((VBox) partners_title.getContent()).getChildren().setAll(partners_items);
        ((VBox) invoices_title.getContent()).getChildren().setAll(invoices_items);
        ((VBox) cash_title.getContent()).getChildren().setAll(cash_items);
        ((VBox) items_title.getContent()).getChildren().setAll(items_items);
        ((VBox) settings_title.getContent()).getChildren().setAll(settings_items);
        ((VBox) reports_title.getContent()).getChildren().setAll(reports_items);
        ((VBox) util_title.getContent()).getChildren().setAll(util_items);
        views.getChildren().removeAll(dashboard);
        views.getChildren().add(dashboard);
        dashboard.setContentDisplay(ContentDisplay.LEFT);
        dashboard.setAlignment(Pos.CENTER_LEFT);
        dashboard.toBack();

    }

    private void barFiltered(String filter) {
        views.getChildren().removeAll(dashboard);
        filteredList.setPredicate(s -> s.getText().toUpperCase().contains(filter.toUpperCase()));
        scroll.setContent(filter(filteredList));
    }

    private VBox filter(ObservableList<Button> nodes) {
        VBox vBox = new VBox();
        //  vBox.getStyleClass().add("drawer-content");
        vBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        vBox.setAlignment(Pos.TOP_RIGHT);
        VBox.setVgrow(vBox, Priority.ALWAYS);
        for (Button node : nodes) {
            node.setAlignment(Pos.CENTER_RIGHT);
        }
        vBox.getChildren().setAll(nodes);
        return vBox;
    }

    @FXML
    private void clearText(MouseEvent event) {
    }

    @FXML
    private void dashBoardAction(ActionEvent event) {
    }

    @FXML
    private void settingsAction(ActionEvent event) {
    }

    public void showAsTab(AnchorPane frm, String label) {
        final CbbDetachableTab tab = new CbbDetachableTab(label);
        tab.setClosable(true);
        tab.setContent(frm);
        mainDetachPane.getTabs().add(tab);
        mainDetachPane.getSelectionModel().select(tab);

        /**
         * Workaround for TabPane memory leak
         */
        tab.setOnClosed(
                (Event t) -> {
                    tab.setContent(null);
                });
        mainDetachPane.getSelectionModel().selectLast();
    }

    @FXML
    private void partnersAction(ActionEvent event) {
    }

    @FXML
    private void invoicesAction(ActionEvent event) {
    }

    @FXML
    private void cancelledAction(ActionEvent event) {
    }

    @FXML
    private void casholdAction(ActionEvent event) {
    }

    @FXML
    private void itemsAction(ActionEvent event) {
    }

    @FXML
    private void storageAction(ActionEvent event) {
    }

    @FXML
    private void reportsAction(ActionEvent event) {
    }

    @FXML
    private void issuesAction(ActionEvent event) {
    }
}
