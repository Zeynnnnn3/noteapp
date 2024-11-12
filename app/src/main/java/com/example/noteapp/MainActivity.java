package com.example.noteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView notesListView;
    private ArrayList<String> notesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        findViewById(R.id.addNoteButton).setOnClickListener(v -> {
            Intent addNoteIntent = new Intent(MainActivity.this, AddNoteActivity.class);
            startActivity(addNoteIntent);
        });

        findViewById(R.id.deleteNoteButton).setOnClickListener(v -> {
            Intent deleteNoteIntent = new Intent(MainActivity.this, DeleteNoteActivity.class);
            startActivity(deleteNoteIntent);
        });
    }

    public void updateNotesList() {
        SharedPreferences sharedPreferences = getSharedPreferences("NotesData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notes", String.join(",", notesList));
        editor.apply();
    }
}
