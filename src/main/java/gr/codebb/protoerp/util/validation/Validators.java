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

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.ValidationDecoration;

/** @author shadow */
public class Validators {

  static String LETTER_REGEX = "^[\\p{L}\\-'.()/& ]+";
  static String NUMBER_REGEX = "[0-9 -]+";
  static String EMAIL_REGEX =
      "[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+";

  private static Validator notEmptyValidator;
  private static Validator onlyLettersValidator;
  private static Validator onlyNumbersValidator;
  private static Validator onlyLettersValidatorWarning;
  private static Validator onlyNumbersValidatorWarning;
  private static Validator emailValidator;
  private static Validator emailValidatorWarning;

  public static void createValidators() {
    notEmptyValidator = Validator.createEmptyValidator("Το πεδίο δεν μπορεί να είναι κενό");
    onlyLettersValidator =
        Validator.createRegexValidator(
            "Το πεδίο μπορεί να περιέχει μόνο χαρακτήρες", LETTER_REGEX, Severity.ERROR);
    onlyNumbersValidator =
        Validator.createRegexValidator(
            "Το πεδίο μπορεί να περιέχει μόνο αριθμούς", NUMBER_REGEX, Severity.ERROR);
    onlyLettersValidatorWarning =
        Validator.createRegexValidator(
            "Το πεδίο μπορεί να περιέχει μόνο χαρακτήρες", LETTER_REGEX, Severity.WARNING);
    onlyNumbersValidatorWarning =
        Validator.createRegexValidator(
            "Το πεδίο μπορεί να περιέχει μόνο αριθμούς", NUMBER_REGEX, Severity.WARNING);
    emailValidator =
        Validator.createRegexValidator("Το e-mail δεν είναι σωστό", EMAIL_REGEX, Severity.ERROR);
    emailValidatorWarning =
        Validator.createRegexValidator("Το e-mail δεν είναι σωστό", EMAIL_REGEX, Severity.WARNING);
  }

  public static Validator notEmptyValidator() {
    return notEmptyValidator;
  }

  public static Validator emailValidator(Severity state) {
    if (Severity.ERROR.compareTo(state) == 0) {
      return emailValidator;
    } else {
      return emailValidatorWarning;
    }
  }

  public static Validator onlyLettersValidator(Severity state) {
    if (Severity.ERROR.compareTo(state) == 0) {
      return onlyLettersValidator;
    } else {
      return onlyLettersValidatorWarning;
    }
  }

  public static Validator onlyNumbersValidator(Severity state) {
    if (Severity.ERROR.compareTo(state) == 0) {
      return onlyNumbersValidator;
    } else {
      return onlyNumbersValidatorWarning;
    }
  }

  public static Validator combine(Validator... validators) {
    return Validator.combine(validators);
  }

  public static void showValidationResult(ValidationSupport support) {
    ValidationDecoration decoration = support.getValidationDecorator();
    support.getValidationResult().getErrors().stream()
        .forEach(decoration::applyValidationDecoration);
    support.getValidationResult().getWarnings().stream()
        .forEach(decoration::applyValidationDecoration);
  }
}
