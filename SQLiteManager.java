package com.example.task51c_subtask2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {
    // Custom class to set up SQLite database for persistent user and playlist data storage
    private static SQLiteManager db;
    // Set database details
    private static final String DATABASE_NAME = "iTubeDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String TABLE2_NAME = "playlists";

    // Set database fields
    private static final String USER_ID_FIELD = "userId";
    private static final String USERNAME_FIELD = "username";
    private static final String PASSWORD_FIELD = "password";
    private static final String PLAYLIST_ID_FIELD = "playlistId";
    private static final String PLAYLIST_USER_FIELD = "playlistUser";
    private static final String URL_FIELD = "url";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        // Return database instance, creating a new database on first call
        if(db == null){
            db = new SQLiteManager(context);
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create database tables
        StringBuilder createUserTable;
        createUserTable = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append(" (")
                .append(USER_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(USERNAME_FIELD)
                .append(" TEXT, ")
                .append(PASSWORD_FIELD)
                .append(" TEXT)");

        db.execSQL(createUserTable.toString());

        StringBuilder createPlaylistTable;
        createPlaylistTable = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE2_NAME)
                .append(" (")
                .append(PLAYLIST_ID_FIELD)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(PLAYLIST_USER_FIELD)
                .append(" TEXT, ")
                .append(URL_FIELD)
                .append(" TEXT)");

        db.execSQL(createPlaylistTable.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Standard function to recreate table on upgrade
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        onCreate(db);
    }

    public void addUserToDatabase(String username, String password){
        // Add new user to users table
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME_FIELD, username);
        contentValues.put(PASSWORD_FIELD, password);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void addURLToDatabase(String username, String url){
        // Add playlist URL to playlists table
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PLAYLIST_USER_FIELD, username);
        contentValues.put(URL_FIELD, url);

        db.insert(TABLE2_NAME, null, contentValues);
    }

    public ArrayList<String> populatePlaylistArray(String username){
        // Populate users playlist from database
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<String> playlist = new ArrayList<>();

        try (Cursor result = db.rawQuery("SELECT " + URL_FIELD + " FROM " + TABLE2_NAME + " WHERE " + PLAYLIST_USER_FIELD + " = '" + username + "'", null)){
            if(result.getCount() > 0){
                while (result.moveToNext()){
                    playlist.add(result.getString(0));
                }
            }
        }

        return playlist;
    }

    public int validateUser(String username, String password){
        // Check if username/password combination is in the database
        SQLiteDatabase db = this.getReadableDatabase();

        int match;

        try (Cursor result = db.rawQuery("SELECT " + USER_ID_FIELD + " FROM " + TABLE_NAME + " WHERE " + USERNAME_FIELD + " = '" + username + "' AND " + PASSWORD_FIELD + " = '" + password + "'", null)){
            match = result.getCount();
        }

        return match;
    }

    public int checkUsername(String username){
        // Query database to see if username already in use
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor result = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME_FIELD + " = '" + username + "'", null)){
            return result.getCount();
        }
    }
}
