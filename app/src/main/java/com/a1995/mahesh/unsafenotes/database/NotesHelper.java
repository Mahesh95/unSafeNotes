package com.a1995.mahesh.unsafenotes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mahesh on 2/6/16.
 */
public class NotesHelper extends SQLiteOpenHelper {
    private static final String NAME = "notes.db";
    private static final int VERSION = 1;

    public NotesHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + Schema.NotesTable.NAME
        + "( _id integer primary key autoincrement, " +
                Schema.NotesTable.Cols.UUID + "," +
                Schema.NotesTable.Cols.TITLE + "," +
                Schema.NotesTable.Cols.CATEGORY + ","+
                Schema.NotesTable.Cols.DATE + "," +
                Schema.NotesTable.Cols.CONTENT + ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
