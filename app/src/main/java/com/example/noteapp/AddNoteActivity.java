package com.example.noteapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity {

    private EditText noteNameEditText;
    private EditText noteContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteNameEditText = findViewById(R.id.noteNameEditText);
        noteContentEditText = findViewById(R.id.noteContentEditText);

        findViewById(R.id.saveNoteButton).setOnClickListener(v -> {
            String noteName = noteNameEditText.getText().toString().trim();
            String noteContent = noteContentEditText.getText().toString().trim();

            if (noteName.isEmpty() || noteContent.isEmpty()) {
                Toast.makeText(AddNoteActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("NotesData", MODE_PRIVATE);
            String notesString = sharedPreferences.getString("notes", "");
            ArrayList<String> notesList = new ArrayList<>();
            if (!notesString.isEmpty()) {
                String[] notesArray = notesString.split(",");
                for (String note : notesArray) {
                    notesList.add(note);
                }
            }

            notesList.add(noteName + ": " + noteContent);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("notes", TextUtils.join(",", notesList));
            editor.apply();

            Toast.makeText(AddNoteActivity.this, "Note added", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
