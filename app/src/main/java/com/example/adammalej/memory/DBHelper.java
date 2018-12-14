package com.example.adammalej.memory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MemoryDB.db";
    public static final String MEMORY_TABLE = "memories";
    public static final String MEMORY_COLUMN_ID = "id";
    public static final String MEMORY_COLUMN_PATH = "path";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memories " +
            "(id INTEGER PRIMARY KEY, path TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS memories");
        onCreate(db);
    }

    public boolean insertMemory(String path){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", path);
        db.insert("memories", null, contentValues);
        return true;
    }

    public ArrayList<String> getAllMemories(){
        ArrayList<String> memoriesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM memories", null);

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false)
        {
            memoriesList.add(cursor.getString(cursor.getColumnIndex(MEMORY_COLUMN_PATH)));
            cursor.moveToNext();
        }

        return  memoriesList;
    }
}
