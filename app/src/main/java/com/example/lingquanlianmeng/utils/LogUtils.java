package com.example.lingquanlianmeng.utils;

import android.util.Log;

public class LogUtils {
    public static int currentLve = 4;

    public static final int DEBUGLVE = 4;
    public static final int INFOLVE = 3;
    public static final int WARNINGLVE = 2;
    public static final int ERRORLVE = 1;

    public static void d(Object object, String log){
        if(currentLve >= DEBUGLVE){
            Log.d(object.getClass().getSimpleName(),log);
        }
    }
    public static void i(Object object, String log){
        if(currentLve >= INFOLVE){
            Log.d(object.getClass().getSimpleName(),log);
        }
    }
    public static void w(Object object, String log){
        if(currentLve >= WARNINGLVE){
            Log.d(object.getClass().getSimpleName(),log);
        }
    }
    public static void e(Object object, String log){
        if(currentLve >= ERRORLVE){
            Log.d(object.getClass().getSimpleName(),log);
        }
    }
}
