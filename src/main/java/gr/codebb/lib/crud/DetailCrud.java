/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/**
 * changelog
 * =========
 * 25/12/2019 (gmoralis) -Added support for TextFieldProperty.Type.INT load/save
 */
package gr.codebb.lib.crud;


import gr.codebb.lib.crud.annotation.CheckBoxProperty;
import gr.codebb.lib.crud.annotation.TextAreaProperty;
import gr.codebb.lib.crud.annotation.TextFieldProperty;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.beans.property.Property;

public class DetailCrud<T> {

    private T modelObject;
    private Object owner;

    public DetailCrud(Object owner) {
        super();
        if (owner == null) {
            throw new RuntimeException("Owner Is Null");
        }
        this.owner = owner;
    }

    public T getModel() {
        return modelObject;
    }

    public void clear() {
        final Field[] declaredFields = owner.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            Annotation ano = f.getAnnotation(TextFieldProperty.class);
            if (ano instanceof TextFieldProperty) {
                TextFieldProperty inject = (TextFieldProperty) ano;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("text")) {
                        Property<String> controller = getProperty(propName, findObject);
                        controller.setValue("");
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            Annotation anocheck = f.getAnnotation(CheckBoxProperty.class);
            if (anocheck instanceof CheckBoxProperty) {
                CheckBoxProperty inject = (CheckBoxProperty) anocheck;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("checkBox")) {
                        final Property<Boolean> controller = getProperty("selected", findObject);
                        controller.setValue(false);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            Annotation anotextArea = f.getAnnotation(TextAreaProperty.class);
            if (anotextArea instanceof TextAreaProperty) {
                TextAreaProperty inject = (TextAreaProperty) anotextArea;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("text")) {
                        Property<String> controller = getProperty(propName, findObject);
                        controller.setValue("");
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void toggleControlState(boolean active) {
        final Field[] declaredFields = owner.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            Annotation ano = f.getAnnotation(TextFieldProperty.class);
            if (ano instanceof TextFieldProperty) {
                TextFieldProperty inject = (TextFieldProperty) ano;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("text")) {
                        Property<Boolean> controller = getProperty("editable", findObject);
                        controller.setValue(active);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            Annotation anocheck = f.getAnnotation(CheckBoxProperty.class);
            if (anocheck instanceof CheckBoxProperty) {
                CheckBoxProperty inject = (CheckBoxProperty) anocheck;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("checkBox")) {
                        final Property<Boolean> controller = getProperty("disable", findObject);
                        controller.setValue(!active);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            Annotation anotextArea = f.getAnnotation(TextAreaProperty.class);
            if (anotextArea instanceof TextAreaProperty) {
                TextAreaProperty inject = (TextAreaProperty) anotextArea;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("text")) {
                        Property<Boolean> controller = getProperty("editable", findObject);
                        controller.setValue(active);
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void loadModel(T bean) {
        modelObject = (T) bean;
        final Field[] declaredFields = owner.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            Annotation ano = f.getAnnotation(TextFieldProperty.class);
            if (ano instanceof TextFieldProperty) {
                TextFieldProperty inject = (TextFieldProperty) ano;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    TextFieldProperty.Type type = inject.type();
                    if (type.toString().equals("string") && propName.equals("text")) {
                        final Property<String> controller = getProperty(propName, findObject);
                        controller.setValue(getStringNonPropertyModel(f.getName(), modelObject));
                    }
                    if (type.toString().equals("int") && propName.equals("text")) {
                        final Property<String> controller = getProperty(propName, findObject);
                        controller.setValue(getIntegerNonPropertyModel(f.getName(), modelObject).toString());
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            Annotation anocheck = f.getAnnotation(CheckBoxProperty.class);
            if (anocheck instanceof CheckBoxProperty) {
                CheckBoxProperty inject = (CheckBoxProperty) anocheck;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("checkBox")) {
                        final Property<Boolean> controller = getProperty("selected", findObject);
                        controller.setValue(getBooleanNonPropertyModel(f.getName(), modelObject));
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            Annotation anotextArea = f.getAnnotation(TextAreaProperty.class);
            if (anotextArea instanceof TextAreaProperty) {
                TextAreaProperty inject = (TextAreaProperty) anotextArea;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("text")) {
                        final Property<String> controller = getProperty(propName, findObject);
                        controller.setValue(getStringNonPropertyModel(f.getName().replace("textArea", ""), modelObject));
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void saveModel(T bean) {
        modelObject = (T) bean;
        final Field[] declaredFields = owner.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            Annotation ano = f.getAnnotation(TextFieldProperty.class);
            if (ano instanceof TextFieldProperty) {
                TextFieldProperty inject = (TextFieldProperty) ano;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    TextFieldProperty.Type type = inject.type();
                    if (type.toString().equals("string") && propName.equals("text")) {
                        final Property<String> controller = getProperty(propName, findObject);
                        setStringNonPropertyModel(f.getName(), modelObject, controller.getValue());
                    }
                    if (type.toString().equals("int") && propName.equals("text")) {
                        final Property<String> controller = getProperty(propName, findObject);
                        setIntegerNonPropertyModel(f.getName(), modelObject, Integer.valueOf(controller.getValue()));
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            Annotation anocheck = f.getAnnotation(CheckBoxProperty.class);
            if (anocheck instanceof CheckBoxProperty) {
                CheckBoxProperty inject = (CheckBoxProperty) anocheck;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("checkBox")) {
                        final Property<Boolean> controller = getProperty("selected", findObject);
                        setBooleanPropertyModel(f.getName(), modelObject, controller.getValue());
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
            Annotation anotextArea = f.getAnnotation(TextAreaProperty.class);
            if (anotextArea instanceof TextAreaProperty) {
                TextAreaProperty inject = (TextAreaProperty) anotextArea;
                try {
                    final Object findObject = f.get(owner);
                    final String propName = inject.value();
                    if (propName.equals("text")) {
                        final Property<String> controller = getProperty(propName, findObject);
                        setStringNonPropertyModel(f.getName().replace("textArea", ""), modelObject, controller.getValue());
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private <T extends Property<?>> T getProperty(final String propertyName, final Object fromThis) {
        try {
            final Method propertyGetter = fromThis.getClass().getMethod(propertyName + "Property");
            final T prop = (T) propertyGetter.invoke(fromThis);
            return prop;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(String.format("Not found Property %s in object of type %s, %s", propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
        }
    }

    private <T extends String> T getStringNonPropertyModel(String propertyName, Object fromThis) {
        try {
            Method propertyGetter = fromThis.getClass().getMethod("get" + propertyName.replace("text", ""));
            return (T) propertyGetter.invoke(fromThis);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(String.format("Not found Property %s in object of type %s, %s", propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
        }
    }
    private <T extends Integer> T getIntegerNonPropertyModel(String propertyName, Object fromThis) {
        try {
            Method propertyGetter = fromThis.getClass().getMethod("get" + propertyName.replace("text", ""));
            return (T) propertyGetter.invoke(fromThis);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(String.format("Not found Property %s in object of type %s, %s", propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
        }
    }

    private <T extends Boolean> T getBooleanNonPropertyModel(String propertyName, Object fromThis) {
        try {
            Method propertyGetter = fromThis.getClass().getMethod("get" + propertyName.replace("checkBox", ""));
            return (T) propertyGetter.invoke(fromThis);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(String.format("Not found Property %s in object of type %s, %s", propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
        }
    }

    private void setStringNonPropertyModel(String propertyName, Object fromThis, String param) {
        try {
            Method propertyGetter = fromThis.getClass().getMethod("set" + propertyName.replace("text", ""), String.class);
            propertyGetter.invoke(fromThis, param);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(String.format("Not found Property %s in object of type %s, %s", propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
        }
    }
    private void setIntegerNonPropertyModel(String propertyName, Object fromThis, Integer param) {
        try {
            Method propertyGetter = fromThis.getClass().getMethod("set" + propertyName.replace("text", ""), Integer.class);
            propertyGetter.invoke(fromThis, param);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(String.format("Not found Property %s in object of type %s, %s", propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
        }
    }

    private void setBooleanPropertyModel(String propertyName, Object fromThis, Boolean param) {
        try {
            Method propertyGetter = fromThis.getClass().getMethod("set" + propertyName.replace("checkBox", ""), Boolean.class);
            propertyGetter.invoke(fromThis, param);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(String.format("Not found Property %s in object of type %s, %s", propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
        }
    }
}
