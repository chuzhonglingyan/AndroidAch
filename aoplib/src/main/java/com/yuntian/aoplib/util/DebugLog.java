package com.yuntian.aoplib.util;

import android.util.Log;

import com.yuntian.aoplib.BuildConfig;

public class DebugLog {

    private DebugLog() {}

    /**
     * Send a debug log message
     * @param tag Source of a log message.
     * @param message The message you would like logged.
     */
    public static void log(String tag, String message) {
        if (BuildConfig.DEBUG){
            Log.d(tag, message);
        }
    }

}
