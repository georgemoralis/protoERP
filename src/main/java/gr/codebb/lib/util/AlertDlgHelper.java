/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 21/04/2021 (gmoralis) - Added selectDialog
 * 12/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.lib.util;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Window;

public class AlertDlgHelper {

  public static Alert saveDialog(String title, Parent parent, Window owner) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initOwner(owner);
    DialogPane d = new DialogPane();
    d.setStyle("-fx-background-color: white;");

    d.setContent(parent);
    alert.setDialogPane(d);
    alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
    alert
        .getDialogPane()
        .getStylesheets()
        .add(AlertDlgHelper.class.getResource("/styles/bootstrap3.css").toExternalForm());

    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.setText("Αποθήκευση");
    okbutton.getStyleClass().add("success");
    Button cancelbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
    cancelbutton.setText("Ακύρωση");
    cancelbutton.getStyleClass().add("danger");
    return alert;
  }

  public static Alert selectDialog(String title, Parent parent, Window owner) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initOwner(owner);
    DialogPane d = new DialogPane();
    d.setStyle("-fx-background-color: white;");

    d.setContent(parent);
    alert.setDialogPane(d);
    alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
    alert
        .getDialogPane()
        .getStylesheets()
        .add(AlertDlgHelper.class.getResource("/styles/bootstrap3.css").toExternalForm());

    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.setText("Επιλογή");
    okbutton.getStyleClass().add("success");
    Button cancelbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
    cancelbutton.setText("Ακύρωση");
    cancelbutton.getStyleClass().add("danger");
    return alert;
  }

  public static Alert editDialog(String title, Parent parent, Window owner) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initOwner(owner);
    DialogPane d = new DialogPane();
    d.setStyle("-fx-background-color: white;");

    d.setContent(parent);
    alert.setDialogPane(d);
    alert.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
    alert
        .getDialogPane()
        .getStylesheets()
        .add(AlertDlgHelper.class.getResource("/styles/bootstrap3.css").toExternalForm());

    Button okbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
    okbutton.setText("Μεταβολή");
    okbutton.getStyleClass().add("success");
    Button cancelbutton = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
    cancelbutton.setText("Ακύρωση");
    cancelbutton.getStyleClass().add("danger");
    return alert;
  }
}
