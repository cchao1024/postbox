package com.cchao.pinbox.util;

/**
 * @author : cchao
 * @version 2019-02-13
 */
public class Printer {

    public static void print(Object... args) {
        System.out.print(getFormat(args));
    }

    public static void println(Object... args) {
        System.out.println(getFormat(args));
    }

    public static String getFormat(Object... args) {
        String content = "";
        for (Object arg : args) {
            content += "【" + arg + "】";
        }
        return content;
    }
}
