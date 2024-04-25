package com.example.task51c_subtask2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomePage extends AppCompatActivity {
    // Declare variables
    Intent videoPlayer;
    String currentUser;
    EditText url;
    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        // Get username passed from login
        currentUser = getIntent().getStringExtra("username");

        // Link variables to UI elements
        url = findViewById(R.id.enterURL);
        welcomeText = findViewById(R.id.welcomeText);

        // Set welcome message
        welcomeText.setText("Welcome back " + currentUser + "!");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void playVideo(View view){
        // Retrieve URL
        String urlToPlay = url.getText().toString();

        if (urlToPlay.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter URL", Toast.LENGTH_LONG).show();
        } else{
            // Launch video player
            videoPlayer = new Intent(this, VideoPlayer.class);
            videoPlayer.putExtra("URL", urlToPlay);
            url.setText("");
            startActivity(videoPlayer);
        }
    }

    public void addToPlaylist(View view){
        // Retrieve URL
        String urlToAdd = url.getText().toString();

        if (urlToAdd.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Enter URL", Toast.LENGTH_LONG).show();
        } else {
            // Initialise and add to database
            SQLiteManager db = SQLiteManager.instanceOfDatabase(this);
            db.addURLToDatabase(currentUser, urlToAdd);

            // Clear entry box and display success message
            url.setText("");
            Toast.makeText(getApplicationContext(), "URL added to playlist", Toast.LENGTH_LONG).show();
        }
    }

    public void openPlaylist(View view){
        // Open playlist
        Intent openPlaylist = new Intent(this, Playlist.class);
        openPlaylist.putExtra("username", currentUser);
        startActivity(openPlaylist);
    }

    public void logout(View view){
        //Return to login screen
        finish();
    }
}