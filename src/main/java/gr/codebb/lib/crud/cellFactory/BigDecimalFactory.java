/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 06/05/2021 (georgemoralis) - Added from prototype
 */
package gr.codebb.lib.crud.cellFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/** @author shadow */
public class BigDecimalFactory<T> {

  DecimalFormat df;

  public BigDecimalFactory(DecimalFormat df) {
    this.df = df;
  }

  public Callback<TableColumn<T, BigDecimal>, TableCell<T, BigDecimal>> cellFactory =
      (final TableColumn<T, BigDecimal> param) -> {
        final TableCell<T, BigDecimal> cell =
            new TableCell<T, BigDecimal>() {
              @Override
              public void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (isEmpty() || item == null) {
                  setGraphic(null);
                  setText(null);
                } else {
                  setGraphic(null);
                  setText(df.format(item));
                }
              }
            };
        cell.setAlignment(Pos.CENTER_RIGHT);
        return cell;
      };
}
