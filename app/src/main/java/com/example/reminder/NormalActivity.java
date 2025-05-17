package com.example.reminder;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class NormalActivity extends AppCompatActivity  {
  private   Button buttonNewNormalreminder;
    private List<ReminderNormal> newNormalReminders = new ArrayList<>();
    private RecyclerView recyclerView;
   private AdaptorReminderNormal adapter;
    NormalRemindersDatabaseHelper dbHelper;
   FloatingActionButton add;
   Toolbar toolbar;
    private TextView emptyTextView;
    private EditText searchEditText;
    private boolean isSearchVisible = false;

    private DrawerLayout drawerLayout;

    private static final int REQUEST_CODE_PICK_DIRECTORY = 123;

    private static final int REQUEST_CODE_PICK_BACKUP_FILE = 2;

    private static final String TAG = "MainActivity";
    private static final String PERMISSION_POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);





        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emptyTextView = findViewById(R.id.emptyTextView);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_normal);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Set click listeners for the search and menu buttons
        ImageButton searchButton = toolbar.findViewById(R.id.search_button);

        if (!checkNotificationPermission()) {
            // Permission not granted, show an AlertDialog to the user
            showPermissionAlertDialog();
        }

      /*  if (!Settings.canDrawOverlays(NormalActivity.this)) {
            // "Appear on top" permission is not granted, request it
            showOverlayPermissionExplanation();
        } else {
            // Permission is already granted, continue with your app's logic
        }

        checkAndRequestAutostartPermission();*/


        searchEditText = toolbar.findViewById(R.id.searchEditText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleSearchBarVisibility();

            }
        });

    drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.navigationView);


        ImageButton navButton = toolbar.findViewById(R.id.menu_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   openNavigationDrawer();

                openNavigationDrawer();




            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Handle navigation item clicks here
                switch (menuItem.getItemId()) {

                    case R.id.chose_alarm:
                        openNotificationSoundSettings();
                        // Handle About Us item click
                        break;
                    case R.id.about_us:
                        AlertDialog.Builder builder = new AlertDialog.Builder(NormalActivity.this);
                        builder.setTitle("درباره برنامه");
                        builder.setMessage("این اپلیکیشن با داشتن تاریخ شمسی به فارسی زبانان کمک میکند تا به راحتی بتوانند یادآوری برای کارها خود قرار دهند.");
                        // add a button
                        builder.setPositiveButton("OK", null);
                        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;

                    case R.id.save_data:
                        // Handle About Us item click

                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                        startActivityForResult(intent, REQUEST_CODE_PICK_DIRECTORY);


                        break;

                    case R.id.backup_data:

                        Intent intent2 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent2.setType("*/*"); // Set the desired MIME type for backup files
                        startActivityForResult(intent2, REQUEST_CODE_PICK_BACKUP_FILE);




                        break;
                    case R.id.call_us:


                        AlertDialog.Builder builder_call = new AlertDialog.Builder(NormalActivity.this);
                        builder_call.setTitle("تماس با ما ");
                        builder_call.setMessage("marjanbagheri753@gmail.com");
                        // add a button
                        builder_call.setPositiveButton("OK", null);
                        // create and show the alert dialog
                        AlertDialog dialog_call = builder_call.create();
                        dialog_call.show();

                        break;

                    case R.id.share:
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "اپ یادآوری شمسی'reminder_P' ");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://cafebazaar.ir/app/?id=com.persianreminder_mb_&ref=share");

                        // Start the activity to show the sharing options
                        startActivity(Intent.createChooser(shareIntent, "Share via"));
                        break;


                    case R.id.emtiyaz:


                        String appPackageName = "your.package.name";

                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://cafebazaar.ir/app/?id=com.persianreminder_mb_&ref=share" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException e) {
                            // If Google Play Store app is not installed on the device, open a web browser
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://cafebazaar.ir/app/?id=com.persianreminder_mb_&ref=share" + appPackageName)));
                        }
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });







        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        dbHelper = new NormalRemindersDatabaseHelper(this);
        newNormalReminders = dbHelper.getAllReminders();
         Log.d("ssssss", " kindstring " + newNormalReminders);
        adapter= new AdaptorReminderNormal(this, newNormalReminders);

        recyclerView.setAdapter(adapter);








        if (newNormalReminders.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called before the text is changed. We don't need to do anything here.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called whenever the text is changed.
                // Perform search on the entered text.
                String searchText = charSequence.toString();
                performSearch(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after the text is changed. We don't need to do anything here.
            }
        });












        add=findViewById(R.id.add_normal);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NormalActivity.this,NewReminderNormalActivity.class);
                startActivity(intent);
            }
        });






    }



    private void toggleSearchBarVisibility() {
        if (searchEditText.getVisibility() == View.VISIBLE) {
            // Search bar is already visible, hide it
            searchEditText.setVisibility(View.GONE);
            isSearchVisible = false;
            performSearch(""); // Clear the search text and show all reminders
        } else {
            // Search bar is hidden, show it
            searchEditText.setVisibility(View.VISIBLE);
            isSearchVisible = true;
        }
    }

    private void performSearch(String searchText) {
        // Filter the reminders based on the search text
        List<ReminderNormal> filteredReminders = new ArrayList<>();

        for (ReminderNormal reminder : newNormalReminders) {
            if (reminder.getDescription().toLowerCase().contains(searchText.toLowerCase())) {
                filteredReminders.add(reminder);
            }
        }
        Log.d("Debug4", "RecyclerView: " + recyclerView);
        Log.d("Debug4", "EmptyTextView: " + emptyTextView);
        Log.d("Debug4", "NewNormalReminders size: " + (newNormalReminders != null ? newNormalReminders.size() : "null"));
        // Update the RecyclerView with the filtered reminders
        adapter = new AdaptorReminderNormal(this, filteredReminders);
        recyclerView.setAdapter(adapter);

        // Check if the filtered reminders list is empty and show/hide the empty view accordingly
        if (filteredReminders.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }
    }


    private void openNavigationDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void openNotificationSoundSettings() {
        // Open the system's sound settings for notifications
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(intent);
    }






    private void saveRemindersToLocation(Uri treeUri) {
        DocumentFile documentFile = DocumentFile.fromTreeUri(this, treeUri);
        if (documentFile != null && documentFile.isDirectory()) {
            String destinationFileName = "reminders_backup.txt"; // Set the desired file name
            DocumentFile destinationFile = documentFile.createFile("text/plain", destinationFileName);

            if (destinationFile != null) {
                try {
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery("SELECT * FROM " + NormalRemindersDatabaseHelper.TABLE_NAME, null);

                    int columnIndexID = cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_ID);
                    int columnIndexDescription = cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_DESCRIPTION);
                    int columnIndexDate = cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_Date);
                    int columnIndexDateG = cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_DateG);
                    int columnIndexTime = cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_time);

                    OutputStream outputStream = getContentResolver().openOutputStream(destinationFile.getUri());
                    if (outputStream != null) {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

                        while (cursor.moveToNext()) {
                            int id = cursor.getInt(columnIndexID);
                            String description = cursor.getString(columnIndexDescription);
                            String date = cursor.getString(columnIndexDate);
                            String dateG = cursor.getString(columnIndexDateG);
                            String time = cursor.getString(columnIndexTime);

                            String reminderLine = id + "," + description + "," + date + "," + dateG + "," + time;
                            writer.write(reminderLine + "\n");
                        }

                        cursor.close();
                        writer.close();

                        Toast.makeText(this, "یادآوری های شما به درستی ذخیره شد.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "یادآوری های شما به درستی ذخیره نشد.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "یادآوری های شما به درستی ذخیره نشد. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void restoreRemindersFromBackup(Uri backupFileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(backupFileUri);
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.beginTransaction();

                try {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 5) {
                            ContentValues values = new ContentValues();
                            values.put(NormalRemindersDatabaseHelper.COLUMN_DESCRIPTION, parts[1]);
                            values.put(NormalRemindersDatabaseHelper.COLUMN_Date, parts[2]);
                            values.put(NormalRemindersDatabaseHelper.COLUMN_DateG, parts[3]);
                            values.put(NormalRemindersDatabaseHelper.COLUMN_time, parts[4]);

                            long insertedRowId = db.insert(NormalRemindersDatabaseHelper.TABLE_NAME, null, values);
                            if (insertedRowId == -1) {
                                Log.e("Database", "Error inserting data: " + line);
                            }
                        }
                    }

                    db.setTransactionSuccessful();
                    db.endTransaction();

                } catch (IOException e) {
                    e.printStackTrace();

                    Log.e("RestoreError", "Error reading backup file: " + e.getMessage());
                    Toast.makeText(this, "یادآوری شما به درستی برگردانده نشد.", Toast.LENGTH_SHORT).show();
                } finally {
                    db.endTransaction();
                    bufferedReader.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            Log.e("RestoreError", "Error restoring reminders from backup: " + e.getMessage());
        }

        Toast.makeText(this, "یادآوری شما به درستی برگردانده شد.", Toast.LENGTH_SHORT).show();
        emptyTextView.setVisibility(View.GONE);
        List<ReminderNormal> restoredReminders = dbHelper.getAllReminders();
        adapter.updateData(restoredReminders);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_DIRECTORY && resultCode == Activity.RESULT_OK) {
            Uri treeUri = data.getData();
            // Save the database to the selected location using the treeUri
            saveRemindersToLocation(treeUri);
        } else if (requestCode == REQUEST_CODE_PICK_BACKUP_FILE && resultCode == RESULT_OK) {
            Uri backupFileUri = data.getData();
            // Restore data from the selected backup file
            restoreRemindersFromBackup(backupFileUri);
        }else if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            // Handle the result of the "Appear on top" permission request
            if (Settings.canDrawOverlays(this)) {
                // Permission granted, proceed with your app's logic
            } else {
                // Permission not granted, handle accordingly
                // You might want to inform the user about the requirement and the need to grant the permission
            }
        }

    }
    private boolean checkNotificationPermission() {
        return ContextCompat.checkSelfPermission(this, PERMISSION_POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_POST_NOTIFICATIONS},
                    PERMISSION_REQUEST_CODE);
        }
    }

    private void showPermissionAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("اجازه دسترسی اعلان ها ");
        builder.setMessage("به اعلان ها اجازه داده شود. ");
        builder.setPositiveButton("اجازه", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Request the notification permission
                requestNotificationPermission();
            }
        });
        builder.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle if the user cancels the dialog
                Toast.makeText(NormalActivity.this, "دسترسی به اعلان ها لغو شد.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    // Rest of the code...

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "برنامه به اعلان ها دسترسی پیدا کرد.");
                // Perform the action for granted permission
            } else {
                Log.d(TAG, "دسترسی لغو شد.!");
            }
        }
    }


   /* private void showOverlayPermissionExplanation() {
        new AlertDialog.Builder(this)
                .setTitle("مجوز لازم")
                .setMessage("این برنامه برای عملکرد صحیح به مجوز 'ظاهر شدن'نیاز دارد این مجوز را اعطا کنید.")
                .setPositiveButton("اجازه", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked "Yes," take them to the device settings
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
                    }
                })
                .setNegativeButton("لغو", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked "No," handle this case accordingly
                        // You might want to disable or limit functionality that requires the permission
                    }
                })
                .show();
    }

    private void checkAndRequestAutostartPermission() {
        if (!isXiaomiDevice()) {
            // On non-Xiaomi devices, there is no "Autostart" setting
            return;
        }

        if (!isAutostartPermissionGranted()) {
            // The "Autostart" permission is not granted, so show the AlertDialog
            showAutostartPermissionAlertDialog();
        }
    }

    private boolean isXiaomiDevice() {
        // Implement a check to identify Xiaomi devices
        // You can use Build.MANUFACTURER and other criteria to determine the device's manufacturer
        // For example:
        return "Xiaomi".equalsIgnoreCase(Build.MANUFACTURER);
    }

    private boolean isAutostartPermissionGranted() {
        // You should implement this method to check if the "Autostart" permission is granted
        // This may involve checking a system setting or using a Xiaomi-specific API
        // For example:
        return Settings.canDrawOverlays(this); // Check if "Appear on top" permission is granted
    }

    private void showAutostartPermissionAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" اجازه آغاز خودکار");
        builder.setMessage("این برنامه برای عملکرد صحیح به مجوز 'آغاز خودکار پس زمینه 'نیاز دارد این مجوز را اعطا کنید.");
        builder.setPositiveButton("اجازه", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openAutostartSettings();
            }
        });
        builder.setNegativeButton("لغو", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the user's decision to cancel
                // You can display a message or take any necessary action
            }
        });
        builder.show();
    }*/

    private void openAutostartSettings() {
        Intent autostartIntent = new Intent();
        autostartIntent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(autostartIntent, PackageManager.MATCH_DEFAULT_ONLY);

        if (resolveInfo != null) {
            startActivity(autostartIntent);
        } else {
            // The "Autostart" settings activity is not available on this device
            // Handle accordingly
        }
    }

    public void backButtonEvent() {
        androidx.appcompat.app.AlertDialog.Builder exit= new androidx.appcompat.app.AlertDialog.Builder(NormalActivity.this);

        exit.setTitle("هشدار");
        exit.setMessage("آیا قصد خروج از برنامه را دارید؟");

        exit.setPositiveButton("بله",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        exit.setNegativeButton("خیر",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        exit.show();

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub



        backButtonEvent();

    }

}

