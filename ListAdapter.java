package com.example.task51c_subtask2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<String> {

    // Custom list adapter class to display task list
    public ListAdapter(@NonNull Context context, ArrayList<String> playlistArrayList){
        super(context, R.layout.playlist_item_layout, playlistArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent){
        // Retrieve URL for given position
        String playlistItem = getItem(position);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.playlist_item_layout, parent, false);
        }

        // Populate display with playlist URL
        TextView URL = view.findViewById(R.id.playlistURL);
        URL.setText(playlistItem);

        return view;
    }
}
