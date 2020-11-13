/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 13/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.lib.util;

import gr.codebb.dlg.AlertDlg;
import javafx.stage.Modality;
import javafx.stage.Window;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class AuthUtil {

  public static boolean isPermitted(String permission, String message, Window owner) {
    Subject currentUser = SecurityUtils.getSubject();
    if (!currentUser.isPermitted(permission)) {
      AlertDlg.create()
          .type(AlertDlg.Type.ERROR)
          .message(message)
          .title("Πρόβλημα")
          .owner(owner)
          .modality(Modality.APPLICATION_MODAL)
          .showAndWait();
      return false;
    }
    return true;
  }
}
