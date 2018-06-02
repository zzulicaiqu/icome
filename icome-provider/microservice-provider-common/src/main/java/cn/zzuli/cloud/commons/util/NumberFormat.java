package cn.zzuli.cloud.commons.util;

import java.text.DecimalFormat;

/**
 * 数字类型格式化类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class NumberFormat {

    private static final String ONE = "#.0";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(ONE);


    public static double parseOneDecimal(double value){

        try {
            return Double.parseDouble(DECIMAL_FORMAT.format(value));
        } catch (NumberFormatException ex) {
            return 0.0d;
        }
    }
}
