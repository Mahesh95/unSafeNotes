package com.a1995.mahesh.unsafenotes.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.a1995.mahesh.unsafenotes.Note;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mahesh on 5/6/16.
 */
public class NotesCursorWrapper extends CursorWrapper {
    public NotesCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        String uuidString = getString(getColumnIndex(Schema.NotesTable.Cols.UUID));
        String title = getString(getColumnIndex(Schema.NotesTable.Cols.TITLE));
        String content = getString(getColumnIndex(Schema.NotesTable.Cols.CONTENT));
        long date = getLong(getColumnIndex(Schema.NotesTable.Cols.DATE));
        String category = getString(getColumnIndex(Schema.NotesTable.Cols.CATEGORY));

        Note note = new Note(UUID.fromString(uuidString));
        note.setCategory(category);
        note.setContent(content);
        note.setDate(new Date(date));
        note.setTitle(title);

        return note;
    }
}
