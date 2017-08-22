package com.mathmash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Home on 9/21/2016.
 */
public class RevisitMistakes extends MainActivity {
    private static final String TAG = "RevisitMistakes";
    full_question mfull_question;
    String operation;
    stats _stats;

    TextView tv_num1;
    TextView tv_operation;
    TextView tv_num2;
    EditText et_ans;
    Button btn_submit;
    Button btn_clear;
    Button btn_one, btn_two, btn_three, btn_four,btn_five,btn_six,btn_seven,btn_eight,btn_nine,btn_zero;
    int num1, num2;
    Boolean answerIsCorrect = false;
    int correctAnswer;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_question);
        tv_num1 = (TextView)findViewById(R.id.tv_num1);
        tv_num2 = (TextView)findViewById(R.id.tv_num2);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_clear = (Button)findViewById(R.id.btn_clear);
        tv_operation = (TextView)findViewById(R.id.tv_operation);
        et_ans = (EditText)findViewById(R.id.et_ans);

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
        adView = (AdView)findViewById(R.id.adView);
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

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_ans.setText("");
            }
        });

        if (mfull_question == null){
            mfull_question = new full_question();
        }

        if (getIntent().getStringExtra("operationChosen") == null){
            Intent intent = new Intent(RevisitMistakes.this,RevisitMistakesChoose.class);
            startActivity(intent);
        }
        operation = getIntent().getStringExtra("operationChosen");

        _stats = new stats(RevisitMistakes.this);
        _stats.getAllStats();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if correct
                if (operation.equals(getResources().getString(R.string.add_operation))) {
                    if (num1 + num2 == Integer.parseInt(et_ans.getText().toString())){
                        answerIsCorrect = true;
                    }
                    else {
                        answerIsCorrect = false;
                        correctAnswer = num1 + num2;
                    }
                } else if (operation.equals(getResources().getString(R.string.multiply_operation))) {
                    if ((num1 * num2) == Integer.parseInt(et_ans.getText().toString())){
                        answerIsCorrect = true;
                    }
                    else {
                        answerIsCorrect = false;
                        correctAnswer = num1 * num2;
                    }
                } else if (operation.equals(getResources().getString(R.string.subtract_operation))) {
                    if (num1 - num2 == Integer.parseInt(et_ans.getText().toString())){
                        answerIsCorrect = true;
                    }
                    else {
                        answerIsCorrect = false;
                        correctAnswer = num1 - num2;
                    }
                }



                //if answer correct - show cheer
                if (answerIsCorrect) {
                    int rowschanged= _stats.updateMistake(mfull_question.getId());
                    Log.d(TAG, "answer Correct");
                    Intent intent = new Intent(RevisitMistakes.this,Correct.class);
                    intent.putExtra("operationChosen",operation);
                    startActivity(intent);
                }
                else {
                    //else show jeer
                    Log.d(TAG, "answer Wrong");
                    Intent intent = new Intent(RevisitMistakes.this,Wrong.class);
                    intent.putExtra("operationChosen",operation);
                    intent.putExtra("correctAnswer",correctAnswer);
                    intent.putExtra("num1",num1);
                    intent.putExtra("num2",num2);
                    intent.putExtra("operation",operation);
                    startActivity(intent);
                }



            }
        });
    }

    @Override
    protected void onPause() {
        if (adView !=null) {
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

        mfull_question = _stats.get_a_mistake(operation);
        if (mfull_question.getId() < 0){
            FragmentManager fm = getSupportFragmentManager();
            dialog_mistakesComplete dlg = new dialog_mistakesComplete();
            dlg.show(fm, "fragment_edit_name");
            tv_num1.setText(" ");
            tv_num2.setText(" ");
        }
        else {
            num1 = mfull_question.getNum1();
            num2 = mfull_question.getNum2();

            tv_num1.setText(String.valueOf(mfull_question.getNum1()));
            tv_num2.setText(String.valueOf(mfull_question.getNum2()));

            if (operation.equals(getResources().getString(R.string.add_operation))) {
                tv_operation.setText("+");

            } else if (operation.equals(getResources().getString(R.string.multiply_operation))) {
                tv_operation.setText("*");

            } else if (operation.equals(getResources().getString(R.string.subtract_operation))) {
                tv_operation.setText("-");
            }
        }


    }

    View.OnClickListener numclicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
