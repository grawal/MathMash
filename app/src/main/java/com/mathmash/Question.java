package com.mathmash;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Random;

/**
 * Created by Home on 9/7/2016.
 */
public class Question extends MainActivity {
    public static final String TAG = "Question";
    TextView tv_num1;
    TextView tv_operation;
    TextView tv_num2;
    EditText et_ans;
    Button btn_submit;
    Button btn_clear;
    Button btn_one, btn_two, btn_three, btn_four,btn_five,btn_six,btn_seven,btn_eight,btn_nine,btn_zero;
    int repeatingLimit = 0;
    questionQueue q;
    AdView adView;

    String pref_operation;
    int pref_num1_from, pref_num1_to, pref_num2_from, pref_num2_to;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedpref_editor;
    int num1, num2;
    Boolean answerIsCorrect = false;
    int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question);
        tv_num1 = (TextView)findViewById(R.id.tv_num1);
        tv_operation = (TextView)findViewById(R.id.tv_operation);
        tv_num2 = (TextView)findViewById(R.id.tv_num2);
        et_ans = (EditText)findViewById(R.id.et_ans);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_clear = (Button)findViewById(R.id.btn_clear);
        adView = (AdView)findViewById(R.id.adView);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(actionBar.DISPLAY_HOME_AS_UP | actionBar.DISPLAY_SHOW_CUSTOM | actionBar.DISPLAY_SHOW_HOME);
        actionBar.show();

        if (adView != null) {
            /*AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();*/
        AdRequest adRequest = new AdRequest.Builder()
                .build();
            adView.loadAd(adRequest);
        }

        /*et_ans.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //int inType = et_ans.getInputType();
                et_ans.setInputType(InputType.TYPE_NULL);
                return true;
            }
        });*/

                //get preferences
                sharedPref = getSharedPreferences("prefs",Context.MODE_PRIVATE);

        //if shared preferences aren't defined, then set the defaults.
        if (!sharedPref.contains(getString(R.string.operation))){
            Log.d(TAG, "preferences aren't defined. Setting default preferences.");
            sharedpref_editor = sharedPref.edit();
            sharedpref_editor.putString(getString(R.string.operation), getResources().getString(R.string.multiply_operation));
            sharedpref_editor.putInt(getString(R.string.num1_from), 1);
            sharedpref_editor.putInt(getString(R.string.num1_to), 10);
            sharedpref_editor.putInt(getString(R.string.num2_from), 1);
            sharedpref_editor.putInt(getString(R.string.num2_to), 10);
            sharedpref_editor.commit();
        }

        //number buttons
        btn_one = (Button)findViewById(R.id.btn_one);
        btn_two = (Button)findViewById(R.id.btn_two);
        btn_three = (Button)findViewById(R.id.btn_three);
        btn_four = (Button)findViewById(R.id.btn_four);
        btn_five = (Button)findViewById(R.id.btn_five);
        btn_six = (Button)findViewById(R.id.btn_six);
        btn_seven = (Button)findViewById(R.id.btn_seven);
        btn_eight = (Button)findViewById(R.id.btn_eight);
        btn_nine = (Button)findViewById(R.id.btn_nine);
        btn_zero = (Button)findViewById(R.id.btn_zero);
        btn_one.setOnClickListener(numclicked);
        btn_two.setOnClickListener(numclicked);
        btn_three.setOnClickListener(numclicked);
        btn_four.setOnClickListener(numclicked);
        btn_five.setOnClickListener(numclicked);
        btn_six.setOnClickListener(numclicked);
        btn_seven.setOnClickListener(numclicked);
        btn_eight.setOnClickListener(numclicked);
        btn_nine.setOnClickListener(numclicked);
        btn_zero.setOnClickListener(numclicked);

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_ans.setText("");
            }
        });

        q = questionQueue.getInstance();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_ans.getText().toString().equals("")){
                    Toast.makeText(Question.this,"Enter a Number",Toast.LENGTH_LONG).show();
                    Log.d(TAG,"no answer given");
                }
                else {

                    //check if correct
                    if (pref_operation.equals(getResources().getString(R.string.add_operation))) {
                        if (num1 + num2 == Integer.parseInt(et_ans.getText().toString())) {
                            answerIsCorrect = true;
                        } else {
                            answerIsCorrect = false;
                            correctAnswer = num1 + num2;
                        }
                    } else if (pref_operation.equals(getResources().getString(R.string.multiply_operation))) {
                        if ((num1 * num2) == Integer.parseInt(et_ans.getText().toString())) {
                            answerIsCorrect = true;
                        } else {
                            answerIsCorrect = false;
                            correctAnswer = num1 * num2;
                        }
                    } else if (pref_operation.equals(getResources().getString(R.string.subtract_operation))) {
                        if (num1 - num2 == Integer.parseInt(et_ans.getText().toString())) {
                            answerIsCorrect = true;
                        } else {
                            answerIsCorrect = false;
                            correctAnswer = num1 - num2;
                        }
                    }

                    //if answer correct - show cheer
                    if (answerIsCorrect) {
                        Log.d(TAG, "answer Correct");
                        Intent intent = new Intent(Question.this, Correct.class);
                        startActivity(intent);
                    } else {
                        //else show jeer
                        Log.d(TAG, "answer Wrong");
                        Intent intent = new Intent(Question.this, Wrong.class);
                        intent.putExtra("correctAnswer", correctAnswer);
                        intent.putExtra("num1", num1);
                        intent.putExtra("num2", num2);
                        intent.putExtra("operation", pref_operation);
                        startActivity(intent);
                    }

                    //store in database
                    stats stats = new stats(Question.this);
                    stats.addstats(num1, num2, pref_operation, Integer.parseInt(et_ans.getText().toString()), (answerIsCorrect) ? 1 : 0, String.valueOf(System.currentTimeMillis()));

                    //add to repeating queue
                    q.addToQuestionQueue(num1,num2,repeatingLimit);
                }
            }
        });
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        adView.destroy();
        adView = new AdView(this, AdSize.SMART_BANNER, myAdMobId);
        adView.setAdListener(this);
        LinearLayout layout = (LinearLayout) findViewById(R.id.adView);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest();
        adView.loadAd(adRequest);
    }*/

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
        pref_operation = sharedPref.getString(getString(R.string.operation),getResources().getString(R.string.multiply_operation));
        pref_num1_from = sharedPref.getInt(getString(R.string.num1_from),3);
        pref_num1_to = sharedPref.getInt(getString(R.string.num1_to),9);
        pref_num2_from = sharedPref.getInt(getString(R.string.num2_from),3);
        pref_num2_to = sharedPref.getInt(getString(R.string.num2_to),9);

        //set repeating limit
        /*
        5 - 5
        6 - 6
        ->if num1to = num1from && num2from = num2to
        --->limit = 1
        */
        if (repeatingLimit == 0 && (pref_num1_to == pref_num1_from) && (pref_num2_to == pref_num2_from)){
            repeatingLimit = 1;
        }

        /*
        5 - 5
        1 - 10
        -> if num1to = num1from && num2from != num2to
        ---> limit = (num2from - num2to) / 2
        */
        if (repeatingLimit == 0 && (pref_num1_to == pref_num1_from) && (pref_num2_from != pref_num2_to)){
            repeatingLimit = (pref_num2_to - pref_num2_from) / 2;
        }

        /*
        1 - 10
        5 - 5
        -> if num2to = num2from && num1from != num1to
        ---> limit = (num1from - num1to) / 2
        */
        if (repeatingLimit == 0 && (pref_num2_to == pref_num2_from) && (pref_num1_from != pref_num1_to)){
            repeatingLimit = (pref_num1_to - pref_num1_from) / 2;
        }

        /*
        5 - 6
        7 - 8
        -> if ((num2to - num2from) + (num1to - num1from) < 4)
        ---> limit = (4 + (num1to - num1from)(num2to - num2from)) / 2
         */
        if (repeatingLimit == 0 && ((pref_num2_to - pref_num2_from) + (pref_num1_to - pref_num1_from) < 4)){
            repeatingLimit = (4 + (pref_num1_to - pref_num1_from)*(pref_num2_to - pref_num2_from)) / 2;
        }

        /*
        1 - 20
        1 - 10
        -> if (diff num2 greater)
        ---> limit = (4 + (num1to - num1from)(num2to - num2from)) / (num1to - num1from)
        -> if (diff num1 greater)
        ---> limit = (4 + (num1to - num1from)(num2to - num2from)) / (num2to - num2from)
         */
        if (repeatingLimit == 0){
            if ((pref_num2_to - pref_num2_from) > (pref_num1_to - pref_num1_from)){
                repeatingLimit = (4 + (pref_num1_to - pref_num1_from)*(pref_num2_to - pref_num2_from)) / (pref_num1_to - pref_num1_from);
            }else {
                repeatingLimit = (4 + (pref_num1_to - pref_num1_from)*(pref_num2_to - pref_num2_from)) / (pref_num2_to - pref_num2_from);
            }
        }



        Random r = new Random();
        Log.d(TAG,"pref_num1_to - pref_num1_from: "+pref_num1_to + " - "+pref_num1_from);
        Log.d(TAG,"pref_num2_to - pref_num2_from: "+pref_num2_to + " - "+pref_num2_from);
        //find a non repeating number
        int counter = 0;
        do {
            if (pref_num1_from != pref_num1_to) {
                num1 = r.nextInt(pref_num1_to - pref_num1_from) + pref_num1_from;
            } else {
                num1 = pref_num1_from;
            }
            if (pref_num2_from != pref_num2_to) {
                num2 = r.nextInt(pref_num2_to - pref_num2_from) + pref_num2_from;
            } else {
                num2 = pref_num2_from;
            }
            counter ++;
        }while(q.isQuestionRepeating(num1,num2) && counter < 25);
        Log.d(TAG,"num1: "+num1+" num2: "+num2);
        if (pref_operation.equals(getResources().getString(R.string.subtract_operation)) && (num1 < num2)){
            int temp = num2;
            num2 = num1;
            num1 = temp;
        }
        tv_num1.setText(String.valueOf(num1));
        tv_num2.setText(String.valueOf(num2));
        if (pref_operation.equals(getResources().getString(R.string.add_operation))) {
            tv_operation.setText("+");

        } else if (pref_operation.equals(getResources().getString(R.string.multiply_operation))) {
            tv_operation.setText("*");

        } else if (pref_operation.equals(getResources().getString(R.string.subtract_operation))) {
            tv_operation.setText("-");
        }
    }

    View.OnClickListener numclicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //v.setback
            //ObjectAnimator colorAnim = ObjectAnimator.ofInt(v, "textColor", getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent));
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofInt("textColor", getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.colorAccent));
            ObjectAnimator colorAnim = ObjectAnimator.ofPropertyValuesHolder(v, pvhX);
            colorAnim.setDuration(500);
            colorAnim.setEvaluator(new ArgbEvaluator());
            colorAnim.setRepeatCount(1);
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);
            colorAnim.start();
            switch(v.getId()){
                case R.id.btn_one:
                    et_ans.setText(et_ans.getText() + "1");
                    break;
                case R.id.btn_two:
                    et_ans.setText(et_ans.getText() + "2");
                    break;
                case R.id.btn_three:
                    et_ans.setText(et_ans.getText() + "3");
                    break;
                case R.id.btn_four:
                    et_ans.setText(et_ans.getText() + "4");
                    break;
                case R.id.btn_five:
                    et_ans.setText(et_ans.getText() + "5");
                    break;
                case R.id.btn_six:
                    et_ans.setText(et_ans.getText() + "6");
                    break;
                case R.id.btn_seven:
                    et_ans.setText(et_ans.getText() + "7");
                    break;
                case R.id.btn_eight:
                    et_ans.setText(et_ans.getText() + "8");
                    break;
                case R.id.btn_nine:
                    et_ans.setText(et_ans.getText() + "9");
                    break;
                case R.id.btn_zero:
                    et_ans.setText(et_ans.getText() + "0");
                    break;
            }
        }
    };
}
