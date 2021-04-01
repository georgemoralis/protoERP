/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 01/04/2021 (gmoralis) - Added EditConfirm
 * 01/04/2021 (gmoralis) - Initial , added SaveConfirm
 */
package gr.codebb.lib.util;

import gr.codebb.codebblib.fxalert.FXAlert;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Window;

/** @author George Moralis wrapper class around fxalert library for use with protoerp easily */
public class AlertHelper {

  public static Optional<ButtonType> SaveConfirm(Window window) {
    Optional<ButtonType> result =
        FXAlert.confirm()
            .withButtonTypes(ButtonType.OK, ButtonType.CANCEL)
            .buttonModify(ButtonType.OK, "Ναι", "success")
            .buttonModify(ButtonType.CANCEL, "'Οχι", "danger")
            .withText("Αποθήκευση εγγραφής?")
            .withInitOwner(window)
            .withInitModality(Modality.APPLICATION_MODAL)
            .withStylesheet("/styles/bootstrap3.css")
            .withTitle("Αποθήκευση")
            .withStyle("-fx-background-color: white;")
            .showAndWait();
    return result;
  }

  public static Optional<ButtonType> EditConfirm(Window window) {
    Optional<ButtonType> result =
        FXAlert.confirm()
            .withButtonTypes(ButtonType.OK, ButtonType.CANCEL)
            .buttonModify(ButtonType.OK, "Ναι", "success")
            .buttonModify(ButtonType.CANCEL, "'Οχι", "danger")
            .withText("Μεταβολή εγγραφής?")
            .withInitOwner(window)
            .withInitModality(Modality.APPLICATION_MODAL)
            .withStylesheet("/styles/bootstrap3.css")
            .withTitle("Αποθήκευση μεταβολής")
            .withStyle("-fx-background-color: white;")
            .showAndWait();
    return result;
  }
}
