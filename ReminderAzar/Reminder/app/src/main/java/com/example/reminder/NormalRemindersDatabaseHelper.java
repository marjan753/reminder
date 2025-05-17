package com.example.reminder;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NormalRemindersDatabaseHelper extends SQLiteOpenHelper {
    public static final String COLUMNNumberrepeat = "numberrepeat";
    public static final String COLUMNRepeat_ID = "repeatid";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_Date = "date";
    public static final String COLUMN_DateG = "dateG";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_time = "time";
    public static final String COLUMNkindofrepeat = "kindofrepeat";
    public static final String DATABASE_NAME = "reminders.db";
    private static final int DATABASE_VERSION = 7;
    public static final String TABLE_NAME = "reminders";
    private static Context mContext;

    public NormalRemindersDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 7);
        mContext = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE reminders (_id INTEGER PRIMARY KEY AUTOINCREMENT,description TEXT,date TEXT,dateG TEXT,time TEXT,repeatid INTEGER,numberrepeat INTEGER,kindofrepeat INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS reminders");
        onCreate(db);
    }

    public int insertReminder(ReminderNormal reminder) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, reminder.getDescription());
        values.put(COLUMN_Date, reminder.getDate());
        values.put(COLUMN_DateG, reminder.getDateGregorian());
        values.put(COLUMN_time, reminder.getTime());
        values.put(COLUMNRepeat_ID, Integer.valueOf(reminder.getRepeat_id()));
        values.put(COLUMNkindofrepeat, Integer.valueOf(reminder.getKindofrepeat()));
        values.put(COLUMNNumberrepeat, Integer.valueOf(reminder.getNumberrepeat()));
        long ID = db.insert(TABLE_NAME, null, values);
        scheduleAlarmsFromDatabase(reminder.getDateGregorian(), reminder.getTime(), reminder.getDescription(), (int) ID);
        db.close();
        return (int) ID;
    }

    public List<ReminderNormal> getAllReminders() {
        List<ReminderNormal> reminders = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            cursor.close();
            db.close();
            Log.d("allofreminder", " allofreminder: " + reminders);
            return reminders;
        }
        do {
            ReminderNormal reminder = new ReminderNormal(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            reminder.setId(cursor.getInt(0));
            reminder.setRepeat_id(cursor.getInt(5));
            reminder.setNumberrepeat(cursor.getInt(6));
            reminder.setKindofrepeat(cursor.getInt(7));
            reminders.add(reminder);
        } while (cursor.moveToNext());
        cursor.close();
        db.close();
        Log.d("allofreminder", " allofreminder: " + reminders);
        return reminders;
    }

    public void updateTakReminder(long reminderId, String description, String date, String dateGregorian, String time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_Date, date);
        values.put(COLUMN_DateG, dateGregorian);
        values.put(COLUMN_time, time);
        db.update(TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(reminderId)});
       AlarmReceiver alarmReceiver=new AlarmReceiver();
        alarmReceiver.cancelAlarm(mContext, (int) reminderId);
       scheduleAlarmsFromDatabase(dateGregorian, time, description, (int) reminderId);

        db.close();
    }
    public void updateAllReminderDT(long reminderId, String description, String time,String dateGregorian) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_time, time);
        db.update(TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(reminderId)});
         AlarmReceiver alarmReceiver=new AlarmReceiver();
         alarmReceiver.cancelAlarm(mContext, (int) reminderId);
         scheduleAlarmsFromDatabase(dateGregorian, time, description, (int) reminderId);
        db.close();
    }
    public void updateAllReminders(long reminderId, String description, String date, String dateGregorian, String time, int kindofrepeat, int numberrepeat) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_Date, date);
        values.put(COLUMN_DateG, dateGregorian);
        values.put(COLUMN_time, time);
        values.put(COLUMNNumberrepeat, Integer.valueOf(numberrepeat));
        values.put(COLUMNkindofrepeat, Integer.valueOf(kindofrepeat));
        db.update(TABLE_NAME, values, "_id = ?", new String[]{String.valueOf(reminderId)});
        AlarmReceiver alarmReceiver=new AlarmReceiver();
         alarmReceiver.cancelAlarm(mContext, (int) reminderId);
         scheduleAlarmsFromDatabase(dateGregorian, time, description, (int) reminderId);
        db.close();
    }

    public Cursor getRemindersByRepeatId(long repeatID) {
        return getReadableDatabase().query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_Date, COLUMN_DateG, COLUMN_time, COLUMNkindofrepeat, COLUMNNumberrepeat}, "repeatid = ?", new String[]{String.valueOf(repeatID)}, null, null, null);
    }

    public ReminderNormal getReminder(int id) {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_Date, COLUMN_time}, "_id=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new ReminderNormal(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
    }

    public void deleteAllRecords(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    public void scheduleAlarmsFromDatabase(String Date, String Time, String Description, int reminderId) {
        String[] dateArray = Date.split("/");
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);
        String[] timeArray = Time.split(":");
        int hourOfDay = Integer.parseInt(timeArray[0]);
        int minute = Integer.parseInt(timeArray[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, year);
        calendar.set(2, month - 1);
        calendar.set(5, day);
        calendar.set(11, hourOfDay);
        calendar.set(12, minute);
        calendar.set(13, 0);
        scheduleAlarm(calendar, Description, reminderId);
    }

    private void scheduleAlarm(Calendar calendar, String title, int reminderId) {
        //Log.d("MyApp", "scheduleAlarm method called");
        // Log.d("Title", title);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
        // alarmIntent.setAction("com.example.reminder.ACTION_ALARM_TRIGGER");
        alarmIntent.putExtra("Title", title);
        alarmIntent.putExtra("notificationId", reminderId);



        // PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, reminderId, alarmIntent, PendingIntent.FLAG_MUTABLE);
        PendingIntent pendingIntent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(
                    mContext,
                    reminderId,
                    alarmIntent,
                    PendingIntent.FLAG_MUTABLE
            );
            //  alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            // Use FLAG_UPDATE_CURRENT for older Android versions
            pendingIntent = PendingIntent.getBroadcast(
                    mContext,
                    reminderId,
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            // alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }




        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);




    }

    public void deleteAllReminders() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public void deleteReminder(int reminderId) {
        SQLiteDatabase db = getWritableDatabase();
        AlarmReceiver alarmReceiver=new AlarmReceiver();
        alarmReceiver.cancelAlarm(mContext, (int) reminderId);
        db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(reminderId)});
        db.close();
    }

    public List<ReminderNormal> fetchDataFromDatabase() {
        List<ReminderNormal> reminders = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_Date, COLUMN_DateG, COLUMN_time}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            reminders.add(new ReminderNormal((int) cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)), cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)), cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_Date)), cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DateG)), cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_time))));
        }
        cursor.close();
        return reminders;
    }

    public int getRowCount(Cursor cursor) {
        int rowCount = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    rowCount++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return rowCount;
    }

    public void deleteRow(long idToDelete) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(idToDelete)});
        db.close();
    }

    public void backupDataToFile() {
        List<ReminderNormal> reminders = fetchDataFromDatabase();
        JSONArray jsonArray = new JSONArray();
        for (ReminderNormal reminder : reminders) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(COLUMN_ID, reminder.getId());
                jsonObject.put(COLUMN_DESCRIPTION, reminder.getDescription());
                jsonObject.put(COLUMN_Date, reminder.getDate());
                jsonObject.put(COLUMN_DateG, reminder.getDateGregorian());
                jsonObject.put(COLUMN_time, reminder.getTime());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String backupData = jsonArray.toString();
        try {
            FileWriter fileWriter = new FileWriter(new File(mContext.getExternalFilesDir(null), "backup_data.json"));
            fileWriter.write(backupData);
            fileWriter.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}








