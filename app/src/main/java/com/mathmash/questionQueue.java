package com.mathmash;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Home on 10/9/2016.
 */
public class questionQueue extends ArrayList<full_question> {
    private static questionQueue q;
    full_question fq;
    private static final String TAG = "questionQueue";

    private questionQueue() {

    }

    public static questionQueue getInstance(){
        if (q == null ){
            q = new questionQueue();
            for(full_question temp_fq:q){
                Log.d(TAG,"CREATING NEW QUWUEU -->num1: "+temp_fq.getNum1()+ "num2: "+temp_fq.getNum2());
            }
        }
        return q;
    }
    public boolean isQuestionRepeating(int num1, int num2){
        for(full_question temp_fq:q){
            if ((temp_fq.getNum1() == num1 && temp_fq.getNum2() == num2) ||
                    (temp_fq.getNum2() == num1 && temp_fq.getNum1() == num2)){
                return true;
            }
        }
        return false;
    }

    public void addToQuestionQueue(int num1, int num2, int repeatingLimit){
        Log.d(TAG,"added to question queue. limit:"+repeatingLimit+" cuurrent size: "+q.size());
        if (q.size() >= repeatingLimit){
            q.remove(0);
        }
        fq = new full_question();
        fq.setNum1(num1);
        fq.setNum2(num2);
        q.add(fq);
        for(full_question temp_fq:q){
            Log.d(TAG,"num1: "+temp_fq.getNum1()+ "num2: "+temp_fq.getNum2());
        }
    }


}
