package com.mathmash;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mathmash.R;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ActionBar actionBar;
    View mActionBarView;
    Menu menu;
    Button btn_start;
    Button btn_revisitMistakes;
    Button btn_viewStats;
    Button btn_settings;
    Button btn_exit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_revisitMistakes = (Button) findViewById(R.id.btn_revisitMistakes);
        btn_viewStats = (Button) findViewById(R.id.btn_stats);
        btn_settings = (Button)findViewById(R.id.btn_settings);
        btn_exit = (Button)findViewById(R.id.btn_exit);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Question.class);
                startActivity(intent);
            }
        });

        btn_revisitMistakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RevisitMistakesChoose.class);
                startActivity(intent);
            }
        });

        btn_viewStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewStats.class);
                startActivity(intent);
            }
        });

        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        stats mstats = new stats(MainActivity.this);

        if ((!mstats.areMistakesPresent(getResources().getString(R.string.add_operation)))
            && (!mstats.areMistakesPresent(getResources().getString(R.string.subtract_operation)))
            && (!mstats.areMistakesPresent(getResources().getString(R.string.multiply_operation)))){
            //btn_revisitMistakes.setVisibility(View.GONE);
            btn_revisitMistakes.setEnabled(false);
        }


        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setHomeActionContentDescription("BACK");
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.hide();



        //mActionBarView = getLayoutInflater().inflate(R.layout.my_action_bar,null);
        //actionBar.setCustomView(mActionBarView);
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Toast.makeText(this,item.get,Toast.LENGTH_LONG).show();
        switch (item.getItemId()){
            case R.id.mi_settings:
                //Toast.makeText(this,"settings",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,Settings.class);
                startActivity(intent);
                break;
            case R.id.mi_quit:
                //Toast.makeText(this,"quit",Toast.LENGTH_LONG).show();
                this.finishAffinity();
                break;
            case android.R.id.home:
                //Toast.makeText(this,"back: "+ this.getClass().getSimpleName().toString(),Toast.LENGTH_LONG).show();
                switch(this.getClass().getSimpleName().toString()){
                    case "Question":
                        Intent intent_a = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent_a);
                        break;
                    case "RevisitMistakes":
                        Intent intent_b = new Intent(MainActivity.this,RevisitMistakesChoose.class);
                        startActivity(intent_b);
                        break;
                    case "RevisitMistakesChoose":
                        Intent intent_c = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent_c);
                        break;

                }
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
