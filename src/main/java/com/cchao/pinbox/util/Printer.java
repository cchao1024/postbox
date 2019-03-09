package com.cchao.pinbox.util;

/**
 * @author : cchao
 * @version 2019-02-13
 */
public class Printer {

    public static void print(Object... args) {
        String content = "";
        for (Object arg : args) {
            content += "[" + arg + "]";
        }
        System.out.print(content);
    }
}
