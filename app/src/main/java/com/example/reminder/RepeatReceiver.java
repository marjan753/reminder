package com.example.reminder;


import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class RepeatReceiver extends BroadcastReceiver {

    String title;
    int notificationId;
    long repeatIntervalMillis;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("RepeatReceiver", "onReceive called");

        notificationId = intent.getIntExtra("notificationId", -1);
        // repeatIntervalMillis = intent.getLongExtra("RepeatInterval", 0);
        repeatIntervalMillis = intent.getLongExtra("RepeatInterval", 0);
        //  Log.d(TAG, " repeatonrepeatreceiver " +repeatIntervalMillis);
        title = intent.getStringExtra("Title");




       // Intent closeIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
      //  context.sendBroadcast(closeIntent);









        if (repeatIntervalMillis > 0) {


            scheduleNextAlarm(context,repeatIntervalMillis,title,notificationId);
            // Log.d(TAG, " =repeat5 " +repeatIntervalMillis);
            repeatIntervalMillis=0;





        }else {
          Intent repeatIntent = new Intent(context, RepeatActivity.class);
            repeatIntent.putExtra("notificationId", notificationId);
            repeatIntent.putExtra("Title", title);
           // repeatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            repeatIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

           Log.d("RepeatActivitystart", "Starting app: " + RepeatActivity.class.getSimpleName());
            // Log.d(TAG, " =repeat3 " +repeatIntervalMillis);
            context.startActivity(repeatIntent);



        }


    }







    private void scheduleNextAlarm(Context context, long repeatTime, String title, int notificationId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("Title", title);
        alarmIntent.putExtra("notificationId", notificationId);
      //  PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, alarmIntent,   PendingIntent.FLAG_MUTABLE);
        PendingIntent pendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId,
                    alarmIntent,
                    PendingIntent.FLAG_MUTABLE
            );
            //  alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            // Use FLAG_UPDATE_CURRENT for older Android versions
            pendingIntent = PendingIntent.getBroadcast(
                    context,
                    notificationId,
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            // alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

        // Calculate the next alarm time
        long nextAlarmTime = System.currentTimeMillis() + repeatTime;

        // Cancel the existing notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);

        // Schedule the next alarm using setExact
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent);
        }
    }
}
