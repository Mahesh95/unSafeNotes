package com.a1995.mahesh.unsafenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mahesh on 1/6/16.
 * this fragment is hosted by the main activity
 * it uses a recyclerView with GridLayoutManager(2 columns) to show the note items
 */
public class NotesFragment extends Fragment {
    private static final String TAG = "NotesFragment";
    private RecyclerView mNotesRecyclerView;
    private FloatingActionButton mFab;          //floating action button for adding notes
    private NotesAdapter mAdapter;
    private static final String ARG_NOTES_CATEGORY = "notes_category"; //this argument specifies which category notes are to be displayed

    //this method returns an instance of notesFragment along with category of the notes stashed in its arguments
    public static NotesFragment newInstance(String notesCategory){
        Bundle args = new Bundle();
        args.putSerializable(ARG_NOTES_CATEGORY, notesCategory);
        NotesFragment fragment = new NotesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        mFab = (FloatingActionButton) view.findViewById(R.id.add_note);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteActivity.getIntent(getActivity());
                startActivity(intent);
            }
        });

        mNotesRecyclerView = (RecyclerView) view.findViewById(R.id.notes_recycler_view);
        mNotesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        UpdateUI();
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        UpdateUI();
    }

    //this method updates the UI with note items
    private void UpdateUI() {
        String category = getArguments().getSerializable(ARG_NOTES_CATEGORY).toString();

        // acquiring a list of notes depending upon the category
        List<Note> notes = NotesSingleton.get(getActivity()).getCategorisedNotes(category);

        //setting up adapter
        if(mAdapter == null){
            mAdapter = new NotesAdapter(notes);
            mNotesRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setNotes(notes);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class NotesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Note mNote;
        private TextView mNotesTitleTextView;
        private TextView mNotesDateTextView;
        private TextView mNotesContentTextView;

        public NotesHolder(View itemView) {
            super(itemView);

            mNotesTitleTextView = (TextView) itemView.findViewById(R.id.notes_recycler_view_item_title);
            mNotesContentTextView = (TextView) itemView.findViewById(R.id.notes_recycler_view_item_content);
            mNotesDateTextView = (TextView) itemView.findViewById(R.id.notes_recycler_view_item_date);
            itemView.setOnClickListener(this);
        }

        public void bindNote(Note note){
            mNote = note;
            String dateFormat = "MMM dd";
            String dateString = DateFormat.format(dateFormat, note.getDate()).toString();

            Log.i(TAG,"date is :" + mNote.getDate().toString());
            mNotesDateTextView.setText(dateString);
            mNotesContentTextView.setText(note.getHintString()+"....");
            mNotesTitleTextView.setText
                    (note.getTitle().length() < 26 ? note.getTitle() : note.getTitle().substring(0,24));
        }

        @Override
        public void onClick(View view) {
            Intent intent = NoteViewActivity.getIntent(getActivity(), mNote.getId());
            startActivity(intent);
        }
    }

    private class NotesAdapter extends RecyclerView.Adapter<NotesHolder>{
        private List<Note> mNotes;

        public NotesAdapter(List<Note> notes){
            mNotes = notes;
        }

        @Override
        public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.notes_recycler_view_item,parent,false);
            return new NotesHolder(view);
        }

        @Override
        public void onBindViewHolder(NotesHolder holder, int position) {
            Note note = mNotes.get(position);
            holder.bindNote(note);
        }

        @Override
        public int getItemCount() {
            if(mNotes == null){
                return 0;
            }else{
                return mNotes.size();
            }

        }

        public void setNotes(List<Note> notes) {
            this.mNotes = notes;
        }
    }

}
