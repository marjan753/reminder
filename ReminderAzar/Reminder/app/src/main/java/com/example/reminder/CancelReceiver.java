package com.example.reminder;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CancelReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        int notificationId = intent.getIntExtra("notificationId", -1);
       // int notificationOk= notificationId-1;
        Log.d(TAG, "CancelReceiver  " + notificationId);
        // Cancel the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notificationId);


    }





    }



