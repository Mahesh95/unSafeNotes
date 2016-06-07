package com.a1995.mahesh.unsafenotes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.UUID;

/**
 * Created by mahesh on 7/6/16.
 * confirmation dialog on  deleting crime, called from  NoteViewFragment
 */
public class DeleteNoteFragment extends DialogFragment {
    private static final String ARG_CRIME_ID = "crimeId";   //this argument helps identify the note to be deleted

    //this method returns the DeleteNoteFragment stashing crimeId of the crime to be deleted
    //in its argument bundle
    public static DeleteNoteFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        DeleteNoteFragment fragment = new DeleteNoteFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final UUID noteId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.delete_alert_dialog_string)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NotesSingleton.get(getActivity()).deleteNote(noteId);      //this deletes note from database

                        getActivity().finish();
                    }
                })
                .setNegativeButton("NO",null)
                .create();
    }
}
