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

import java.util.ArrayList;
import java.util.Collection;
import org.controlsfx.control.decoration.Decoration;
import org.controlsfx.control.decoration.StyleClassDecoration;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

public class CustomValidationDecoration extends GraphicValidationDecoration {

  @Override
  protected Collection<Decoration> createValidationDecorations(ValidationMessage message) {
    Collection<Decoration> decorations = super.createValidationDecorations(message);
    ArrayList<Decoration> myDecorations = new ArrayList<>(decorations);
    myDecorations.add(new StyleClassDecoration("error-text-field"));
    return myDecorations;
  }
}
