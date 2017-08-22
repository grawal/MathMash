package com.mathmash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

/**
 * Created by Home on 9/7/2016.
 */
public class Correct extends Activity {
    Boolean comingFromQuestionActivity = true;
    String operationChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correct_layout);
        if (getIntent().getStringExtra("operationChosen") != null){
            comingFromQuestionActivity = false;
            operationChosen = getIntent().getStringExtra("operationChosen");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        new CountDownTimer(500, 1000) {
            public void onFinish() {
                if (comingFromQuestionActivity) {
                    Intent intent = new Intent(Correct.this, Question.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(Correct.this, RevisitMistakes.class);
                    intent.putExtra("operationChosen",operationChosen);
                    startActivity(intent);
                    finish();
                }
            }

            public void onTick(long millisUntilFinished) {
            }

        }.start();


        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }
}
