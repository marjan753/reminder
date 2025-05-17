package com.example.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView logoImageView = findViewById(R.id.image_logo);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        logoImageView.startAnimation(rotateAnimation);

        // Delay the activity transition after the animation completes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start a new activity here (replace SecondActivity.class with your target activity)
                Intent intent = new Intent(MainActivity.this, NormalActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity if needed
            }
        }, 4000); // Adjust the delay time (in milliseconds) as needed



    }





}