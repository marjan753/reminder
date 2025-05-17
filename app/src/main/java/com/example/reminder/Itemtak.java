package com.example.reminder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Itemtak extends AppCompatActivity {
    private TextView description;
    private TextView date;
    private TextView dateG;
    private TextView Time;

    private String description_item;
    private String data_item;
    private String dataG_item;
    private String time_item;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemtak);






        description = (TextView) findViewById(R.id.text_item);
        date = (TextView) findViewById(R.id.data_item);
        dateG = (TextView) findViewById(R.id.dataG_item);
        Time = (TextView) findViewById(R.id.time_item);



        description_item = getIntent().getStringExtra("description");
        data_item = getIntent().getStringExtra("date");
        dataG_item = getIntent().getStringExtra("date_G");
        time_item = getIntent().getStringExtra("time");


        description.setText(description_item);
        date.setText(data_item);
        dateG.setText(dataG_item);
        Time.setText(time_item);



    }
}