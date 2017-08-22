package com.mathmash;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Home on 9/14/2016.
 */
public class ViewStats extends MainActivity {

    private List<stats> statslist;
    private ListView lst_mistakes;
    private TextView txt_stats_add_correct;
    private TextView txt_stats_add_incorrect;
    private TextView txt_stats_mult_correct;
    private TextView txt_stats_mult_incorrect;
    private TextView txt_stats_sub_correct;
    private TextView txt_stats_sub_incorrect;

    private ImageButton btn_viewDetails_add_correct;
    private ImageButton btn_viewDetails_add_incorrect;
    private ImageButton btn_viewDetails_mult_correct;
    private ImageButton btn_viewDetails_mult_incorrect;
    private ImageButton btn_viewDetails_sub_correct;
    private ImageButton btn_viewDetails_sub_incorrect;

    private static final String TAG = "ViewStats";

    private Cursor correct_addition;
    private Cursor correct_subtraction;
    private Cursor correct_multiplication;
    private Cursor incorrect_addition;
    private Cursor incorrect_subtraction;
    private Cursor incorrect_multiplication;
    private stats mystats;
    private SimpleCursorAdapter adapter;

    private static DialogFragment newFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_stats);

        //declare buttons, textviews, listview
        //lst_mistakes = (ListView) findViewById(R.id.lst_mistakes);
        txt_stats_add_correct = (TextView) findViewById(R.id.txt_stats_add_correct);
        txt_stats_add_incorrect= (TextView) findViewById(R.id.txt_stats_add_incorrect);
        txt_stats_mult_correct = (TextView) findViewById(R.id.txt_stats_mult_correct);
        txt_stats_mult_incorrect = (TextView) findViewById(R.id.txt_stats_mult_incorrect);
        txt_stats_sub_correct = (TextView) findViewById(R.id.txt_stats_sub_correct);
        txt_stats_sub_incorrect = (TextView) findViewById(R.id.txt_stats_sub_incorrect);
        btn_viewDetails_add_correct = (ImageButton) findViewById(R.id.btn_viewDetails_add_correct);
        btn_viewDetails_add_incorrect = (ImageButton) findViewById(R.id.btn_viewDetails_add_incorrect);
        btn_viewDetails_mult_correct = (ImageButton) findViewById(R.id.btn_viewDetails_mult_correct);
        btn_viewDetails_mult_incorrect = (ImageButton) findViewById(R.id.btn_viewDetails_mult_incorrect);
        btn_viewDetails_sub_correct = (ImageButton) findViewById(R.id.btn_viewDetails_sub_correct);
        btn_viewDetails_sub_incorrect = (ImageButton) findViewById(R.id.btn_viewDetails_sub_incorrect);

        //set and get Cursors
        /*private Cursor correct_addition;
        private Cursor correct_subtraction;
        private Cursor correct_multiplication;
        private Cursor incorrect_addition;
        private Cursor incorrect_subtraction;
        incorrect_multiplication = new Cursor();*/
        mystats = new stats(this);
        correct_addition = mystats.getCorrect(getResources().getString(R.string.add_operation));
        correct_subtraction = mystats.getCorrect(getResources().getString(R.string.subtract_operation));
        correct_multiplication = mystats.getCorrect(getResources().getString(R.string.multiply_operation));
        incorrect_addition = mystats.getInCorrect(getResources().getString(R.string.add_operation));
        incorrect_subtraction = mystats.getInCorrect(getResources().getString(R.string.subtract_operation));
        incorrect_multiplication = mystats.getInCorrect(getResources().getString(R.string.multiply_operation));

        //fill textviews with right stats
        txt_stats_add_correct.setText(String.valueOf(correct_addition.getCount()));
        //Log.d(TAG,String.valueOf(correct_addition.getCount()));
        //txt_stats_add_correct.setText("000");
        txt_stats_sub_correct.setText(String.valueOf(correct_subtraction.getCount()));
        txt_stats_mult_correct.setText(String.valueOf(correct_multiplication.getCount()));
        txt_stats_add_incorrect.setText(String.valueOf(incorrect_addition.getCount()));
        txt_stats_sub_incorrect.setText(String.valueOf(incorrect_subtraction.getCount()));
        txt_stats_mult_incorrect.setText(String.valueOf(incorrect_multiplication.getCount()));

        //click details behavior
        /*btn_viewDetails_add_correct = (Button) findViewById(R.id.btn_viewDetails_add_correct);
        btn_viewDetails_add_incorrect = (Button) findViewById(R.id.btn_viewDetails_add_incorrect);
        btn_viewDetails_mult_correct = (Button) findViewById(R.id.btn_viewDetails_mult_correct);
        btn_viewDetails_mult_incorrect = (Button) findViewById(R.id.btn_viewDetails_mult_incorrect);
        btn_viewDetails_sub_correct = (Button) findViewById(R.id.btn_viewDetails_sub_correct);
        btn_viewDetails_sub_incorrect = (Button) findViewById(R.id.btn_viewDetails_sub_incorrect);*/
        btn_viewDetails_add_correct.setOnClickListener(new viewDetailsClicked());
        btn_viewDetails_add_incorrect.setOnClickListener(new viewDetailsClicked());
        btn_viewDetails_mult_correct.setOnClickListener(new viewDetailsClicked());
        btn_viewDetails_mult_incorrect.setOnClickListener(new viewDetailsClicked());
        btn_viewDetails_sub_correct.setOnClickListener(new viewDetailsClicked());
        btn_viewDetails_sub_incorrect.setOnClickListener(new viewDetailsClicked());

        actionBar.setDisplayOptions(actionBar.DISPLAY_HOME_AS_UP | actionBar.DISPLAY_SHOW_CUSTOM | actionBar.DISPLAY_SHOW_HOME);
        actionBar.show();

        //if count is 0, then don't show details
        if (correct_addition.getCount() == 0){
            btn_viewDetails_add_correct.setVisibility(View.INVISIBLE);
        }

        if (incorrect_addition.getCount() == 0){
            btn_viewDetails_add_incorrect.setVisibility(View.INVISIBLE);
        }

        if (correct_subtraction.getCount() == 0){
            btn_viewDetails_sub_correct.setVisibility(View.INVISIBLE);
        }

        if (incorrect_subtraction.getCount() == 0){
            btn_viewDetails_sub_incorrect.setVisibility(View.INVISIBLE);
        }

        if (correct_multiplication.getCount() == 0){
            btn_viewDetails_mult_correct.setVisibility(View.INVISIBLE);
        }

        if (incorrect_multiplication.getCount() == 0){
            btn_viewDetails_mult_incorrect.setVisibility(View.INVISIBLE);
        }
    }

    private class viewDetailsClicked implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch(v.getId()){
                case R.id.btn_viewDetails_add_correct:
                    newFragment = stats_details_gv.newInstance(getResources().getString(R.string.add_operation), true);
                    newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    newFragment.show(getSupportFragmentManager(), "dialog");
                    /*adapter = new SimpleCursorAdapter(ViewStats.this,R.layout.stats_wrong_list,correct_addition,
                            new String[] {dbhelper.stats.COLUMN_NUM1,
                                    dbhelper.stats.COLUMN_NUM2},
                            new int[] {R.id.stats_wrong_num1,R.id.stats_wrong_num2},
                            0);
                    lst_mistakes.setAdapter(adapter);*/
                    break;
                case R.id.btn_viewDetails_add_incorrect:
                    newFragment = stats_details_gv.newInstance(getResources().getString(R.string.add_operation), false);
                    newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    newFragment.show(getSupportFragmentManager(), "dialog");


                    /*adapter = new SimpleCursorAdapter(ViewStats.this,R.layout.stats_wrong_list,
                            incorrect_addition,
                            new String[] {dbhelper.stats.COLUMN_NUM1,dbhelper.stats.COLUMN_NUM2},
                            new int[] {R.id.stats_wrong_num1,R.id.stats_wrong_num2},
                            0);
                    lst_mistakes.setAdapter(adapter);*/
                    break;
                case R.id.btn_viewDetails_mult_correct:
                    newFragment = stats_details_gv.newInstance(getResources().getString(R.string.multiply_operation), true);
                    newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    newFragment.show(getSupportFragmentManager(), "dialog");

                    /*adapter = new SimpleCursorAdapter(ViewStats.this,
                            R.layout.stats_wrong_list,correct_multiplication,
                            new String[] {dbhelper.stats.COLUMN_NUM1,dbhelper.stats.COLUMN_NUM2},
                            new int[] {R.id.stats_wrong_num1,R.id.stats_wrong_num2},
                            0);
                    lst_mistakes.setAdapter(adapter);*/
                    break;
                case R.id.btn_viewDetails_mult_incorrect:
                    newFragment = stats_details_gv.newInstance(getResources().getString(R.string.multiply_operation), false);
                    newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    newFragment.show(getSupportFragmentManager(), "dialog");

                    /*adapter = new SimpleCursorAdapter(ViewStats.this,
                            R.layout.stats_wrong_list,
                            incorrect_multiplication,
                            new String[] {dbhelper.stats.COLUMN_NUM1,dbhelper.stats.COLUMN_NUM2},
                            new int[] {R.id.stats_wrong_num1,R.id.stats_wrong_num2},
                            0);
                    lst_mistakes.setAdapter(adapter);*/
                    break;
                case R.id.btn_viewDetails_sub_correct:
                    newFragment = stats_details_gv.newInstance(getResources().getString(R.string.subtract_operation), true);
                    //newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                    newFragment.show(getSupportFragmentManager(), "dialog");

                    /*adapter = new SimpleCursorAdapter(ViewStats.this,R.layout.stats_wrong_list,
                            correct_subtraction,
                            new String[] {dbhelper.stats.COLUMN_NUM1,dbhelper.stats.COLUMN_NUM2},
                            new int[] {R.id.stats_wrong_num1,R.id.stats_wrong_num2},
                            0);
                    lst_mistakes.setAdapter(adapter);*/
                    break;
                case R.id.btn_viewDetails_sub_incorrect:
                    newFragment = stats_details_gv.newInstance(getResources().getString(R.string.subtract_operation), false);
                    //newFragment.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    newFragment.show(getSupportFragmentManager(), "dialog");

                    /*adapter = new SimpleCursorAdapter(ViewStats.this,
                            R.layout.stats_wrong_list,
                            incorrect_subtraction,
                            new String[] {dbhelper.stats.COLUMN_NUM1,dbhelper.stats.COLUMN_NUM2},
                            new int[] {R.id.stats_wrong_num1,R.id.stats_wrong_num2},
                            0);
                    lst_mistakes.setAdapter(adapter);*/
                    break;
            }
        }
    }
}
