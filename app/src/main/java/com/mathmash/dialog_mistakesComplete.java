package com.mathmash;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Window;

/**
 * Created by Home on 9/24/2016.
 */
public class dialog_mistakesComplete extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
    }

    @NonNull
    @Override
    public android.support.v7.app.AlertDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dlg= new AlertDialog.Builder(getActivity())
                    .setTitle("Congratulations !!!")
                    .setMessage("Congratulations!!! You have revisited and corrected all the mistakes for this operation.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        stats mstats = new stats(getActivity());
                        if (!mstats.areMistakesPresent(getResources().getString(R.string.add_operation))
                                && (!mstats.areMistakesPresent(getResources().getString(R.string.subtract_operation)))
                                && (!mstats.areMistakesPresent(getResources().getString(R.string.multiply_operation)))
                                ){
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(getActivity(), RevisitMistakesChoose.class);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog dialog = dlg.create();
        //getDialog().setTitle("Details");
        //dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        return dialog;
    }
}
