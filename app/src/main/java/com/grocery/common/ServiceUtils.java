package com.grocery.common;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class ServiceUtils {
    public static void sendLocalBroadcast(Context context, String action) {
        Intent intent = new Intent(action);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendLocalBroadcast(Context context, String action, String[] names, Object[] values) {
        if (names.length == 0 || names.length != values.length) {
            sendLocalBroadcast(context, action);
        } else {
            Intent intent = new Intent(action);
            for (int i = 0; i < names.length; i++) {

                if (values[i] instanceof Double) {
                    intent.putExtra(names[i], ((Double) values[i]).doubleValue());
                }

                if (values[i] instanceof String) {
                    intent.putExtra(names[i], (String) values[i]);
                }

                if (values[i] instanceof Integer) {
                    intent.putExtra(names[i], ((Integer) values[i]).intValue());
                }

                if (values[i] instanceof Long) {
                    intent.putExtra(names[i], ((Long) values[i]).longValue());
                }

                if (values[i] instanceof Boolean) {
                    intent.putExtra(names[i], ((Boolean) values[i]).booleanValue());
                }
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }

    }

    public static void stopService(Context context, Class<?> serviceClass) {
        context.stopService(new Intent(context, serviceClass));
    }

    public static void startService(Context context, Class<?> serviceClass) {
        context.startService(new Intent(context, serviceClass));
    }

    public static void startService(Context context, Class<?> serviceClass, String action) {
        context.startService(new Intent(context, serviceClass).setAction(action));
    }

    public static void startService(Context context, Class<?> serviceClass, String action, String[] names, Object[] values) {
        if (names.length == 0 || names.length != values.length) {
            startService(context, serviceClass, action);
        } else {
            Intent intentService = new Intent(context, serviceClass);
            intentService.setAction(action);
            for (int i = 0; i < names.length; i++) {

                if (values[i] instanceof Double) {
                    intentService.putExtra(names[i], ((Double) values[i]).doubleValue());
                }

                if (values[i] instanceof String) {
                    intentService.putExtra(names[i], (String) values[i]);
                }

                if (values[i] instanceof Integer) {
                    intentService.putExtra(names[i], ((Integer) values[i]).intValue());
                }

                if (values[i] instanceof Long) {
                    intentService.putExtra(names[i], ((Long) values[i]).longValue());
                }

                if (values[i] instanceof Boolean) {
                    intentService.putExtra(names[i], ((Boolean) values[i]).booleanValue());
                }
            }

            context.startService(intentService);
        }
    }
}
