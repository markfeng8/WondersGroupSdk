package com.wondersgroup.android.sdk.utils;

import android.annotation.SuppressLint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by x-sir on 2018/9/9 :)
 * Function:
 */
public class NumberUtil {

    /**
     * 保留两位小数，四舍五入的一个老土的方法
     *
     * @param d parameter
     * @return result
     */
    public static double formatDouble1(double d) {
        return (double) Math.round(d * 100) / 100;
    }

    /**
     * The BigDecimal class provides operations for arithmetic, scale manipulation, rounding, comparison, hashing, and format conversion.
     *
     * @param d parameter
     * @return result
     */
    public static double formatDouble2(double d) {
        // 旧方法，已经不再推荐使用
        //BigDecimal bg = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
        // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }

    /**
     * NumberFormat is the abstract base class for all number formats.
     * This class provides the interface for formatting and parsing numbers.
     *
     * @param d parameter
     * @return result
     */
    public static String formatDouble3(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(d);
    }

    /**
     * 这个方法挺简单的。
     * DecimalFormat is a concrete subclass of NumberFormat that formats decimal numbers.
     *
     * @param d parameter
     * @return result
     */
    public static String formatDouble4(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(d);
    }

    /**
     * 保留两位小数
     *
     * @param num parameter
     * @return result
     */
    public static String twoBitDecimal(String num) {
        // 格式化小数
        DecimalFormat df = new DecimalFormat("0.00");
        // 返回的是String类型
        return df.format(Double.parseDouble(num));
    }

    /**
     * 如果只是用于程序中的格式化数值然后输出，那么这个方法还是挺方便的。
     * 应该是这样使用：System.out.println(String.format("%.2f", d));
     *
     * @param d parameter
     * @return result
     */
    @SuppressLint("DefaultLocale")
    public static String formatDouble5(double d) {
        return String.format("%.2f", d);
    }

    public static long getFormatCent(String amount) {
        long formatCents = 0L;
        try {
            BigDecimal original = new BigDecimal(amount);
            BigDecimal hundred = new BigDecimal("100");
            formatCents = original.multiply(hundred).longValueExact();
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        return formatCents;
    }

    public static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]+(.[0-9]{1,2})?$");
    }

}
