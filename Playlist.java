package com.example.task51c_subtask2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Playlist extends AppCompatActivity {
    // Declare variables
    ListView playlistListView;
    ListAdapter listAdapter;
    SQLiteManager db;
    String currentUser;
    ArrayList<String> playlist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_playlist);

        // Retrieve username sent from home page
        currentUser = getIntent().getStringExtra("username");

        // Initialise database
        db = SQLiteManager.instanceOfDatabase(this);

        // Populate playlist from database
        playlist = db.populatePlaylistArray(currentUser);

        // Link variables to UI elements
        playlistListView = findViewById(R.id.listView);

        // Set up list view adapter and on click listener
        setTaskAdapter();
        setOnClickListener();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setTaskAdapter() {
        // Set list adapter to display elements of playlist
        listAdapter = new ListAdapter(this, playlist);
        playlistListView.setAdapter(listAdapter);
    }

    private void setOnClickListener() {
        // Define on click action for playlist
        playlistListView.setClickable(true);

        playlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve clicked playlist url
                String URL = playlist.get(position);

                Intent videoPlayer = new Intent(getApplicationContext(), VideoPlayer.class);
                videoPlayer.putExtra("URL", URL);
                startActivity(videoPlayer);
            }
        });
    }

    public void closePlaylist(View view){
        // Return to home page
        finish();
    }
}