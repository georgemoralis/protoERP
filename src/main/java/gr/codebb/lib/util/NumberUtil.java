/*
 * copyright 2013-2021
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 05/04/2021 (gmoralis) - Copied from ERP
 */
package gr.codebb.lib.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {

  public static BigDecimal apoforologisi(BigDecimal input, BigDecimal vat_rate, int scale) {
    input = input.multiply(new BigDecimal(100));
    input = input.divide(new BigDecimal(100).add(vat_rate), scale, RoundingMode.HALF_UP);
    return input;
  }

  public static BigDecimal forologisi(BigDecimal input, BigDecimal vat_rate, int scale) {
    BigDecimal tmp = input;
    tmp = tmp.multiply(vat_rate);
    tmp = tmp.divide(new BigDecimal(100), scale, RoundingMode.HALF_UP);
    input = input.add(tmp);
    return input;
  }

  public static BigDecimal percent(BigDecimal input, BigDecimal percent, int scale) {
    BigDecimal tmp = input;
    tmp = tmp.multiply(percent);
    tmp = tmp.divide(new BigDecimal(100), scale, RoundingMode.HALF_UP);
    return tmp;
  }

  public static BigDecimal multiply(BigDecimal input1, BigDecimal input2, int scale) {
    BigDecimal tmp = input1;
    tmp = tmp.multiply(input2);
    tmp = round(tmp, scale);
    return tmp;
  }

  public static BigDecimal add(BigDecimal input1, BigDecimal input2, int scale) {
    BigDecimal tmp = input1;
    tmp = tmp.add(input2);
    tmp = round(tmp, scale);
    return tmp;
  }

  public static BigDecimal substract(BigDecimal input1, BigDecimal input2, int scale) {
    BigDecimal tmp = input1;
    tmp = tmp.subtract(input2);
    tmp = round(tmp, scale);
    return tmp;
  }

  public static BigDecimal round(BigDecimal value, int numberOfDigitsAfterDecimalPoint) {
    value = value.setScale(numberOfDigitsAfterDecimalPoint, BigDecimal.ROUND_HALF_UP);
    return value;
  }
}
