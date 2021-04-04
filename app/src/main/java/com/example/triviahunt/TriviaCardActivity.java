package com.example.triviahunt;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class TriviaCardActivity extends AppCompatActivity {
    public int counter = 10;
    public boolean choiceSelected = false;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView counttime=findViewById(R.id.timer);

        CountDownTimer cd = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(!choiceSelected) {
                    counttime.setText(String.valueOf(counter));
                    counter--;
                }
            }
            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    public void onClick(View view) {

        if(view.getId() == R.id.answer1 || view.getId() == R.id.answer2 || view.getId() == R.id.answer3 || view.getId() == R.id.answer4) {
            choiceSelected = true;

            switch(view.getId()) {
                case R.id.answer1:
                    findViewById(R.id.answer1).setBackgroundColor(Color.parseColor("#228B22"));
                    break;

                case R.id.answer2:
                    findViewById(R.id.answer2).setBackgroundColor(Color.parseColor("#228B22"));
                    break;

                case R.id.answer3:
                    findViewById(R.id.answer3).setBackgroundColor(Color.parseColor("#228B22"));
                    break;

                case R.id.answer4:
                    findViewById(R.id.answer4).setBackgroundColor(Color.parseColor("#228B22"));
                    break;
            }
        }
    }
}