package com.mathmash;

import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Home on 9/27/2016.
 */
public class stats_details_gv extends DialogFragment {
    static Boolean isItAList = false;

    public static stats_details_gv newInstance(String operation, Boolean isCorrect){
        stats_details_gv frag = new stats_details_gv();
        Bundle b = new Bundle();
        b.putString("operation",operation);
        b.putBoolean("isCorrect",isCorrect);
        frag.setArguments(b);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        stats mystats = new stats(getActivity());
        String operation = getArguments().getString("operation");
        Boolean isCorrect = getArguments().getBoolean("isCorrect");
        Cursor my_crsr = null;
        if (isCorrect) {
                if (operation.equals(getResources().getString(R.string.add_operation))){
                        my_crsr = mystats.getCorrect(getResources().getString(R.string.add_operation));
                }
                else if (operation.equals(getResources().getString(R.string.subtract_operation))) {
                        my_crsr = mystats.getCorrect(getResources().getString(R.string.subtract_operation));
                }
                else if (operation.equals(getResources().getString(R.string.multiply_operation))){
                        my_crsr = mystats.getCorrect(getResources().getString(R.string.multiply_operation));
                }
        }else {
                if (operation.equals(getResources().getString(R.string.add_operation))){
                    my_crsr = mystats.getInCorrect(getResources().getString(R.string.add_operation));
                }
                else if (operation.equals(getResources().getString(R.string.subtract_operation))) {
                    my_crsr = mystats.getInCorrect(getResources().getString(R.string.subtract_operation));
                }
                else if (operation.equals(getResources().getString(R.string.multiply_operation))){
                    my_crsr = mystats.getInCorrect(getResources().getString(R.string.multiply_operation));
                }
        }

        View v = inflater.inflate(R.layout.layout_stats_details_gv,container,false);

        //Cursor my_crsr = mystats.getCorrect(getResources().getString(R.string.add_operation));
        GridView gv = (GridView) v.findViewById(R.id.stats_details_gv);
        ListView lv = (ListView) v.findViewById(R.id.stats_details_lv);
        Button close_btn = (Button) v.findViewById(R.id.close_btn);





        if (my_crsr.getCount() > 5) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),R.layout.stats_details_tv,
                    my_crsr,
                    new String[] {dbhelper.stats.COLUMN_NUM1, dbhelper.stats.COLUMN_OPERATION, dbhelper.stats.COLUMN_NUM2},
                    new int[] {R.id.stats_details_tvnum1,R.id.stats_details_operation, R.id.stats_details_tvnum2},
                    0);
            isItAList = false;
            gv.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            gv.setAdapter(adapter);
        } else {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),R.layout.stats_details_tv_list,
                    my_crsr,
                    new String[] {dbhelper.stats.COLUMN_NUM1, dbhelper.stats.COLUMN_OPERATION, dbhelper.stats.COLUMN_NUM2},
                    new int[] {R.id.stats_details_tvnum1,R.id.stats_details_operation, R.id.stats_details_tvnum2},
                    0);
            isItAList = true;
            lv.setVisibility(View.VISIBLE);
            gv.setVisibility(View.GONE);
            lv.setAdapter(adapter);
        }

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();

        if (isItAList) {
            // retrieve display dimensions
            /*Rect displayRectangle = new Rect();
            Window window = getDialog().getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
            params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, displayRectangle.width()*0.15f, getResources().getDisplayMetrics());*/
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        else {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }
}
