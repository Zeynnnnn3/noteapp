package com.example.noteapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeleteNoteActivity extends AppCompatActivity {

    private ListView notesListView;
    private ArrayList<String> notesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        notesListView = findViewById(R.id.notesListView);

        SharedPreferences sharedPreferences = getSharedPreferences("NotesData", MODE_PRIVATE);
        String notesString = sharedPreferences.getString("notes", "");
        notesList = new ArrayList<>();
        if (!notesString.isEmpty()) {
            String[] notesArray = notesString.split(",");
            for (String note : notesArray) {
                notesList.add(note);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedNote = notesList.get(position);
            notesList.remove(position);
            adapter.notifyDataSetChanged();

            SharedPreferences sharedPreferences1 = getSharedPreferences("NotesData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("notes", String.join(",", notesList));
            editor.apply();

            Toast.makeText(DeleteNoteActivity.this, "Deleted: " + selectedNote, Toast.LENGTH_SHORT).show();
        });
    }
}
