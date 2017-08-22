package com.mathmash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Home on 9/13/2016.
 */
public class stats
{

    private static final String TAG = "stats";
    private int num1;
    private int num2;
    private String operation;
    private int ans;
    private int isCorrect;
    private String datetime;
    private Context mcontext;
    private stats_db mydb;
    private stats mystats;

    public stats(Context context){
        this.mcontext = context;
        mydb = stats_db.getInstance(mcontext);
    }

    public stats(int num1, int num2, String operation, int ans, int isCorrect, String datetime) {
        this.num1 = num1;
        this.num2 = num2;
        this.operation = operation;
        this.ans = ans;
        this.isCorrect = isCorrect;
        this.datetime = datetime;
    }

    public void addstats(int num1, int num2, String operation, int ans, int isCorrect, String datetime) {
        this.num1 = num1;
        this.num2 = num2;
        this.operation = operation;
        this.ans = ans;
        this.isCorrect = isCorrect;
        this.datetime = datetime;

        //write to db
        SQLiteDatabase db = mydb.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(dbhelper.stats.COLUMN_NUM1,num1);
        val.put(dbhelper.stats.COLUMN_NUM2,num2);
        val.put(dbhelper.stats.COLUMN_OPERATION,operation);
        val.put(dbhelper.stats.COLUMN_ANS,ans);
        val.put(dbhelper.stats.COLUMN_ISCORRECT,isCorrect);
        val.put(dbhelper.stats.COLUMN_DATETIME,datetime);
        Long i = db.insert(dbhelper.stats.TABLE_NAME,null,val);

        Cursor csr = db.rawQuery("select * from "+dbhelper.stats.TABLE_NAME+" where "+dbhelper.stats.COLUMN_ID+" = " + i,null);
        csr.moveToFirst();
        Log.d(TAG,"just inserted id: "+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_ID))));
        Log.d(TAG,"just inserted num1: "+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_NUM1))));
        Log.d(TAG,"just inserted num2: "+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_NUM2))));
        Log.d(TAG,"just inserted opearation: "+String.valueOf(csr.getString(csr.getColumnIndex(dbhelper.stats.COLUMN_OPERATION))));
        Log.d(TAG,"just inserted ans: "+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_ANS))));
        Log.d(TAG,"just inserted iscorrect: "+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_ISCORRECT))));
        Log.d(TAG,"just inserted datetime: "+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_DATETIME))));
        Log.d(TAG,"____________________________________");
        csr.close();
    }

    public Boolean areMistakesPresent(String operation){
        SQLiteDatabase db = mydb.getWritableDatabase();
        Cursor csr = db.rawQuery("select "+dbhelper.stats.COLUMN_ID+","+dbhelper.stats.COLUMN_NUM1+", "+dbhelper.stats.COLUMN_NUM2+" from "+dbhelper.stats.TABLE_NAME+" where "+dbhelper.stats.COLUMN_ISCORRECT + " = '0' and "+dbhelper.stats.COLUMN_OPERATION+ " = '" + operation+"'", null);
        csr.moveToFirst();
        csr.close();
        return (csr.getCount() > 0);
    }

    public full_question get_a_mistake(String operation){
        SQLiteDatabase db = mydb.getWritableDatabase();
        Cursor csr = db.rawQuery("select "+dbhelper.stats.COLUMN_ID+","+dbhelper.stats.COLUMN_NUM1+", "+dbhelper.stats.COLUMN_NUM2+" from "+dbhelper.stats.TABLE_NAME+" where "+dbhelper.stats.COLUMN_ISCORRECT + " = '0' and "+dbhelper.stats.COLUMN_OPERATION+ " = '" + operation+"'", null);
        full_question full_question = new full_question();
        csr.moveToFirst();
        while (csr.moveToNext()){
            Log.d(TAG,"get_a_mistake - all ids:"+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_ID))));
        }

        if (csr.moveToFirst()) {
            Random r = new Random();
            int index = r.nextInt(csr.getCount());
            csr.moveToPosition(index);

            full_question.setId(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_ID)));
            full_question.setNum1(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_NUM1)));
            full_question.setNum2(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_NUM2)));
            full_question.setOperation(operation);
            csr.close();
        }
        else{
            full_question.setId(-1);
            full_question.setNum1(0);
            full_question.setNum2(0);
            full_question.setOperation("");
        }
            return full_question;
    }

    public int updateMistake(int id){
        SQLiteDatabase db = mydb.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(dbhelper.stats.COLUMN_ISCORRECT,"1");
        return db.update(dbhelper.stats.TABLE_NAME,val,dbhelper.stats.COLUMN_ID + "=?",new String[] {String.valueOf(id)});
        //db.update("YOUR_TABLE", newValues, "id=6", null);
    }

    public List<stats> getAllStats(){
        List<stats> mylist = new ArrayList<stats>();
        SQLiteDatabase db = mydb.getWritableDatabase();
        Cursor csr = db.rawQuery("select * from "+dbhelper.stats.TABLE_NAME, null);
        csr.moveToFirst();
        while (csr.moveToNext()){
            //mystats = new stats(csr.getInt(0),csr.getInt(1),csr.getInt(2), csr.getString(3), csr.getString(4),csr.getString(5), csr.getString(6));
            Log.d(TAG,"id:"+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_ID))));
            Log.d(TAG,"num1:"+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_NUM1))));
            Log.d(TAG,"num2:"+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_NUM2))));
            Log.d(TAG,"operation:"+csr.getString(csr.getColumnIndex(dbhelper.stats.COLUMN_OPERATION)));
            Log.d(TAG,"ans:"+String.valueOf(csr.getInt(csr.getColumnIndex(dbhelper.stats.COLUMN_ANS))));
            Log.d(TAG,"correct? ->:"+csr.getString(csr.getColumnIndex(dbhelper.stats.COLUMN_ISCORRECT)));
            //Log.d(TAG,String.valueOf(csr.getInt(csr.getColumnIndex("_id"))));
            //mylist.add(mystats);
        }
        csr.close();
        db.close();
        return mylist;
    }

    public Cursor getCorrect(String operation){
        SQLiteDatabase db = mydb.getWritableDatabase();
        return db.rawQuery("select distinct 'id' as _id,"+dbhelper.stats.COLUMN_NUM1+", "+dbhelper.stats.COLUMN_OPERATION+", "+dbhelper.stats.COLUMN_NUM2+","+dbhelper.stats.COLUMN_ANS+" from "+dbhelper.stats.TABLE_NAME+" where "+dbhelper.stats.COLUMN_ISCORRECT + " = '1' and "+dbhelper.stats.COLUMN_OPERATION+ " = '" + operation+"'", null);
    }

    public Cursor getInCorrect(String operation){
        SQLiteDatabase db = mydb.getWritableDatabase();
        return db.rawQuery("select distinct 'id' as _id, "+dbhelper.stats.COLUMN_NUM1+", "+dbhelper.stats.COLUMN_OPERATION+", "+dbhelper.stats.COLUMN_NUM2+","+dbhelper.stats.COLUMN_ANS+" from "+dbhelper.stats.TABLE_NAME+" where "+dbhelper.stats.COLUMN_ISCORRECT + " = '0' and "+dbhelper.stats.COLUMN_OPERATION+ " = '" + operation+"'", null);

    }


}
