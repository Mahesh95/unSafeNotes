package com.a1995.mahesh.unsafenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by mahesh on 3/6/16.
 * this fragment is hosted by AddNoteActivity when floating action button is clicked in notesFragment
 */
public class AddNoteFragment extends Fragment {
    private Toolbar mToolbar;
    private Note mNote;
    private EditText mTitleEditText;
    private EditText mContentEditText;
    private RadioGroup mNoteCategoryRadioGroup;

    private static final String TAG = "AddNoteFragment";


    public static AddNoteFragment newInstance(){
        return new AddNoteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        mTitleEditText = (EditText) view.findViewById(R.id.add_note_title_edit_text);
        mContentEditText = (EditText) view.findViewById(R.id.add_note_content_edit_text);

        mToolbar = (Toolbar) view.findViewById(R.id.add_note_fragment_toolbar);
        configToolbar();

        mNoteCategoryRadioGroup = (RadioGroup) view.findViewById(R.id.note_category_radio_group);
        mContentEditText.requestFocus();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mNote = new Note();
    }


    private void configToolbar(){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setTitle("Add Note");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.add_note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.save_note:
                saveNote(mNote);
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void saveNote(Note mNote) {

        if(mContentEditText.getText().length() != 0){
            String content = mContentEditText.getText().toString();

            String title = null;

            if(mTitleEditText.getText().length() == 0){
                if(content.length() < 60){
                    title = content;
                }else{
                    title = content.substring(0,60);
                }
            }else{
                    title = mTitleEditText.getText().toString();
            }


            String category = null;
            switch (mNoteCategoryRadioGroup.getCheckedRadioButtonId()){
                case R.id.work_radio_button:
                    category = "work";
                    break;
                case R.id.personal_radio_button:
                    category = "personal";
                    break;
                case R.id.poetry_radio_button:
                    category = "poetry";
                    break;
            }

            mNote.setTitle(title);
            mNote.setCategory(category);
            mNote.setContent(content);
            mNote.setDate(new Date());

            Log.i(TAG, "saved date is: " + mNote.getDate().toString());

            NotesSingleton.get(getActivity()).addNote(mNote);
        }

        getActivity().finish();
    }


}
