package com.weblancer.dikobrazzz.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tim.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "time"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_CLOCK = "fClock";
    public static final String COLUMN_SECOND_CLOCK = "sClock";
    public static final String COLUMN_FIRST_TIME = "fTime";
    public static final String COLUMN_SECOND_TIME = "sTime";
    public static final String COLUMN_NOTES = "notes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE + " ("+ COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRST_CLOCK + " TEXT ,"
                + COLUMN_SECOND_CLOCK + " TEXT ,"
                + COLUMN_FIRST_TIME + " TEXT ," + COLUMN_SECOND_TIME
                + " TEXT, " + COLUMN_NOTES + " TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
