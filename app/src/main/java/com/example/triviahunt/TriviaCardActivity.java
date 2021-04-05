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
    private TriviaCard thisCard;
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_card);
        thisCard = (TriviaCard)getIntent().getSerializableExtra("trivia card");
        ((TextView)findViewById(R.id.question)).setText(thisCard.getQuestion());
        ((TextView)findViewById(R.id.answer1)).setText(thisCard.getAnswers().get(0));
        ((TextView)findViewById(R.id.answer2)).setText(thisCard.getAnswers().get(1));
        ((TextView)findViewById(R.id.answer3)).setText(thisCard.getAnswers().get(2));
        ((TextView)findViewById(R.id.answer4)).setText(thisCard.getAnswers().get(3));
        final TextView counttime=findViewById(R.id.timer);

        new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(!choiceSelected && counttime != null) {
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
            checkSelection(view.getId());
        }
        finish();
    }

    private void checkSelection(int id){
        if (id == R.id.answer1 && thisCard.getCorrectIndex() == 0
        || id == R.id.answer2 && thisCard.getCorrectIndex() == 1
        || id == R.id.answer3 && thisCard.getCorrectIndex() == 2
        || id == R.id.answer4 && thisCard.getCorrectIndex() == 3) {
            findViewById(id).setBackgroundColor(Color.parseColor("#228B22"));
        } else {
            findViewById(id).setBackgroundColor(Color.parseColor("#CC1100"));
        }
        findViewById(R.id.answer1).setClickable(false);
        findViewById(R.id.answer2).setClickable(false);
        findViewById(R.id.answer3).setClickable(false);
        findViewById(R.id.answer4).setClickable(false);
    }
}