package com.weblancer.dikobrazzz.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID,DatabaseHelper.COLUMN_FIRST_CLOCK, DatabaseHelper.COLUMN_SECOND_CLOCK, DatabaseHelper.COLUMN_FIRST_TIME, DatabaseHelper.COLUMN_SECOND_TIME, DatabaseHelper.COLUMN_NOTES};
        return  database.query(DatabaseHelper.TABLE, columns, null, null, null, null, null);
    }

    public ArrayList<Table> getUsers(){
        ArrayList<Table> tables = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String fClock = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRST_CLOCK));
                String sClock = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SECOND_CLOCK));
                String fTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRST_TIME));
                String sTime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SECOND_TIME));
                String note= cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOTES));
                tables.add(new Table(id,fClock, sClock, fTime, sTime, note));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return tables;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public String getNote(long id){
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        String note = "dd";
        if(cursor.moveToFirst()){
            note = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOTES));
        }
        cursor.close();
        return note;
    }

    public long insert(Table table){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_FIRST_CLOCK, table.getFirstClock());
        cv.put(DatabaseHelper.COLUMN_SECOND_CLOCK, table.getSecondClock());
        cv.put(DatabaseHelper.COLUMN_FIRST_TIME, table.getFirstTimer());
        cv.put(DatabaseHelper.COLUMN_SECOND_TIME, table.getSecondTimer());
        cv.put(DatabaseHelper.COLUMN_NOTES, table.getNotes());
        return database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long clear(){
        return database.delete(DatabaseHelper.TABLE, null, null);
    }

    public long delete(long Id){

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(Id)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public int update(Table table){

        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(table.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NOTES, table.getNotes());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }
}

//send_file @dikobrazzz /home/dikobraz/AndroidStudioProjects/MyApplication2/app/build/outputs/apk/debug/app-debug.apk

