package com.mathmash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Home on 9/21/2016.
 */
public class RevisitMistakesChoose extends MainActivity implements View.OnClickListener{

    Button button_add;
    Button button_subtract;
    Button button_multiply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_revisit_mistakes_choose);
        button_add = (Button)findViewById(R.id.button_add);
        button_subtract = (Button)findViewById(R.id.button_subtract);
        button_multiply = (Button)findViewById(R.id.button_multiply);

        button_add.setOnClickListener(this);
        button_subtract.setOnClickListener(this);
        button_multiply.setOnClickListener(this);
        actionBar.setDisplayOptions(actionBar.DISPLAY_HOME_AS_UP | actionBar.DISPLAY_SHOW_CUSTOM | actionBar.DISPLAY_SHOW_HOME);
        actionBar.show();

        //show buttons only if mistakes for that operation exist
        stats mstats = new stats(RevisitMistakesChoose.this);
        if (!mstats.areMistakesPresent(getResources().getString(R.string.add_operation))){
            //button_add.setVisibility(View.GONE);
            button_add.setAlpha(0.4f);
            button_add.setEnabled(false);
        }

        if (!mstats.areMistakesPresent(getResources().getString(R.string.subtract_operation))){
            //button_subtract.setVisibility(View.GONE);
            button_subtract.setAlpha(0.4f);
            button_subtract.setEnabled(false);
        }

        if (!mstats.areMistakesPresent(getResources().getString(R.string.multiply_operation))){
            //button_multiply.setVisibility(View.GONE);
            button_multiply.setAlpha(0.4f);
            button_multiply.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        String operation = "";
        switch (v.getId()){
            case R.id.button_add:
                operation = getResources().getString(R.string.add_operation);
                break;
            case R.id.button_subtract:
                operation = getResources().getString(R.string.subtract_operation);
                break;
            case R.id.button_multiply:
                operation = getResources().getString(R.string.multiply_operation);
                break;
        }

        if (!operation.equals("")){
            Intent intent = new Intent(RevisitMistakesChoose.this,RevisitMistakes.class);
            intent.putExtra("operationChosen",operation);
            startActivity(intent);
        }
    }
}
