/*
 * Copyright (c) 2014ff Thomas Feuster
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/*
 * Changelog
 * =========
 * 19/02/2020 (georgemoralis) - Adjustments for use with our custom tablecolumns. WIP work on save/restore table Settings
 * 19/02/2020 (georgemoralis) - Initial import
 */
package gr.codebb.lib.util;

import gr.codebb.ctl.cbbTableView.columns.CbbTableColumn;
import gr.codebb.protoerp.settings.tableSettings.TableSaveSettings;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.input.MouseEvent;

/**
 * Customize the table menu for tableviews and treetableviews.
 *
 * <p>A custom contextmenu is created with add. options to show/hide all columns. By setting the
 * column's user data to NO_HIDE_COLUMN it is forced to always be visible.
 *
 * <p>Based on the example from https://gist.github.com/Roland09/d92829cdf5e5fee6fee9
 *
 * @author thomas
 */
public class TableMenuUtils {

  // option to flag columns that can't be hidden
  // set as userdata
  public static final String NO_HIDE_COLUMN = "noHideColumn";
  public static final String NO_LIST_COLUMN = "noListColumn";

  /**
   * Make table menu button visible and replace the context menu with a custom context menu via
   * reflection. The preferred height is modified so that an empty header row remains visible. This
   * is needed in case you remove all columns, so that the menu button won't disappear with the row
   * header. IMPORTANT: Modification is only possible AFTER the table has been made visible,
   * otherwise you'd get a NullPointerException
   *
   * @param tableView
   */
  public static void addCustomTableViewMenu(final TableView tableView) {
    if (tableView.getSkin() == null) {
      return;
    }

    // enable table menu
    tableView.setTableMenuButtonVisible(true);

    // replace internal mouse listener with custom listener
    setCustomContextMenu(
        tableView,
        ObjectsHelper.uncheckedCast(tableView.getSkin()),
        ObjectsHelper.uncheckedCast(tableView.getColumns()));
  }

  private static void setCustomContextMenu(
      final TableView tableView,
      final SkinBase<? extends Control> tableSkin,
      final ObservableList<CbbTableColumn<?, ?>> columns) {
    // get all children of the skin
    final ObservableList<Node> children = tableSkin.getChildren();

    // find the TableHeaderRow child
    for (int i = 0; i < children.size(); i++) {
      final Node node = children.get(i);

      if (node instanceof TableHeaderRow) {
        final TableHeaderRow tableHeaderRow = (TableHeaderRow) node;

        // setting the preferred height for the table header row
        // if the preferred height isn't set, then the table header would disappear if there are no
        // visible columns
        // and with it the table menu button
        // by setting the preferred height the header will always be visible
        // note: this may need adjustments in case you have different heights in columns (eg when
        // you use grouping)
        final double defaultHeight = tableHeaderRow.getHeight();
        tableHeaderRow.setPrefHeight(defaultHeight);

        for (Node child : tableHeaderRow.getChildren()) {
          // child identified as cornerRegion in TableHeaderRow.java
          if (child.getStyleClass().contains("show-hide-columns-button")) {
            // get the context menu
            final ContextMenu columnPopupMenu = createContextMenu(columns, tableView);

            // replace mouse listener
            child.setOnMousePressed(
                me -> {
                  if (!columnPopupMenu.isShowing()) {
                    // show a popupMenu which lists all columns
                    columnPopupMenu.show(child, Side.BOTTOM, 0, 0);
                  } else {
                    columnPopupMenu.hide();
                  }
                  me.consume();
                });
          }
        }
      }
    }
  }

  /**
   * Create a menu with custom items. The important thing is that the menu remains open while you
   * click on the menu items.
   *
   * @param cm
   * @param table
   */
  private static ContextMenu createContextMenu(
      final ObservableList<CbbTableColumn<?, ?>> columns, final TableView tableView) {
    final ContextMenu cm = new ContextMenu();

    // create new context menu
    CustomMenuItem cmi;

    if (tableView.getUserData() != null) {
      Label saveSettings = new Label("Αποθήκευση Ρυθμίσεων");
      saveSettings.addEventHandler(
          MouseEvent.MOUSE_CLICKED,
          (MouseEvent event) -> {
            TableViewPreferences.saveTableViewPreferences(
                tableView, (String) tableView.getUserData(), TableSaveSettings.getInstance());
          });

      cmi = new CustomMenuItem(saveSettings);
      cmi.setHideOnClick(false);
      cm.getItems().add(cmi);
      // separator
      cm.getItems().add(new SeparatorMenuItem());
    }
    // select all item
    Label showAll = new Label("Προβολή όλων");
    showAll.addEventHandler(
        MouseEvent.MOUSE_CLICKED,
        (MouseEvent event) -> {
          setVisible(columns, true);
        });

    cmi = new CustomMenuItem(showAll);
    cmi.setHideOnClick(false);
    cm.getItems().add(cmi);

    // deselect all item
    final Label hideAll = new Label("Απόκρυψη όλων");
    hideAll.addEventHandler(
        MouseEvent.MOUSE_CLICKED,
        (MouseEvent event) -> {
          setVisible(columns, false);
        });

    cmi = new CustomMenuItem(hideAll);
    cmi.setHideOnClick(false);
    cm.getItems().add(cmi);

    // separator
    cm.getItems().add(new SeparatorMenuItem());

    // menu item for each of the available columns
    for (CbbTableColumn<?, ?> column : columns) {
      // TFE, 20201205: allow to exclude columns from the list
      if (!NO_LIST_COLUMN.equals((String) column.getUserData())) {
        final CheckBox cb = new CheckBox(column.getColumnName());
        cb.selectedProperty().bindBidirectional(column.visibleProperty());

        cmi = new CustomMenuItem(cb);
        cmi.setHideOnClick(false);

        cm.getItems().add(cmi);

        // some columns just want to stay visible
        if (NO_HIDE_COLUMN.equals((String) column.getUserData())) {
          column.setVisible(true);
          cmi.setDisable(true);
        }
      }
    }

    return cm;
  }

  private static void setVisible(
      final ObservableList<CbbTableColumn<?, ?>> columns, final boolean value) {
    for (TableColumnBase<?, ?> column : columns) {
      if (!NO_HIDE_COLUMN.equals((String) column.getUserData())) {
        column.setVisible(value);
      }
    }
  }
}
