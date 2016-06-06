package com.a1995.mahesh.unsafenotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mahesh on 1/6/16.
 */
public class NotesFragment extends Fragment {
    private RecyclerView mNotesRecyclerView;
    private FloatingActionButton mFab;
    private NotesAdapter mAdapter;
    private static final String ARG_NOTES_CATEGORY = "notes_category";

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

    private void UpdateUI() {
        String category = getArguments().getSerializable(ARG_NOTES_CATEGORY).toString();

        List<Note> notes = NotesSingleton.get(getActivity()).getCategorisedNotes(category);

        if(mAdapter == null){
            mAdapter = new NotesAdapter(notes);
            mNotesRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setNotes(notes);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class NotesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
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
            String dateFormat = "YYYY, MMM dd";
            String dateString = DateFormat.format(dateFormat, note.getDate()).toString();
            mNotesDateTextView.setText(dateString);
            mNotesContentTextView.setText(note.getHintString()+"....");
            mNotesTitleTextView.setText
                    (note.getTitle().length() < 26 ? note.getTitle() : note.getTitle().substring(0,24));
        }

        @Override
        public void onClick(View view) {

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
