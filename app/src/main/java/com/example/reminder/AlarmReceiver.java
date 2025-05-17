package com.example.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID ="alarm_channel_Reminder";
    private static final String CHANNEL_NAME ="Alarm Channel_Reminder";
    int notificationId;
    String title;








    @Override
    public void onReceive(Context context, Intent intent) {


       // Log.d("AlarmReceiverJ", "pendingIntentArray size: " + pendingIntentArray.size());
            title = intent.getStringExtra("Title");
         notificationId=intent.getIntExtra("notificationId",-1);

        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_layout);

        Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.number1);
       // Log.d("MYSOUND", "sound: " +  R.raw.number1);



        notificationLayout.setTextViewText(R.id.notification_content, title);

        // Toast.makeText(context.getApplicationContext(), "Hello, Toast!", Toast.LENGTH_LONG).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setCustomContentView(notificationLayout)
                .setSound(soundUri)
                .setCustomBigContentView(notificationLayout)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)


                ;



        Intent cancelIntent = new Intent(context, CancelReceiver.class);
        cancelIntent.putExtra("notificationId", notificationId);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context, notificationId, cancelIntent,  PendingIntent.FLAG_MUTABLE);
        notificationLayout.setOnClickPendingIntent(R.id.cancel_button, cancelPendingIntent);




        Intent repeatIntent = new Intent(context, RepeatReceiver.class);
        repeatIntent.putExtra("notificationId", notificationId);
        repeatIntent.putExtra("Title", title);
     //  PendingIntent repeatPendingIntent = PendingIntent.getBroadcast(context, notificationId, repeatIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent repeatPendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            repeatPendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId,
                    repeatIntent,
                    PendingIntent.FLAG_MUTABLE
            );
            //  alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            // Use FLAG_UPDATE_CURRENT for older Android versions
            repeatPendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId,
                    repeatIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            // alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        notificationLayout.setOnClickPendingIntent(R.id.repeat_button, repeatPendingIntent);





        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();



        // Create a notification channel for devices running Android Oreo and above
        //Log.d("MyAppVersion", "SDK Version: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT>=  Build.VERSION_CODES.O) {


            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setSound(soundUri,audioAttributes);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                channel.setAllowBubbles(true);
            }


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);


        }


        // Show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());








    }



    public void cancelAlarm(Context context, int alarmId) {


        AlarmManager am = (AlarmManager)  context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent pendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(
                    context,
                    alarmId,
                    intent,
                    PendingIntent.FLAG_MUTABLE
            );

        } else {

            pendingIntent = PendingIntent.getBroadcast(
                    context,
                    alarmId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

        }

        am.cancel(pendingIntent);


    }


    }



