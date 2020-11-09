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
package gr.codebb.lib.crud;

import gr.codebb.ctl.cbbTableView.CbbTableView;
import gr.codebb.lib.crud.annotation.ColumnProperty;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.MaskerPane;

public abstract class AbstractListView<T> {

  protected abstract CbbTableView<T> getTableView();

  protected abstract List<T> getMainQuery();

  protected abstract StackPane getMainStackPane();

  protected abstract Button getOpenButton();

  protected abstract Button getDeleteButton();

  protected abstract void openAction(ActionEvent e);

  public ObservableList<T> listData;
  private MaskerPane masker = new MaskerPane();
  EventHandler doubleMouseClick = null;
  Object owner;

  public void init(Object owner) {
    this.owner = owner;
    getTableView().setPlaceholder(new Label("Δεν υπάρχουν δεδομένα"));
    getMainStackPane().getChildren().add(masker);
    masker.setVisible(false);
    Platform.runLater(
        () -> {
          listData = FXCollections.observableArrayList();
          setTableModel();
        });
    // disables the Button when the user has selected nothing or cleared his selection and becomes
    // enabled as soon as at least one row is being selected.
    getOpenButton()
        .disableProperty()
        .bind(Bindings.isEmpty(getTableView().getSelectionModel().getSelectedItems()));
    getDeleteButton()
        .disableProperty()
        .bind(Bindings.isEmpty(getTableView().getSelectionModel().getSelectedItems()));
    doubleMouseClick =
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
              openAction(null);
            }
          }
        };
    getTableView().addEventHandler(MouseEvent.MOUSE_PRESSED, doubleMouseClick);
  }

  public void disableDoubleClickEvent() {
    getTableView().removeEventHandler(MouseEvent.MOUSE_PRESSED, doubleMouseClick);
  }

  public void selectData() {
    if (listData == null) {
      listData = FXCollections.observableArrayList(getMainQuery());
    } else {
      listData.clear();
      listData.addAll(getMainQuery());
    }
    getTableView().setData(listData);
  }

  public void selectWithService() {
    Platform.runLater(
        () -> {
          masker.setText("Φόρτωση δεδομένων\nΠαρακαλώ περιμένετε");
          masker.setVisible(true);
          Service<Integer> service =
              new Service<Integer>() {
                @Override
                protected Task<Integer> createTask() {
                  selectData();
                  return new Task<Integer>() {
                    @Override
                    protected Integer call() throws Exception {
                      Integer max = listData.size();
                      if (max > 35) {
                        max = 30;
                      }
                      updateProgress(0, max);
                      for (int k = 0; k < max; k++) {
                        updateProgress(k + 1, max);
                      }
                      return max;
                    }
                  };
                }
              };
          service.start();
          service.setOnSucceeded(
              (WorkerStateEvent event) -> {
                masker.setVisible(false);
              });
        });
  }

  public void setTableModel() {
    final Field[] declaredFields = owner.getClass().getDeclaredFields();
    for (Field f : declaredFields) {
      f.setAccessible(true);
      Annotation ano = f.getAnnotation(ColumnProperty.class);
      ColumnProperty inject = (ColumnProperty) ano;
      if (inject != null) {
        try {
          final Object findObject = f.get(owner);
          final String propName = inject.value();
          String prefWidth = inject.prefWidth();
          final Property<Object> controller = getPropertyColumn(propName, findObject);
          controller.setValue(
              new PropertyValueFactory<>(ConvertToLower(f.getName().replace("column", ""))));
          final Property<Object> controller2 = getProperty("prefWidth", findObject);
          controller2.setValue(Double.parseDouble(prefWidth));
          ColumnProperty.Align align = inject.align();
          if (align.toString().equals("right")) {
            Property<Object> controller4 = getProperty("style", findObject);
            controller4.setValue("-fx-alignment: CENTER-RIGHT;");
          } else if (align.toString().equals("center")) {
            Property<Object> controller4 = getProperty("style", findObject);
            controller4.setValue("-fx-alignment: CENTER;");
          } else if (align.toString().equals("left")) {
            Property<Object> controller4 = getProperty("style", findObject);
            controller4.setValue("-fx-alignment: CENTER-LEFT;");
          } else {
            // default or custom ovverided

          }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  private String ConvertToLower(String mit) {
    if (mit.length() == 1) {
      return mit.toUpperCase();
    }
    String firstLetter = mit.substring(0, 1).toLowerCase();
    return firstLetter + mit.substring(1);
  }

  private <T extends Property<?>> T getProperty(final String propertyName, final Object fromThis) {
    try {
      final Method propertyGetter = fromThis.getClass().getMethod(propertyName + "Property");
      final T prop = (T) propertyGetter.invoke(fromThis);
      return prop;
    } catch (NoSuchMethodException
        | SecurityException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException e) {
      throw new RuntimeException(
          String.format(
              "Not found Property %s in object of type %s, %s",
              propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
    }
  }

  private <T extends Property<?>> T getPropertyColumn(
      final String propertyName, final Object fromThis) {
    try {
      final Method propertyGetter = fromThis.getClass().getMethod(propertyName);
      final T prop = (T) propertyGetter.invoke(fromThis);
      return prop;
    } catch (NoSuchMethodException
        | SecurityException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException e) {
      throw new RuntimeException(
          String.format(
              "Not found Property %s in object of type %s, %s",
              propertyName, fromThis.getClass().getName(), "owner: " + owner.getClass().getName()));
    }
  }
}
