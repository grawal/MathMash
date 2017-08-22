package com.mathmash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Home on 9/13/2016.
 */
public class stats_db extends SQLiteOpenHelper {

    private static stats_db sInstance;

    public static synchronized stats_db getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new stats_db(context.getApplicationContext());
        }
        return sInstance;
    }

    private stats_db(Context context){
        super(context,dbhelper.DATABASE_NAME,null,dbhelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(dbhelper.stats.CREATE_TABLE_STATS);
        Log.d("stats_db","creating table" + dbhelper.stats.CREATE_TABLE_STATS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS " + dbhelper.stats.TABLE_NAME + ";");
            onCreate(db);
        }
    }







}
