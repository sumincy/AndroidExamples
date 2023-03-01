package com.yung.android.common.util;

/**
 * <pre>
 *    author  : Yung
 *    email   : sumincy@163.com
 *    time    : 2023/03/01
 *    desc    :
 *    version : 1.0
 * <pre>
 */
public class NameUtil {
    public static String getName(Object obj) {
        return obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode());
    }
}
