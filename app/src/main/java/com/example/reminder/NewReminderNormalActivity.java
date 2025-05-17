package com.example.reminder;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import saman.zamani.persiandate.PersianDate;

public class NewReminderNormalActivity extends AppCompatActivity
        {

    EditText new_description;
    Button pickerdate;
    String selectedDate = "";
    TextView text_pickerdate;
    TextView text_test;
    String selectedDateG="";

    Button pickertime;
    TextView text_pickertime;
    String selectedtime = "";
    Button button_addnew;

    TextView textdayofweek;

    TimePickerDialog pickert;
    NormalRemindersDatabaseHelper dbHelper;
    ReminderNormal reminder;
    String selectedItem="";

    int year_save;
    int month_save;
    int day_save;
    int hour_seve;
    int minute_seve;

    int numberofRepeat;
    int kindofRepeat=0;

            int sdkversion;
            List<Integer> selectedCheckboxes;

            EditText numberRepeatEditext;


            Spinner spin;

            int currentCheckedId = -1;
            private RadioButton currentlySelectedRadioButton;
            private CheckBox selectedCheckBox;



            private ProgressBar progressBar;



            // int totalMinutes;
    private PersianDatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder_normal);




        new_description=findViewById(R.id.new_description_normal);

       pickerdate = findViewById(R.id.Pikerpersion);
       text_pickerdate=findViewById(R.id.textdate);

       pickertime=findViewById(R.id.timepicker);
        text_pickertime=findViewById(R.id.texttime);


        text_test=findViewById(R.id.texttest);
        textdayofweek=findViewById(R.id.textdayofweek);

       button_addnew=findViewById(R.id.add_new_normal);

       numberRepeatEditext=findViewById(R.id.numberRepeat);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);






// Set up the OnDateChangedListener for the DatePicker
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


        final View dayofweekRepeatLayout = findViewById(R.id.dayofweek_repeat);
        final View dayRepeatLayout = findViewById(R.id.day_repeat);
        final View weekRepeatLayout = findViewById(R.id.week_repeat);
        final View monthRepeatLayout = findViewById(R.id.month_repeat);
        final View yearRepeatLayout = findViewById(R.id.year_repeat);

        spin = findViewById(R.id.spinner);

        String[] repeat = {"نوع تکرار","روزهای هفته", "روز", "هفته", "ماه", "سال"};

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














        button_addnew.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

      //  sdkversion = Build.VERSION.SDK_INT;
        //Log.d("MyAppVersion", "SDK Version: " + sdkversion);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }


        String numberRepeatInput = numberRepeatEditext.getText().toString();



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


        /*if ( isDateTimeValid(selectedtime)) {
            // Show an error message to the user
            Toast.makeText(NewReminderNormalActivity.this, "لطفا زمان را بعد از زمان حال  وارد کنید.", Toast.LENGTH_SHORT).show();
            return;
        }*/


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
});



    }









            private String number_to_persian(int number) {

                String[] persianDigits = {
                        "۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"
                };

                String persianNumber = "";
                String numberStr = String.valueOf(number);

                for (char digit : numberStr.toCharArray()) {
                    if (Character.isDigit(digit)) {
                        int digitValue = Character.getNumericValue(digit);
                        persianNumber += persianDigits[digitValue];
                    } else {
                        persianNumber += digit;
                    }
                }

                return persianNumber;
            }







            private static String getDayOfWeek(String selectDate) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date;
                try {
                    date = dateFormat.parse(selectDate);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Invalid date";
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                String dayOfWeekString;

                switch (dayOfWeek) {
                    case Calendar.SUNDAY:
                        dayOfWeekString = "یکشنبه";
                        break;
                    case Calendar.MONDAY:
                        dayOfWeekString = "دوشنبه";
                        break;
                    case Calendar.TUESDAY:
                        dayOfWeekString = "سه شنبه";
                        break;
                    case Calendar.WEDNESDAY:
                        dayOfWeekString = "چهار شنبه";
                        break;
                    case Calendar.THURSDAY:
                        dayOfWeekString = "پنج شنبه";
                        break;
                    case Calendar.FRIDAY:
                        dayOfWeekString = "جمعه";
                        break;
                    case Calendar.SATURDAY:
                        dayOfWeekString = "شنبه";
                        break;
                    default:
                        dayOfWeekString = "Invalid day";
                        break;
                }

                return dayOfWeekString;
            }




            private static List<String> getOccurrences(String startDate, int selectedDay, int numOccurrences) {
                List<String> occurrences = new ArrayList<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
                Calendar calendar = Calendar.getInstance();

                try {
                    Date selectedDate = dateFormat.parse(startDate);
                    calendar.setTime(selectedDate);


                while (occurrences.size() < numOccurrences) {
                    int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    if (currentDayOfWeek == selectedDay) {
                      //  String formattedDate = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).format(calendar.getTime());
                        String formattedDate = dateFormat.format(calendar.getTime());
                        occurrences.add(formattedDate);
                    }
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }

                } catch (Exception e) {
                    // Handle parsing or other exceptions
                }
                return occurrences;
            }

            private boolean isDateTimeValid(String dateStr, String timeStr) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
                try {
                    // Combine date and time into a single string
                    String dateTimeStr = dateStr + " " + timeStr;

                    // Parse the user-input date and time
                    Date selectedDateTime = sdf.parse(dateTimeStr);

                    // Create a calendar in the device's local time zone
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

                //Log.d("kindofrepeat", String.valueOf(kindRepeat));
                List<String> dates = new ArrayList<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
                Calendar calendar = Calendar.getInstance();
                Calendar calendar2 = Calendar.getInstance();

                try {
                    Date selectedDate = dateFormat.parse(selectDate);
                    calendar.setTime(selectedDate);
                    calendar2.setTime(selectedDate);


                    String formattedDate = dateFormat.format(selectedDate);
                    dates.add(formattedDate);

                    if (kindRepeat == 30) {  // If repetition is by 30 days
                        for (int i = 1; i <= numberRepeat; i++) {
                            calendar.add(Calendar.MONTH, 1);
                            Date incrementedDate = calendar.getTime();
                            formattedDate = dateFormat.format(incrementedDate);
                            dates.add(formattedDate);
                        }
                    } else if (kindRepeat == 60) {  // If repetition is by 60 days
                        for (int i = 1; i <= numberRepeat; i++) {
                            calendar.add(Calendar.MONTH, 2);
                            Date incrementedDate = calendar.getTime();
                            formattedDate = dateFormat.format(incrementedDate);
                            dates.add(formattedDate);
                        }
                    } else if (kindRepeat == 90) {  // If repetition is by 60 months (5 years)
                        for (int i = 1; i <= numberRepeat; i++) {
                            calendar.add(Calendar.MONTH, 3);
                            Date incrementedDate = calendar.getTime();
                            formattedDate = dateFormat.format(incrementedDate);
                            dates.add(formattedDate);
                        }
                    }else if (kindRepeat == 365) {  // If repetition is by 60 months (5 years)
                        for (int i = 1; i <= numberRepeat; i++) {
                            if(i==1){
                                calendar2.add(Calendar.DAY_OF_MONTH,-1);
                                calendar2.add(Calendar.YEAR, 1);
                                calendar.add(Calendar.YEAR, 1);
                                Date incrementedDate = calendar2.getTime();
                                formattedDate = dateFormat.format(incrementedDate);
                                dates.add(formattedDate);

                            }
                            else {
                                calendar2.add(Calendar.YEAR, 1);
                                calendar.add(Calendar.YEAR, 1);
                                Date incrementedDate = calendar.getTime();
                                formattedDate = dateFormat.format(incrementedDate);
                                dates.add(formattedDate);
                            }

                        }
                    }
                    else {
                        for (int i = 1; i <= numberRepeat; i++) {
                            calendar.add(Calendar.DAY_OF_MONTH, kindRepeat);
                            Date incrementedDate = calendar.getTime();
                            formattedDate = dateFormat.format(incrementedDate);
                            dates.add(formattedDate);
                        }
                    }
                } catch (Exception e) {
                    // Handle parsing or other exceptions
                }

                return dates;
            }



            public List<String> GetDatesPersion(List<String> dateG){

                List<String> datesP = new ArrayList<>();

                for(int i=0 ; i< dateG.size();i++){

                    String dateTakG=dateG.get(i);

                    String[] dateArray =dateTakG.split("/");

                    int year = Integer.parseInt(dateArray[0]);
                    int month = Integer.parseInt(dateArray[1]);
                    int day = Integer.parseInt(dateArray[2]);

                    PersianDate pdate = new PersianDate();
                    int a[]=   pdate.gregorian_to_jalali(year,month,day);



                    String persianYear = number_to_persian(a[0]);
                    String persianMonth = number_to_persian(a[1]);
                    String persianDay = number_to_persian(a[2]);

                    // Create the Persian date string using Persian numbers
                    String dateTakP = persianYear + "/" + persianMonth + "/" + persianDay;
                    datesP.add(dateTakP);




                }

                return datesP;



            }






            private void executeDatabaseInsertion() {

                if(selectedItem=="روزهای هفته") {



                    List<String> datasdayofweek = new ArrayList<>();




                    datasdayofweek.add(selectedDateG);
                    for (int i = 0; i < selectedCheckboxes.size(); i++) {

                        List<String> occurrences = getOccurrences(selectedDateG, selectedCheckboxes.get(i), numberofRepeat);
                        datasdayofweek.addAll(occurrences);


                    }

                    List<String> datesdayofweekP = GetDatesPersion(datasdayofweek);



                    dbHelper = new NormalRemindersDatabaseHelper(NewReminderNormalActivity.this);
                    for(int i=0;i<datasdayofweek.size();i++){
                        reminder=new ReminderNormal(new_description.getText().toString(),datesdayofweekP.get(i),datasdayofweek.get(i),selectedtime);
                        dbHelper.insertReminder(reminder);

                    }

                    Intent intent = new Intent(NewReminderNormalActivity.this,NormalActivity.class);
                    startActivity(intent);

                }









                else{
                    List<String> datasG = new ArrayList<>();

                  datasG = GetDates(numberofRepeat, kindofRepeat, selectedDateG);
                    List<String> datasP = GetDatesPersion(datasG);






                    dbHelper = new NormalRemindersDatabaseHelper(NewReminderNormalActivity.this);

                    for(int i=0;i<datasG.size();i++){

                        reminder=new ReminderNormal(new_description.getText().toString(),datasP.get(i),datasG.get(i),selectedtime);
                        dbHelper.insertReminder(reminder);

                    }
                    Intent intent = new Intent(NewReminderNormalActivity.this,NormalActivity.class);
                    startActivity(intent);


                }


            }



        }
