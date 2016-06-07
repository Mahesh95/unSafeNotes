package com.a1995.mahesh.unsafenotes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mahesh on 7/6/16.
 */
public class NoteViewFragment extends Fragment {
    private static final String ARG_NOTE_ID = "note_id";
    private static final String TAG = "NoteViewFragment";
    private static final String DELETE_NOTE_DIALOG = "deleteNoteDialog";

    private EditText mTitleEditText;
    private EditText mContentEditText;
    private RadioGroup mRadioGroup;
    private Note mNote;
    private Toolbar mToolbar;
    private Menu mMenu;
    private RadioButton mWorkButton;
    private RadioButton mPersonalButton;
    private RadioButton mPoetryButton;

    public static NoteViewFragment newInstance(UUID noteId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTE_ID, noteId);
        NoteViewFragment fragment = new NoteViewFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        UUID noteId = (UUID) getArguments().getSerializable(ARG_NOTE_ID);
        mNote = NotesSingleton.get(getActivity()).getNote(noteId);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_note_view, parent, false);
        mTitleEditText = (EditText) view.findViewById(R.id.fragment_note_view_title_edit_text);
        mContentEditText = (EditText) view.findViewById(R.id.fragment_note_view_content_edit_text);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.fragment_note_view_note_category_radio_group);
        mWorkButton = (RadioButton) view.findViewById(R.id.fragment_note_view_work_radio_button);
        mPersonalButton = (RadioButton) view.findViewById(R.id.fragment_note_view_personal_radio_button);
        mPoetryButton = (RadioButton) view.findViewById(R.id.fragment_note_view_poetry_radio_button);

        mToolbar = (Toolbar) view.findViewById(R.id.fragment_note_view_toolbar);
        configToolbar();

        return view;
    }


    private void configToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.note_view_menu, menu);
        mMenu = menu;
        readMode();

        Log.i(TAG, "options menu created");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.note_view_menu_delete_note:
                DeleteNoteFragment dialog = DeleteNoteFragment.newInstance(mNote.getId());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                dialog.show(fragmentManager, DELETE_NOTE_DIALOG);
                return true;

            case R.id.note_view_menu_edit_note:
                editMode();
                return true;

            case R.id.note_view_menu_discard:
                readMode();
                return true;

            case R.id.note_view_menu_save_changes:
                saveChanges();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveChanges() {
        String title = mTitleEditText.getText().toString();
        String content = mContentEditText.getText().toString();
        String category = null;

        switch (mRadioGroup.getCheckedRadioButtonId()){
            case R.id.fragment_note_view_work_radio_button:
                category = "work";
                break;
            case R.id.fragment_note_view_poetry_radio_button:
                category = "poetry";
                break;
            case R.id.fragment_note_view_personal_radio_button:
                category = "personal";
                break;
        }

        Date date = new Date();

        mNote.setDate(date);
        mNote.setCategory(category);
        mNote.setContent(content);
        mNote.setTitle(title);

        NotesSingleton.get(getActivity()).updateNote(mNote);
        readMode();
    }

    private void editMode() {
        mRadioGroup.setVisibility(View.VISIBLE);
        mContentEditText.setEnabled(true);
        mTitleEditText.setEnabled(true);
        mContentEditText.requestFocus();

        String category = mNote.getCategory();
        switch (category){
            case "work":
                mWorkButton.setChecked(true);
                break;
            case "personal":
                mPersonalButton.setChecked(true);
                break;
            case "poetry":
                mPoetryButton.setChecked(true);
                break;
        }

        if(mMenu != null){
            mMenu.findItem(R.id.note_view_menu_edit_note).setVisible(false);
            mMenu.findItem(R.id.note_view_menu_discard).setVisible(true);
            mMenu.findItem(R.id.note_view_menu_save_changes).setVisible(true);
        }

    }

    private void readMode() {

        mTitleEditText.setText(mNote.getTitle());
        mContentEditText.setText(mNote.getContent());

        mContentEditText.setEnabled(false);
        mTitleEditText.setEnabled(false);
        mRadioGroup.setVisibility(View.GONE);

        if(mMenu != null){
            mMenu.findItem(R.id.note_view_menu_edit_note).setVisible(true);
            mMenu.findItem(R.id.note_view_menu_discard).setVisible(false);
            mMenu.findItem(R.id.note_view_menu_save_changes).setVisible(false);
        }


    }

}
