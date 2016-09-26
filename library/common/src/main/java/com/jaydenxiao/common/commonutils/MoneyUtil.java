package com.jaydenxiao.common.commonutils;

import java.text.DecimalFormat;

/**
 * des:金钱
 * Created by xsf
 * on 2016.06.11:48
 */
public class MoneyUtil {
    public static String MoneyFomatWithTwoPoint(double d){
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }
}
