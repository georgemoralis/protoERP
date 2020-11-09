/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 09/11/2020 (georgemoralis) - Initial
 */
package gr.codebb.lib.crud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ColumnProperty {

  public enum Align {
    NONE,
    LEFT {
      @Override
      public String toString() {
        return "left";
      }
    },
    RIGHT {

      @Override
      public String toString() {
        return "right";
      }
    },
    CENTER {

      @Override
      public String toString() {
        return "center";
      }
    }
  }

  Align align() default Align.NONE;

  public String value() default "cellValueFactoryProperty";

  public String prefWidth() default "50";
}
