package com.a1995.mahesh.unsafenotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by mahesh on 4/6/16.
 */
public class AddNoteActivity extends SingleFragmentActivity {
    private static final String TAG = "AddNoteActivity";

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AddNoteActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return AddNoteFragment.newInstance();
    }
}
