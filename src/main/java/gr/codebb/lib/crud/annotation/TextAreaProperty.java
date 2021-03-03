/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
package gr.codebb.lib.crud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author snow
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TextAreaProperty {

    /**
     *
     *
     * @return value default is text
     */

    public String value() default "text";
}
