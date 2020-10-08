/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 08/10/2020 (georgemoralis) - Added from prototype
 */
package gr.codebb.lib.database;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * Extends MySQL5Dialect and sets the default charset to be UTF-8
 *
 * @author George Moralis (shadow)
 * @since September 24 , 2015 modified September 07 ,2017
 */
public class CustomMysqlDialect extends MySQL5Dialect {

  @Override
  public String getTableTypeString() {
    return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
  }
}
