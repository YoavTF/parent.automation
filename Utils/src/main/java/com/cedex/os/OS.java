package com.cedex.os;

public class OS {

    public static boolean isWin() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("win") >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
