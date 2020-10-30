/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 30/10/2020 (georgemoralis) - Added from prototype
 */
package gr.codebb.lib.crud.services;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

public class ComboboxService<T> extends Service {

  List<T> results;
  ComboBox combobox;

  public ComboboxService(List<T> results, ComboBox combobox) {
    this.results = results;
    this.combobox = combobox;
  }

  @Override
  protected Task createTask() {
    return new Task<ObservableList<T>>() {
      @Override
      protected ObservableList<T> call() throws Exception {
        ObservableList<T> listTask = FXCollections.observableArrayList();
        if (listTask == null) {
          listTask = FXCollections.observableArrayList(results);
        } else {
          listTask.clear();
          listTask.addAll(results);
        }
        combobox.setItems(listTask);
        return listTask;
      }
    };
  }
}
