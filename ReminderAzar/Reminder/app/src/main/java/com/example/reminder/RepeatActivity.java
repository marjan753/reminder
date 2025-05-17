package com.example.reminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class RepeatActivity extends AppCompatActivity {
    private int notificationId;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat);

        Log.d("RepeatActivity", "onCreate called");
        notificationId = getIntent().getIntExtra("notificationId", -1);
        title = getIntent().getStringExtra("Title");
        //  Log.d(TAG, " =title " +title);


        // Show the repeat interval selection dialog
        showRepeatIntervalDialog(RepeatActivity.this,notificationId);





    }

    private void showRepeatIntervalDialog(Context context, int notificationId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Repeat Interval");
        CharSequence[] items = {"5 دقیقه", "10 دقیقه", "30 دقیقه"};
        builder.setItems(items, (dialog, which) -> {
            long repeatIntervalMillis = 0;
            switch (which) {
                case 0:
                    repeatIntervalMillis = 5 * 60 * 1000; // 5 minute
                    break;
                case 1:
                    repeatIntervalMillis = 10 * 60 * 1000; // 10 minutes
                    break;
                case 2:
                    repeatIntervalMillis = 30 * 60 * 1000; // 30 minutes
                    break;
            }

            // Schedule the next alarm
            // scheduleNextAlarm(repeatIntervalMillis, title, notificationId);
            //  repeatIntervalMillis1=repeatIntervalMillis;

            Intent repeatReceiverIntent = new Intent(RepeatActivity.this, RepeatReceiver.class);
            repeatReceiverIntent.putExtra("RepeatInterval", repeatIntervalMillis);
            repeatReceiverIntent.putExtra("Title", title);
            repeatReceiverIntent.putExtra("notificationId", notificationId);
          //  Log.d(TAG, " =repeat4 " +repeatIntervalMillis);
            sendBroadcast(repeatReceiverIntent);
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }




}


