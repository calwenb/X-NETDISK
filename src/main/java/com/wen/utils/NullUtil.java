package com.wen.utils;


public class NullUtil {
    /**
     * 判断是否有空的参数
     * 有，返回true
     *
     * @author Mr.文
     */
    public static boolean hasNull(Object... params) {
        for (Object o : params) {
            if (o == null|| "".equals(o)) {
                return true;
            }
        }
        return false;
    }

    public static String msg() {
        return ResponseUtil.error("参数有空！");
    }
}
