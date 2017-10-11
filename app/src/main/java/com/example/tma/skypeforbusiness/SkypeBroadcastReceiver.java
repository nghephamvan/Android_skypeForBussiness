package com.example.tma.skypeforbusiness;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by tmvien on 2/15/17.
 */

public class SkypeBroadcastReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = SkypeBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received a GCM notification");
    }
}
