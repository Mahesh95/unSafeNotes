package com.a1995.mahesh.unsafenotes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by mahesh on 7/6/16.
 */
public class NoteViewActivity extends SingleFragmentActivity {
    private static final String NOTE_ID = "note_id";

    public static Intent getIntent(Context context, UUID noteId){
        Intent intent = new Intent(context, NoteViewActivity.class);
        intent.putExtra(NOTE_ID, noteId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        UUID noteId = (UUID)getIntent().getSerializableExtra(NOTE_ID);
        return NoteViewFragment.newInstance(noteId);
    }
}
