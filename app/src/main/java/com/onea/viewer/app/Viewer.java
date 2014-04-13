package com.onea.viewer.app;

import android.util.Log;

public final class Viewer {

    public static boolean DEBUG;

    public static void Log(String tag, String message) {
        if (Viewer.DEBUG) {
            Log.i(tag, message);
        }
    }

}