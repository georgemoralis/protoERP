/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * changelog
 * =========
 * 12/06/2019 (gmoralis) - Direct copy from ERP
 */
package gr.codebb.protoerp.util.validation;

import javafx.scene.control.Control;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;

/** @author snow */
public class VatValidator implements Validator<String> {

  @Override
  public ValidationResult apply(Control t, String vat) {
    if (vat.length() != 9) {
      return ValidationResult.fromError(t, "Τα ψηφία του ΑΦΜ πρέπει να είναι 9 αριθμοί");
    }
    int digit1 = Character.getNumericValue(vat.charAt(0));
    int digit2 = Character.getNumericValue(vat.charAt(1));
    int digit3 = Character.getNumericValue(vat.charAt(2));
    int digit4 = Character.getNumericValue(vat.charAt(3));
    int digit5 = Character.getNumericValue(vat.charAt(4));
    int digit6 = Character.getNumericValue(vat.charAt(5));
    int digit7 = Character.getNumericValue(vat.charAt(6));
    int digit8 = Character.getNumericValue(vat.charAt(7));
    int digit9 = Character.getNumericValue(vat.charAt(8));
    int total =
        digit1 * 256
            + digit2 * 128
            + digit3 * 64
            + digit4 * 32
            + digit5 * 16
            + digit6 * 8
            + digit7 * 4
            + digit8 * 2;
    int ipolipo = total % 11;
    if ((ipolipo == digit9) || (ipolipo == 10 && digit9 == 0)) {
      return null;
    } else {
      return ValidationResult.fromError(t, "Το ΑΦΜ που εισάγατε δεν είναι σωστό");
    }
  }
}
