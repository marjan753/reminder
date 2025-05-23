package com.example.reminder;


import static java.util.Collections.addAll;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import saman.zamani.persiandate.PersianDate;


public class NewReminderNormalActivity extends AppCompatActivity {
    String Descriptionbefor;
    int IdRemindertoedit;
    int IdRepeatEdite;
    String Timebefor;
    Button button_addnew;
    int currentCheckedId = -1;
    private RadioButton currentlySelectedRadioButton;
    Cursor cursor;

    int day_save;
    NormalRemindersDatabaseHelper dbHelper;
    int hour_seve;
    int kindofRepeat = 0;
    int kindofrepeatEdite;
    int kindofrepeatdayofweek;
    int minute_seve;
    int month_save;
    ArrayList<Integer> myarrayID;
    List<String> myarraydateGbefor;
    List<String> myarraydatePbefor;
    EditText new_description;
    EditText numberRepeatEditext;
    int numberofRepeat;
    int numberrepeatEdite;
    private PersianDatePickerDialog picker;
    Button pickerdate;
    TimePickerDialog pickert;
    Button pickertime;
    private ProgressBar progressBar;
    RelativeLayout relativeLayout;
    ReminderNormal reminder;
    int repeatid;
    private int repeatidCounter;
    int sdkversion;
    private CheckBox selectedCheckBox;
    List<Integer> selectedCheckboxes;
    String selectedDate = "";
    String selectedDateG = "";
    String selectedItem = "";
    String selectedtime = "";
    Spinner spin;
    TextView text_pickerdate;
    TextView text_pickertime;
    TextView text_test;
    TextView textdayofweek;
    int year_save;
    int kindofrepeatupdate;

    String descriptionEdite;
    String dataEdite;
    String dateGEdite;
    String timeEdite;






    @SuppressLint("Range")
    public void onCreate(Bundle savedInstanceState) {
       // final View dayofweekRepeatLayout;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder_normal);



        final   View dayofweekRepeatLayout = findViewById(R.id.dayofweek_repeat);
        final View dayRepeatLayout = findViewById(R.id.day_repeat);
        final View weekRepeatLayout = findViewById(R.id.week_repeat);
        final View monthRepeatLayout = findViewById(R.id.month_repeat);
        final View yearRepeatLayout = findViewById(R.id.year_repeat);
        this.spin = (Spinner) findViewById(R.id.spinner);
        this.relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_repeat);
        this.new_description = (EditText) findViewById(R.id.new_description_normal);
        this.pickerdate = (Button) findViewById(R.id.Pikerpersion);
        this.text_pickerdate = (TextView) findViewById(R.id.textdate);
        this.pickertime = (Button) findViewById(R.id.timepicker);
        this.text_pickertime = (TextView) findViewById(R.id.texttime);
        this.text_test = (TextView) findViewById(R.id.texttest);
        this.textdayofweek = (TextView) findViewById(R.id.textdayofweek);
        this.button_addnew = (Button) findViewById(R.id.add_new_normal);
        this.numberRepeatEditext = (EditText) findViewById(R.id.numberRepeat);
        this.dbHelper = new NormalRemindersDatabaseHelper(this);
        getWindow().setSoftInputMode(3);
        this.repeatidCounter = getSharedPreferences("MyPreferences", 0).getInt("lastRepeatID", 1);
        Intent intent = getIntent();
        this.IdRemindertoedit = intent.getIntExtra("IDremindertoedit", -1);
        int IdRepeatEdite = intent.getIntExtra("repeat_id", -1);




        pickerdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "Bjalal.ttf");

                picker = new PersianDatePickerDialog(NewReminderNormalActivity.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMinYear(1402)
                        .setAllButtonsTextSize(20)
                        .setMaxYear(1500)
                        .setInitDate(1402, 1, 1)
                        .setActionTextColor(Color.GRAY)
                        .setTypeFace(typeface)
//               .setShowDayPicker(false)
                        .setTitleType(PersianDatePickerDialog.DAY_MONTH_YEAR)
                        .setShowInBottomSheet(true)
                        .setListener(new PersianPickerListener() {
                            @Override
                            public void onDateSelected(PersianPickerDate persianPickerDate) {



                                selectedDate=(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay());
                                text_pickerdate.setText(selectedDate);





                                PersianDate pdate = new PersianDate();
                                
                                int a[]=   pdate.jalali_to_gregorian(persianPickerDate.getPersianYear(),persianPickerDate.getPersianMonth(),persianPickerDate.getPersianDay());
                                selectedDateG=a[0]+"/"+a[1]+"/"+a[2];
                                text_test.setText(selectedDateG);
                                year_save= a[0];
                                month_save=a[1];
                                day_save=a[2];


                                String persianDayOfWeek = getDayOfWeek(selectedDateG);
                                textdayofweek.setText(persianDayOfWeek);











                            }

                            @Override
                            public void onDismissed() {
                                Toast.makeText(NewReminderNormalActivity.this, "Dismissed", Toast.LENGTH_SHORT).show();
                            }
                        });

                picker.show();

            }
        });

        pickertime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                hour_seve=hour;
                minute_seve=minutes;
                // time picker dialog
                pickert = new TimePickerDialog(NewReminderNormalActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                selectedtime=(sHour + ":" + sMinute);
                                text_pickertime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                pickert.show();



            }
        });




        spin = findViewById(R.id.spinner);

        String[] repeat = {"نوع تکرار", "روز", "هفته", "ماه","سال","روزهای هفته"};

// Create a custom adapter by extending ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, repeat) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;

                // Apply custom text color to the first item
                if (position == 0) {
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.button));
                } else {
                    textView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                }

                return view;
            }
        };
        selectedCheckboxes = new ArrayList<>();













        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

// Set a listener to handle item selection
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection

                if (currentlySelectedRadioButton != null) {
                    currentlySelectedRadioButton.setChecked(false);
                    currentlySelectedRadioButton = null;
                }
                selectedItem = (String) parent.getItemAtPosition(position);
                if (selectedItem.equals(getString(R.string.spinner_hint))) {
                    // Handle the case when the hint is selected

                } else {
                    // Handle other item selections




                    dayofweekRepeatLayout.setVisibility(View.GONE);
                    dayRepeatLayout.setVisibility(View.GONE);
                    weekRepeatLayout.setVisibility(View.GONE);
                    monthRepeatLayout.setVisibility(View.GONE);
                    yearRepeatLayout.setVisibility(View.GONE);







                    switch (selectedItem) {



                        case "روزهای هفته":

                            dayofweekRepeatLayout.setVisibility( View.VISIBLE);



                            CheckBox shanbe= findViewById(R.id.shanbe);
                            CheckBox yekshanbe= findViewById(R.id.yekshanbe);
                            CheckBox doshanbe= findViewById(R.id.doshanbe);
                            CheckBox seshanbe= findViewById(R.id.seshanbe);
                            CheckBox chaharshanbe= findViewById(R.id.chaharshanbe);
                            CheckBox panjshanbe= findViewById(R.id.panjshanbe);
                            CheckBox jome= findViewById(R.id.jomee);



                            shanbe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        selectedCheckboxes.add(7);
                                        kindofRepeat=72;
                                        selectedCheckBox = shanbe;
                                    } else {
                                        selectedCheckboxes.remove(Integer.valueOf(7));
                                        selectedCheckBox = null; // Clear the selected checkbox

                                    }

                                }
                            });

                            yekshanbe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        selectedCheckboxes.add(1);
                                        kindofRepeat=72;
                                        selectedCheckBox = yekshanbe;
                                    } else {
                                        selectedCheckboxes.remove(Integer.valueOf(1));
                                        selectedCheckBox = null;
                                    }

                                }
                            });
                            doshanbe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        selectedCheckboxes.add(2);
                                        kindofRepeat=72;
                                        selectedCheckBox = doshanbe;
                                    } else {
                                        selectedCheckboxes.remove(Integer.valueOf(2));
                                        selectedCheckBox = null;
                                    }
                                }
                            });

                            seshanbe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        selectedCheckboxes.add(3);
                                        kindofRepeat=72;
                                        selectedCheckBox = seshanbe;
                                    } else {
                                        selectedCheckboxes.remove(Integer.valueOf(3));
                                        selectedCheckBox = null;
                                    }
                                }
                            });

                            chaharshanbe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        selectedCheckboxes.add(4);
                                        kindofRepeat=72;
                                        selectedCheckBox = chaharshanbe;
                                    } else {
                                        selectedCheckboxes.remove(Integer.valueOf(4));
                                        selectedCheckBox = null;
                                    }
                                }
                            });

                            panjshanbe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        selectedCheckboxes.add(5);
                                        kindofRepeat=72;
                                        selectedCheckBox = panjshanbe;
                                    } else {
                                        selectedCheckboxes.remove(Integer.valueOf(5));
                                        selectedCheckBox = null;
                                    }
                                }
                            });
                            jome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if (isChecked) {
                                        selectedCheckboxes.add(6);
                                        kindofRepeat=72;
                                        selectedCheckBox = jome;
                                    } else {
                                        selectedCheckboxes.remove(Integer.valueOf(6));
                                        selectedCheckBox = null;
                                    }
                                }
                            });

                            break;
                        case "روز":
                            dayRepeatLayout.setVisibility( View.VISIBLE);

                            RadioGroup radioGroupD = findViewById(R.id.radiogroupday);

                            radioGroupD.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton selectedRadioButton = findViewById(checkedId);

                                    if (selectedRadioButton != null) {

                                        if (selectedCheckBox != null) {
                                            selectedCheckBox.setChecked(false);
                                            selectedCheckBox = null;
                                        }

                                        if (currentlySelectedRadioButton != null && currentlySelectedRadioButton != selectedRadioButton) {
                                            currentlySelectedRadioButton.setChecked(false);
                                        }

                                        currentlySelectedRadioButton = selectedRadioButton;


                                        switch (checkedId) {
                                            case R.id.oneday:
                                                kindofRepeat=1;
                                                break;
                                            case R.id.towday:
                                                kindofRepeat=2;
                                                break;
                                            case R.id.threeday:
                                                kindofRepeat=3;
                                                break;
                                            case R.id.fourday:
                                                kindofRepeat=4;
                                                break;
                                            case R.id.fiveday:
                                                kindofRepeat=5;
                                                break;
                                            case R.id.sexday:
                                                kindofRepeat=6;
                                                break;
                                            default:
                                                // Handle other cases or no selection
                                                break;
                                        }
                                    }
                                }
                            });

                            radioGroupD.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (currentCheckedId != -1) {
                                        // If a RadioButton is currently selected, clicking on the RadioGroup should deselect it
                                        radioGroupD.clearCheck();
                                        kindofRepeat = 0; // Or any other value that represents "None" or unselected state
                                        currentCheckedId = -1; // Reset the currently selected RadioButton ID
                                    }
                                }
                            });

                            break;




                        case "هفته":
                            weekRepeatLayout.setVisibility(  View.VISIBLE );




                            RadioGroup radioGroupW = findViewById(R.id.radiogroupweek);

                            radioGroupW.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton selectedRadioButton = findViewById(checkedId);
                                    if (selectedRadioButton != null) {

                                        if (selectedCheckBox != null) {
                                            selectedCheckBox.setChecked(false);
                                            selectedCheckBox = null;
                                        }

                                        if (currentlySelectedRadioButton != null && currentlySelectedRadioButton != selectedRadioButton) {
                                            currentlySelectedRadioButton.setChecked(false);
                                        }
                                        currentlySelectedRadioButton = selectedRadioButton;
                                        switch (checkedId) {
                                            case R.id.oneweek:
                                                kindofRepeat=7;

                                                break;
                                            case R.id.towweek:
                                                kindofRepeat=14;
                                                break;
                                            case R.id.threeweek:
                                                kindofRepeat=21;
                                                break;

                                            default:
                                                // Handle other cases or no selection
                                                break;
                                        }
                                    }
                                }
                            });







                            break;
                        case "ماه":
                            monthRepeatLayout.setVisibility(View.VISIBLE );

                            RadioGroup radioGroupM = findViewById(R.id.radiogroupmonth);

                            radioGroupM.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton selectedRadioButton = findViewById(checkedId);
                                    if (selectedRadioButton != null) {

                                        if (selectedCheckBox != null) {
                                            selectedCheckBox.setChecked(false);
                                            selectedCheckBox = null;
                                        }

                                        if (currentlySelectedRadioButton != null && currentlySelectedRadioButton != selectedRadioButton) {
                                            currentlySelectedRadioButton.setChecked(false);
                                        }
                                        currentlySelectedRadioButton = selectedRadioButton;
                                        switch (checkedId) {
                                            case R.id.onemonth:
                                                kindofRepeat=30;

                                                break;
                                            case R.id.towmonth:
                                                kindofRepeat=60;
                                                break;
                                            case R.id.threemonth:
                                                kindofRepeat=90;
                                                break;

                                            default:
                                                // Handle other cases or no selection
                                                break;
                                        }
                                    }
                                }
                            });






                            break;
                        case "سال":
                            yearRepeatLayout.setVisibility(View.VISIBLE );


                            RadioGroup radioGroupY = findViewById(R.id.radiogroupyear);

                            radioGroupY.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    RadioButton radioButton = findViewById(checkedId);

                                    if (radioButton != null && checkedId == R.id.oneyear) {

                                        if (selectedCheckBox != null) {
                                            selectedCheckBox.setChecked(false);
                                            selectedCheckBox = null;
                                        }


                                        if (currentlySelectedRadioButton == radioButton) {
                                            radioGroupY.clearCheck();
                                            currentlySelectedRadioButton = null;
                                            kindofRepeat = 0;
                                        } else {
                                            kindofRepeat = 365;

                                            if (currentlySelectedRadioButton != null && currentlySelectedRadioButton != radioButton) {
                                                currentlySelectedRadioButton.setChecked(false);
                                            }
                                            currentlySelectedRadioButton = radioButton;
                                        }
                                    } else {
                                        // Handle other RadioButton selections (if any)
                                    }
                                }
                            });


                            break;
                        default:

                            break;
                    }







                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });











        if (IdRemindertoedit != -1) {
            if (IdRepeatEdite == -1) {
                this.relativeLayout.setVisibility(View.GONE);
                String description_item = getIntent().getStringExtra(NormalRemindersDatabaseHelper.COLUMN_DESCRIPTION);
                String data_item = getIntent().getStringExtra(NormalRemindersDatabaseHelper.COLUMN_Date);
                String dataG_item = getIntent().getStringExtra(NormalRemindersDatabaseHelper.COLUMN_DateG);
                String time_item = getIntent().getStringExtra(NormalRemindersDatabaseHelper.COLUMN_time);
                this.text_pickerdate.setText(data_item);
                this.text_test.setText(dataG_item);
                this.text_pickertime.setText(time_item);
                this.new_description.setText(description_item);
                this.textdayofweek.setText(getDayOfWeek(dataG_item));

            } else {
                Log.d("vorood", " repeat: vared shode");
                this.kindofrepeatEdite = getIntent().getIntExtra("kindofrepeatEdite", -1);
                this.numberrepeatEdite = getIntent().getIntExtra("numberrepeatEdite", -1);
                Log.d("kindddd", " kindstring " + kindofrepeatEdite);
                descriptionEdite = getIntent().getStringExtra(NormalRemindersDatabaseHelper.COLUMN_DESCRIPTION);
                dataEdite = getIntent().getStringExtra(NormalRemindersDatabaseHelper.COLUMN_Date);
                dateGEdite = getIntent().getStringExtra(NormalRemindersDatabaseHelper.COLUMN_DateG);
                Log.d("dateG", " dateG " + dateGEdite);
                timeEdite = getIntent().getStringExtra(NormalRemindersDatabaseHelper.COLUMN_time);

                 // Adjust this according to your logic

                // Set the selection in the spinner

                if (kindofrepeatEdite == 1 || kindofrepeatEdite== 2 || kindofrepeatEdite==3 || kindofrepeatEdite==4 ||kindofrepeatEdite ==5 ||kindofrepeatEdite== 6) {
                   spin.setSelection(1);


                    switch (kindofrepeatEdite) {
                        case 1:
                            RadioButton selectedRadioButton = (RadioButton) findViewById(R.id.oneday);
                            selectedRadioButton.setVisibility(View.VISIBLE);
                            selectedRadioButton.setChecked(true);
                            break;
                        case 2:
                            // Log.d("Debuggg", "Kindofrepeat is 2");
                            //  dayRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton2 = (RadioButton) findViewById(R.id.towday);
                            selectedRadioButton2.setVisibility(View.VISIBLE);
                            selectedRadioButton2.setChecked(true);
                            break;
                        case 3:
                            //Log.d("Debuggg", "Kindofrepeat is 3");
                            // dayRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton3 = (RadioButton) findViewById(R.id.threeday);
                            selectedRadioButton3.setVisibility(View.VISIBLE);
                            selectedRadioButton3.setChecked(true);
                            break;
                        case 4:
                            // dayRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton4 = (RadioButton) findViewById(R.id.fourday);
                            selectedRadioButton4.setVisibility(View.VISIBLE);
                            selectedRadioButton4.setChecked(true);
                            break;
                        case 5:
                            // dayRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton5 = (RadioButton) findViewById(R.id.fiveday);
                            selectedRadioButton5.setVisibility(View.VISIBLE);
                            selectedRadioButton5.setChecked(true);
                            break;
                        case 6:
                            // dayRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton6 = (RadioButton) findViewById(R.id.sexday);
                            selectedRadioButton6.setVisibility(View.VISIBLE);
                            selectedRadioButton6.setChecked(true);
                            break;
                    }
               }

                if (kindofrepeatEdite == 7 || kindofrepeatEdite== 14 || kindofrepeatEdite==21 ) {
                    spin.setSelection(2);

                    switch (this.kindofrepeatEdite) {
                        case 7:
                            //  weekRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton7 = (RadioButton) findViewById(R.id.oneweek);
                            selectedRadioButton7.setVisibility(View.VISIBLE);
                            selectedRadioButton7.setChecked(true);
                            break;
                        case 14:
                            //  weekRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton14 = (RadioButton) findViewById(R.id.towweek);
                            selectedRadioButton14.setVisibility(View.VISIBLE);
                            selectedRadioButton14.setChecked(true);
                            break;
                        case 21:
                            // weekRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton21 = (RadioButton) findViewById(R.id.threeweek);
                            selectedRadioButton21.setVisibility(View.VISIBLE);
                            selectedRadioButton21.setChecked(true);
                            break;
                    }
                }
                if (kindofrepeatEdite == 30 || kindofrepeatEdite== 60 || kindofrepeatEdite==90 ) {
                    spin.setSelection(3);
                    switch (this.kindofrepeatEdite) {
                        case 30:
                            // monthRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton30 = (RadioButton) findViewById(R.id.onemonth);
                            selectedRadioButton30.setVisibility(View.VISIBLE);
                            selectedRadioButton30.setChecked(true);
                            break;
                        case 60:
                            // monthRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton60 = (RadioButton) findViewById(R.id.towmonth);
                            selectedRadioButton60.setVisibility(View.VISIBLE);
                            selectedRadioButton60.setChecked(true);
                            break;
                        case 90:
                            // monthRepeatLayout.setVisibility(View.VISIBLE);
                            RadioButton selectedRadioButton90 = (RadioButton) findViewById(R.id.threemonth);
                            selectedRadioButton90.setVisibility(View.VISIBLE);
                            selectedRadioButton90.setChecked(true);
                            break;

                    }
                }
                if (kindofrepeatEdite == 365 ) {
                    spin.setSelection(4);
                    RadioButton selectedRadioButton365 = (RadioButton) findViewById(R.id.oneyear);
                    selectedRadioButton365.setVisibility(View.VISIBLE);
                    selectedRadioButton365.setChecked(true);

                }
                String kindofrepeatEditeString = String.valueOf(this.kindofrepeatEdite);
                if (kindofrepeatEditeString.contains("72")) {
                     spin.setSelection(5);
                    int index72 = kindofrepeatEditeString.indexOf("72");
                    int i = index72 + 2;
                    while (i < kindofrepeatEditeString.length()) {
                        int digitValue = Character.getNumericValue(kindofrepeatEditeString.charAt(i));
                        Log.d("digikind", " int: " + digitValue);
                        switch (digitValue) {
                            case 1:
                                CheckBox yekshanbe = (CheckBox) findViewById(R.id.yekshanbe);
                                yekshanbe.setVisibility(View.VISIBLE);
                                selectedCheckboxes.add(1);
                                yekshanbe.setChecked(true);
                                break;
                            case 2:
                                CheckBox doshanbe = (CheckBox) findViewById(R.id.doshanbe);
                                doshanbe.setVisibility(View.VISIBLE);
                                selectedCheckboxes.add(2);
                                doshanbe.setChecked(true);
                                break;
                            case 3:
                                CheckBox seshanbe = (CheckBox) findViewById(R.id.seshanbe);
                                seshanbe.setVisibility(View.VISIBLE);
                                selectedCheckboxes.add(3);
                                seshanbe.setChecked(true);
                                break;
                            case 4:
                                CheckBox chaharshanbe = (CheckBox) findViewById(R.id.chaharshanbe);
                                chaharshanbe.setVisibility(View.VISIBLE);
                                selectedCheckboxes.add(4);
                                chaharshanbe.setChecked(true);
                                break;
                            case 5:
                                CheckBox panjshanbe = (CheckBox) findViewById(R.id.panjshanbe);
                                panjshanbe.setVisibility(View.VISIBLE);
                                selectedCheckboxes.add(5);
                                panjshanbe.setChecked(true);
                                break;
                            case 6:
                                CheckBox jome = (CheckBox) findViewById(R.id.jomee);
                                jome.setVisibility(View.VISIBLE);
                                selectedCheckboxes.add(6);
                                jome.setChecked(true);
                                break;
                            case 7:
                                CheckBox shanbe = (CheckBox) findViewById(R.id.shanbe);
                                shanbe.setVisibility(View.VISIBLE);
                                selectedCheckboxes.add(7);
                                shanbe.setChecked(true);
                                break;
                        }
                        i++;
                        index72 = index72;
                        kindofrepeatEditeString = kindofrepeatEditeString;
                        // dayofweekRepeatLayout2 = dayofweekRepeatLayout2;
                    }






                     }


                this.numberRepeatEditext.setText(String.valueOf(this.numberrepeatEdite));
                this.text_pickerdate.setText(dataEdite);
                this.text_test.setText(dateGEdite);
                this.text_pickertime.setText(timeEdite);
                this.new_description.setText(descriptionEdite);
                this.textdayofweek.setText(getDayOfWeek(dateGEdite));

                this.myarrayID = new ArrayList<>();
                this.myarraydatePbefor = new ArrayList();
                this.myarraydateGbefor = new ArrayList();

                NormalRemindersDatabaseHelper dbHelper = new NormalRemindersDatabaseHelper(this);
                cursor = dbHelper.getRemindersByRepeatId(IdRepeatEdite);
                //  Log.d("jadval", "dd " + this.cursor);



                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            this.myarrayID.add(Integer.valueOf(this.cursor.getInt(this.cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_ID))));
                            this.myarraydatePbefor.add(this.cursor.getString(this.cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_Date)));
                            this.myarraydateGbefor.add(this.cursor.getString(this.cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_DateG)));
                            this.Descriptionbefor = this.cursor.getString(this.cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_DESCRIPTION));
                            this.Timebefor = this.cursor.getString(this.cursor.getColumnIndex(NormalRemindersDatabaseHelper.COLUMN_time));
                        } while (cursor.moveToNext());
                    }
                }




            }









        }














        this.button_addnew.setOnClickListener(new View.OnClickListener() {
            /* class com.example.reminder.NewReminderNormalActivity.AnonymousClass5 */

            public void onClick(View v) {

                closeKeyboard();

                String numberRepeatInput =numberRepeatEditext.getText().toString();




                if (IdRemindertoedit != -1) {

                     if(IdRepeatEdite==-1){

                         dbHelper.updateTakReminder(IdRemindertoedit, new_description.getText().toString(),text_pickerdate.getText().toString(),text_test.getText().toString(), text_pickertime.getText().toString());
                      startActivity(new Intent(NewReminderNormalActivity.this, NormalActivity.class));



                      }else {
                         int rowCount = dbHelper.getRowCount(cursor);


                         if(!selectedCheckboxes.isEmpty()){
                             Log.d("check11", "Condition selectedcheck: " + (!selectedCheckboxes.isEmpty()));
                             kindofRepeat=72;

                         }


                         if(!selectedDateG.isEmpty() || kindofRepeat!=0 || Integer.parseInt(numberRepeatInput)!=numberrepeatEdite){
                             Log.d("check11", "Condition 1: " + (!selectedDateG.isEmpty()));
                             Log.d("check11", "Condition 2: " + (kindofRepeat != 0));
                             Log.d("check11", "Condition 3: " + (Integer.parseInt(numberRepeatInput) != numberrepeatEdite));


                           //  List<String> datasPEdit = null;
                          //   List<String> datasGEdit=null;
                             if(selectedDateG.isEmpty()){
                                 selectedDateG=dateGEdite;

                             }


                             if(kindofRepeat==0){
                                 kindofrepeatupdate=kindofrepeatEdite;
                             } else {


                                 if(String.valueOf(kindofRepeat).contains("72")) {

                                     kindofrepeatupdate=CreatekindofrepeatDayofweek(72,selectedCheckboxes);



                                 }else{

                                     kindofrepeatupdate=kindofRepeat;
                                 }

                             }
                             Log.d("check11", "kind"+selectedCheckboxes);
                             Log.d("check11", "kind"+selectedDateG);
                             Log.d("check11", "kind"+kindofrepeatupdate);
                             String kindofrepeatEditeupString = String.valueOf(kindofrepeatupdate);

                             if (kindofrepeatEditeupString.contains("72")) {
                                 Log.d("check11", "vared if72  shod");

                                 List<Integer> kindofrepeatupdateforDatelist= convertToDigitList(kindofrepeatupdate);
                                 Log.d("check11", "num: " +kindofrepeatupdateforDatelist);
                                 List<String> datasdayofweekEdit = new ArrayList<>();
                                 for (int i = 0; i < kindofrepeatupdateforDatelist.size(); i++) {

                                     datasdayofweekEdit.addAll(getOccurrences(selectedDateG,kindofrepeatupdateforDatelist.get(i).intValue(), Integer.parseInt(numberRepeatInput)));

                                 }
                                 Log.d("check11", "dats: " +datasdayofweekEdit);
                                 List<String>  datasdayofweekEditP = GetDatesPersion(datasdayofweekEdit);
                                 int size=datasdayofweekEditP.size();

                                 if (rowCount == size) {
                                     Log.d("check11", "وارد if اول");
                                     for (int i = 0; i < size; i++) {
                                         dbHelper.updateAllReminders(myarrayID.get(i).intValue(),new_description.getText().toString(), datasdayofweekEditP.get(i), datasdayofweekEdit.get(i), NewReminderNormalActivity.this.text_pickertime.getText().toString(), kindofrepeatupdate, Integer.parseInt(numberRepeatInput));
                                     }
                                     startActivity(new Intent(NewReminderNormalActivity.this, NormalActivity.class));

                                 }
                                 if (rowCount < size) {
                                     Log.d("check11", "وارد if دوم");
                                     int j = 0;
                                     int i = 0;
                                     Log.d("dbup1","i"+ i);
                                     Log.d("dbup1","j"+ j);
                                     while (i < rowCount) {

                                         dbHelper.updateAllReminders(myarrayID.get(i), new_description.getText().toString(), datasdayofweekEditP.get(i), datasdayofweekEdit.get(i), text_pickertime.getText().toString(), kindofrepeatupdate, Integer.parseInt(numberRepeatInput));
                                         j = i;
                                         i++;

                                     }
                                     j++;

                                     Log.d("dbup1","j"+ j);
                                     while (j < size) {
                                         reminder = new ReminderNormal(new_description.getText().toString(), datasdayofweekEditP.get(j), datasdayofweekEdit.get(j),text_pickertime.getText().toString(), IdRepeatEdite, kindofrepeatupdate, Integer.parseInt(numberRepeatInput));
                                         dbHelper.insertReminder(NewReminderNormalActivity.this.reminder);
                                         j++;

                                     }
                                     NewReminderNormalActivity.this.startActivity(new Intent(NewReminderNormalActivity.this, NormalActivity.class));

                                 }
                                 if (rowCount > size) {
                                     Log.d("check11", "وارد if سوم");
                                     int j = 0;
                                     int i = 0;
                                     while (i< size) {
                                         // Log.d("dbup1", str2);

                                         dbHelper.updateAllReminders(myarrayID.get(i), new_description.getText().toString(), datasdayofweekEditP.get(i), datasdayofweekEdit.get(i), text_pickertime.getText().toString(), kindofrepeatupdate, Integer.parseInt(numberRepeatInput));
                                         j = i;
                                         i++;

                                     }
                                     j++;
                                     while (j < rowCount) {
                                         dbHelper.deleteReminder(myarrayID.get(j).intValue());
                                         j++;

                                     }
                                     NewReminderNormalActivity.this.startActivity(new Intent(NewReminderNormalActivity.this, NormalActivity.class));

                                 }







                             }else {

                                 List<String>   datasGEdit = GetDates(Integer.parseInt(numberRepeatInput),kindofrepeatupdate,selectedDateG);
                                 List<String>  datasPEdit = GetDatesPersion(datasGEdit);


                                 int size=datasPEdit.size();

                                 if (rowCount == size) {
                                     Log.d("check11", "وارد if اول");
                                     for (int i = 0; i < size; i++) {
                                         dbHelper.updateAllReminders(myarrayID.get(i).intValue(),new_description.getText().toString(), datasPEdit.get(i), datasGEdit.get(i), NewReminderNormalActivity.this.text_pickertime.getText().toString(), kindofrepeatupdate, Integer.parseInt(numberRepeatInput));
                                     }
                                     startActivity(new Intent(NewReminderNormalActivity.this, NormalActivity.class));

                                 }
                                 if (rowCount < size) {
                                     Log.d("check11", "وارد if دوم");
                                     int j = 0;
                                     int i = 0;
                                     Log.d("dbup1","i"+ i);
                                     Log.d("dbup1","j"+ j);
                                     while (i < rowCount) {

                                         dbHelper.updateAllReminders(myarrayID.get(i), new_description.getText().toString(), datasPEdit.get(i), datasGEdit.get(i), text_pickertime.getText().toString(), kindofrepeatupdate, Integer.parseInt(numberRepeatInput));
                                         j = i;
                                         i++;

                                     }
                                     j++;

                                     Log.d("dbup1","j"+ j);
                                     while (j < size) {
                                         reminder = new ReminderNormal(new_description.getText().toString(), datasPEdit.get(j), datasGEdit.get(j),text_pickertime.getText().toString(), IdRepeatEdite, kindofrepeatupdate, Integer.parseInt(numberRepeatInput));
                                         dbHelper.insertReminder(NewReminderNormalActivity.this.reminder);
                                         j++;

                                     }
                                     NewReminderNormalActivity.this.startActivity(new Intent(NewReminderNormalActivity.this, NormalActivity.class));

                                 }
                                 if (rowCount > size) {
                                     Log.d("check11", "وارد if سوم");
                                     int j = 0;
                                     int i = 0;
                                     while (i< size) {
                                         // Log.d("dbup1", str2);

                                         dbHelper.updateAllReminders(myarrayID.get(i), new_description.getText().toString(), datasPEdit.get(i), datasGEdit.get(i), text_pickertime.getText().toString(), kindofrepeatupdate, Integer.parseInt(numberRepeatInput));
                                         j = i;
                                         i++;

                                     }
                                     j++;
                                     while (j < rowCount) {
                                         dbHelper.deleteReminder(myarrayID.get(j).intValue());
                                         j++;

                                     }
                                     NewReminderNormalActivity.this.startActivity(new Intent(NewReminderNormalActivity.this, NormalActivity.class));

                                 }





                             }




                         }else {
                             Log.d("check11", "vared else dovom shod");
                             for (int i = 0; i < rowCount; i++) {

                                 dbHelper.updateAllReminderDT(myarrayID.get(i), new_description.getText().toString(), text_pickertime.getText().toString(),dateGEdite);

                             }
                             startActivity(new Intent(NewReminderNormalActivity.this, NormalActivity.class));




                         }




                     }







                }else {

                    if (new_description.getText().toString().trim().isEmpty() || text_pickerdate.getText().toString().trim().isEmpty() ||  text_pickertime.getText().toString().trim().isEmpty() ) {
                        // Handle the case when the user has not chosen a date
                        Toast.makeText(getApplicationContext(), "لطفا تاریخ و زمان و توضیحات یادآوری خود را وارد کنید.", Toast.LENGTH_SHORT).show();
                        return; // Stop further execution
                    }




                    if (isDateTimeValid(selectedDateG,selectedtime)==false) {
                        // Show an error message to the user
                        Toast.makeText(NewReminderNormalActivity.this, "لطفا تاریخ و زمان را بعد از امروز و این ساعت وارد کنید.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(kindofRepeat==365 && Integer.parseInt(numberRepeatInput)>5){
                        Toast.makeText(getApplicationContext(), "تعداد تکرار برای سال را کمتر از 5 وارد کنید.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    if ( kindofRepeat==0 && numberRepeatInput.isEmpty()) {
                        numberofRepeat = 0;
                    } else  if (numberRepeatInput.isEmpty() && kindofRepeat!=0){
                        numberofRepeat=1;
                    }else if(!numberRepeatInput.isEmpty() && kindofRepeat!=0)
                    {
                        numberofRepeat = Integer.parseInt(numberRepeatInput);
                    }else if(!numberRepeatInput.isEmpty() && kindofRepeat==0) {
                        Toast.makeText(getApplicationContext(), "لطفا نوع تکرار را مشخص کنید.", Toast.LENGTH_SHORT).show();
                        return;
                    }



                    executeDatabaseInsertion();







                }










            }
        });
    }

    private String number_to_persian(int number) {
        String[] persianDigits = {"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};
        String persianNumber = "";
        char[] charArray = String.valueOf(number).toCharArray();
        for (char digit : charArray) {
            persianNumber = Character.isDigit(digit) ? persianNumber + persianDigits[Character.getNumericValue(digit)] : persianNumber + digit;
        }
        return persianNumber;
    }

    /* access modifiers changed from: private */
    public static String getDayOfWeek(String selectDate) {
        try {
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(selectDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            switch (calendar.get(7)) {
                case 1:
                    return "یکشنبه";
                case 2:
                    return "دوشنبه";
                case 3:
                    return "سه شنبه";
                case 4:
                    return "چهار شنبه";
                case 5:
                    return "پنج شنبه";
                case 6:
                    return "جمعه";
                case 7:
                    return "شنبه";
                default:
                    return "Invalid day";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Invalid date";
        }
    }

    private static List<String> getOccurrences(String startDate, int selectedDay, int numOccurrences) {


        List<String> occurrences = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(startDate));
            while (occurrences.size() < numOccurrences) {
                if (calendar.get(7) == selectedDay) {
                    occurrences.add(dateFormat.format(calendar.getTime()));
                }
                calendar.add(5, 1);
            }
        } catch (Exception e) {
        }

        return occurrences;
    }

    private boolean isDateTimeValid(String dateStr, String timeStr) {
        try {
            Date selectedDateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).parse(dateStr + " " + timeStr);
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.setTime(selectedDateTime);
            selectedCalendar.setTimeZone(TimeZone.getDefault());
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTimeZone(TimeZone.getDefault());
            return selectedCalendar.after(currentCalendar);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> GetDates(int numberRepeat, int kindRepeat, String selectDate) {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        try {
            Date selectedDate2 = dateFormat.parse(selectDate);
            calendar.setTime(selectedDate2);
            calendar2.setTime(selectedDate2);
            dates.add(dateFormat.format(selectedDate2));
            if (kindRepeat == 30) {
                for (int i = 1; i <= numberRepeat; i++) {
                    calendar.add(2, 1);
                    dates.add(dateFormat.format(calendar.getTime()));
                }
            } else if (kindRepeat == 60) {
                for (int i2 = 1; i2 <= numberRepeat; i2++) {
                    calendar.add(2, 2);
                    dates.add(dateFormat.format(calendar.getTime()));
                }
            } else if (kindRepeat == 90) {
                for (int i3 = 1; i3 <= numberRepeat; i3++) {
                    calendar.add(2, 3);
                    dates.add(dateFormat.format(calendar.getTime()));
                }
            } else if (kindRepeat == 365) {
                for (int i4 = 1; i4 <= numberRepeat; i4++) {
                    if (i4 == 1) {
                        calendar2.add(5, -1);
                        calendar2.add(1, 1);
                        calendar.add(1, 1);
                        dates.add(dateFormat.format(calendar2.getTime()));
                    } else {
                        calendar2.add(1, 1);
                        calendar.add(1, 1);
                        dates.add(dateFormat.format(calendar.getTime()));
                    }
                }
            } else {
                for (int i5 = 1; i5 <= numberRepeat; i5++) {
                    calendar.add(5, kindRepeat);
                    dates.add(dateFormat.format(calendar.getTime()));
                }
            }
        } catch (Exception e) {
        }
        return dates;
    }

    public List<String> GetDatesPersion(List<String> dateG) {
        List<String> datesP = new ArrayList<>();
        for (int i = 0; i < dateG.size(); i++) {
            String[] dateArray = dateG.get(i).split("/");
            int[] a = new PersianDate().gregorian_to_jalali(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2]));
            String persianYear = number_to_persian(a[0]);
            String persianMonth = number_to_persian(a[1]);
            datesP.add(persianYear + "/" + persianMonth + "/" + number_to_persian(a[2]));
        }
        return datesP;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void executeDatabaseInsertion() {
        if (this.selectedItem == "روزهای هفته") {
            List<String> datasdayofweek1 = new ArrayList<>();
            this.kindofrepeatdayofweek = CreatekindofrepeatDayofweek(72, this.selectedCheckboxes);
            datasdayofweek1.add(this.selectedDateG);
            for (int i = 0; i < this.selectedCheckboxes.size(); i++) {
                datasdayofweek1.addAll(getOccurrences(this.selectedDateG, this.selectedCheckboxes.get(i).intValue(), this.numberofRepeat));
            }
            List<String> datasdayofweek = removeDuplicates(datasdayofweek1);
            List<String> datesdayofweekP = GetDatesPersion(datasdayofweek);
            this.repeatid = generateUniqueRepeatID();
            for (int i2 = 0; i2 < datasdayofweek.size(); i2++) {
                ReminderNormal reminderNormal = new ReminderNormal(this.new_description.getText().toString(), datesdayofweekP.get(i2), datasdayofweek.get(i2), this.selectedtime, this.repeatid, this.kindofrepeatdayofweek, this.numberofRepeat);
                this.reminder = reminderNormal;
                this.dbHelper.insertReminder(reminderNormal);
            }
            startActivity(new Intent(this, NormalActivity.class));
            return;
        }
        new ArrayList();
        List<String> datasG = GetDates(numberofRepeat, kindofRepeat, selectedDateG);
        List<String> datasP = GetDatesPersion(datasG);
        this.repeatid = generateUniqueRepeatID();
        for (int i3 = 0; i3 < datasG.size(); i3++) {
            ReminderNormal reminderNormal2 = new ReminderNormal(this.new_description.getText().toString(), datasP.get(i3), datasG.get(i3), this.selectedtime, this.repeatid, this.kindofRepeat, this.numberofRepeat);
            this.reminder = reminderNormal2;
            this.dbHelper.insertReminder(reminderNormal2);
        }
        startActivity(new Intent(this, NormalActivity.class));
    }

    public int generateUniqueRepeatID() {
        int uniqueRepeatID = this.repeatidCounter;
        this.repeatidCounter++;
        SharedPreferences.Editor editor = getSharedPreferences("MyPreferences", 0).edit();
        editor.putInt("lastRepeatID", this.repeatidCounter);
        editor.apply();
        return uniqueRepeatID;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }

    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int CreatekindofrepeatDayofweek(int baseValue, List<Integer> digits) {
        String baseValueString = String.valueOf(baseValue);
        Iterator<Integer> it = digits.iterator();
        while (it.hasNext()) {
            baseValueString = baseValueString + it.next().intValue();
        }
        return Integer.parseInt(baseValueString);
    }

    public static List<Integer> convertToDigitList(int a) {
        List<Integer> resultList = new ArrayList<>();

        // Convert the integer to a string
        String numberString = Integer.toString(a);

        // Find the index of "72" in the string
        int index72 = numberString.indexOf("72");

        // Extract the digits after "72"
        if (index72 != -1 && index72 + 2 < numberString.length()) {
            String restOfNumber = numberString.substring(index72 + 2);

            // Convert the extracted string to a list of integers
            for (char digitChar : restOfNumber.toCharArray()) {
                resultList.add(Character.getNumericValue(digitChar));
            }
        } else {
            System.out.println("The number " + a + " doesn't contain '72' or has no digits after '72'.");
        }

        return resultList;
    }

    private int getDayValueFromCheckBox(CompoundButton checkBox) {
        switch (checkBox.getId()) {
            case R.id.shanbe:
                return 7;
            case R.id.yekshanbe:
                return 1;
            case R.id.doshanbe:
                return 2;
            case R.id.seshanbe:
                return 3;
            case R.id.chaharshanbe:
                return 4;
            case R.id.panjshanbe:
                return 5;
            case R.id.jomee:
                return 6;
            default:
                return -1; // or another default value if needed
        }
    }

    public static List<String> removeDuplicates(List<String> dataList) {
        Set<String> uniqueDates = new HashSet<>();
        List<String> result = new ArrayList<>();

        for (String date : dataList) {
            if (uniqueDates.add(date)) {
                // If the date is added to the set, it's unique
                result.add(date);
            }
        }

        return result;
    }
}



