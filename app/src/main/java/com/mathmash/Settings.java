package com.mathmash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Home on 9/11/2016.
 */
public class Settings extends MainActivity {
    NumberPicker np_num1_from;
    NumberPicker np_num1_to;
    NumberPicker np_num2_from;
    NumberPicker np_num2_to;
    RadioGroup rg_operation;
    RadioButton rb_multiply;
    RadioButton rb_add;
    RadioButton rb_subtract;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedpref_editor;
    private static final String TAG = "Settings activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        sharedPref = this.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        sharedpref_editor = sharedPref.edit();
        setContentView(R.layout.layout_settings);
        actionBar.setDisplayOptions(actionBar.DISPLAY_HOME_AS_UP | actionBar.DISPLAY_SHOW_CUSTOM | actionBar.DISPLAY_SHOW_HOME);
        actionBar.show();

        //NumberPicker
        np_num1_from = (NumberPicker)findViewById(R.id.np_num1_from);
        np_num1_to = (NumberPicker)findViewById(R.id.np_num1_to);

        np_num2_from = (NumberPicker)findViewById(R.id.np_num2_from);
        np_num2_to = (NumberPicker)findViewById(R.id.np_num2_to);
        initializeNumberPickers();

        //Operations
        rg_operation = (RadioGroup)findViewById(R.id.rg_operation);
        rb_multiply = (RadioButton)findViewById(R.id.rb_multiply);
        rb_add = (RadioButton)findViewById(R.id.rb_add);
        rb_subtract = (RadioButton)findViewById(R.id.rb_subtract);



        initializeOperations();

    }



    public void initializeNumberPickers(){
        np_num1_from.setMinValue(1);
        np_num1_from.setMaxValue(999);
        np_num1_from.setValue(sharedPref.getInt(getResources().getString(R.string.num1_from),1));

        np_num1_to.setMinValue(1);
        np_num1_to.setMaxValue(999);
        np_num1_to.setValue(sharedPref.getInt(getResources().getString(R.string.num1_to),1));

        np_num2_from.setMinValue(1);
        np_num2_from.setMaxValue(999);
        np_num2_from.setValue(sharedPref.getInt(getResources().getString(R.string.num2_from),1));

        np_num2_to.setMinValue(1);
        np_num2_to.setMaxValue(999);
        np_num2_to.setValue(sharedPref.getInt(getResources().getString(R.string.num2_to),1));

        np_num1_from.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > np_num1_to.getValue()){
                    np_num1_to.setValue(newVal);
                    sharedpref_editor.putInt(getString(R.string.num1_to), newVal);
                    //sharedpref_editor.commit();
                }
                sharedpref_editor.putInt(getString(R.string.num1_from), newVal);
                sharedpref_editor.commit();
            }
        });

        np_num1_to.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (np_num1_from.getValue() > newVal){
                    np_num1_from.setValue(newVal);
                    sharedpref_editor.putInt(getString(R.string.num1_from), newVal);
                }
                sharedpref_editor.putInt(getString(R.string.num1_to), newVal);
                sharedpref_editor.commit();
            }
        });

        np_num2_from.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > np_num2_to.getValue()){
                    np_num2_to.setValue(newVal);
                    sharedpref_editor.putInt(getString(R.string.num2_to), newVal);
                }
                sharedpref_editor.putInt(getString(R.string.num2_from), newVal);
                sharedpref_editor.commit();
            }
        });

        np_num2_to.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (np_num2_from.getValue() > newVal){
                    np_num2_from.setValue(newVal);
                    sharedpref_editor.putInt(getString(R.string.num2_from), newVal);
                }
                sharedpref_editor.putInt(getString(R.string.num2_to), newVal);
                sharedpref_editor.commit();
            }
        });

    }

    public void initializeOperations(){
        rg_operation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_add:
                        Log.d(TAG,"add operation selected");
                        sharedpref_editor.putString(getString(R.string.operation), getResources().getString(R.string.add_operation));
                        break;
                    case R.id.rb_multiply:
                        Log.d(TAG,"multiply operation selected");
                        sharedpref_editor.putString(getString(R.string.operation), getResources().getString(R.string.multiply_operation));
                        break;
                    case R.id.rb_subtract:
                        Log.d(TAG,"subtract operation selected");
                        sharedpref_editor.putString(getString(R.string.operation), getResources().getString(R.string.subtract_operation));
                        break;
                }
                sharedpref_editor.commit();
            }
        });

        if (sharedPref.getString(getResources().getString(R.string.operation),"").equals(getResources().getString(R.string.add_operation))) {
            ((RadioButton)rg_operation.findViewById(R.id.rb_add)).setChecked(true);
            //rg_operation.check(R.id.rb_add);
        }
        else if (sharedPref.getString(getResources().getString(R.string.operation),"").equals(getResources().getString(R.string.multiply_operation))){
            ((RadioButton)rg_operation.findViewById(R.id.rb_multiply)).setChecked(true);
            //rg_operation.check(R.id.rb_multiply);
        }
        else if (sharedPref.getString(getResources().getString(R.string.operation),"").equals(getResources().getString(R.string.subtract_operation))){
            ((RadioButton)rg_operation.findViewById(R.id.rb_subtract)).setChecked(true);
            //rg_operation.check(R.id.rb_subtract);
        }

    }
}
