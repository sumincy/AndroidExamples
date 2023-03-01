package com.yung.android.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class TimeUtil {
    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS",
                Locale.getDefault()).format(new Date());
    }
}
