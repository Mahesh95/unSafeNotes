package com.a1995.mahesh.unsafenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.a1995.mahesh.unsafenotes.database.NotesCursorWrapper;
import com.a1995.mahesh.unsafenotes.database.NotesHelper;
import com.a1995.mahesh.unsafenotes.database.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mahesh on 5/6/16.
 */
public class NotesSingleton {
    private static final String TAG = "NotesSingleton" ;
    private static NotesSingleton sNotesSingleton;
    private Context mContext;
    private List<Note> mNotes;
    private List<Note> mCategorisedNotes;
    private SQLiteDatabase mDatabase;

    public static NotesSingleton get(Context context){
        if(sNotesSingleton == null){
            return new NotesSingleton(context);
        }
        return sNotesSingleton;
    }

    private NotesSingleton(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new NotesHelper(mContext).getWritableDatabase();
    }

    public void addNote(Note note){
        ContentValues values = getContentValues(note);
        Log.i(TAG, "inserted into database date:" + values.get(Schema.NotesTable.Cols.DATE));
        mDatabase.insert(Schema.NotesTable.NAME, null, values);
    }


    private ContentValues getContentValues(Note note){
        ContentValues values = new ContentValues();
        values.put(Schema.NotesTable.Cols.UUID, note.getId().toString());
        values.put(Schema.NotesTable.Cols.CATEGORY, note.getCategory());
        values.put(Schema.NotesTable.Cols.CONTENT, note.getContent());
        values.put(Schema.NotesTable.Cols.DATE, note.getDate().getTime());
        values.put(Schema.NotesTable.Cols.TITLE, note.getTitle());

        return values;
    }

    private NotesCursorWrapper queryDatabase(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(Schema.NotesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                Schema.NotesTable.Cols.DATE + " DESC");

        return new NotesCursorWrapper(cursor);
    }

    public List<Note> getNotes(){
        mNotes = new ArrayList<>();
        NotesCursorWrapper cursor = queryDatabase(null, null); // select all rows

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Note note = cursor.getNote();
                mNotes.add(note);
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return mNotes;
    }

    public List<Note> getNotes(String filterString){
        mNotes = new ArrayList<>();
        String wildCardString = "%"+filterString+"%";
        NotesCursorWrapper cursor = queryDatabase(Schema.NotesTable.Cols.TITLE + " LIKE ?", new String[]{wildCardString});

        try{
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                mNotes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return mNotes;
    }

    public List<Note> getCategorisedNotes(String category){
        mCategorisedNotes = new ArrayList<>();
        NotesCursorWrapper cursor = queryDatabase(Schema.NotesTable.Cols.CATEGORY + "=?", new String[]{category});
        try {
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                mCategorisedNotes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return mCategorisedNotes;
    }

    public Note getNote(UUID id){
        Note note;
        NotesCursorWrapper cursorWrapper = queryDatabase(Schema.NotesTable.Cols.UUID + "=?", new String[]{id.toString()});
        try{
            if(cursorWrapper.getCount() == 0){
                return null;
            }else {
                cursorWrapper.moveToFirst();
                return cursorWrapper.getNote();
            }
        }finally {
            cursorWrapper.close();
        }
    }

    public void updateNote(Note note){
        String uuidString = note.getId().toString();
        ContentValues values = getContentValues(note);
        mDatabase.update(Schema.NotesTable.NAME,values, Schema.NotesTable.Cols.UUID+"=?", new String[]{uuidString});
    }

    public void deleteNote(UUID noteId){
        String tableName = Schema.NotesTable.NAME;
        String whereClause = Schema.NotesTable.Cols.UUID+"=?";
        String[] whereArg = new String[]{noteId.toString()};

        deleteRow(tableName, whereClause, whereArg);
    }

    private void deleteRow(String tableName, String whereClause, String[] whereArg) {
        mDatabase.delete(tableName, whereClause, whereArg);
    }

}


