package com.colinchartier.overture.app.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.colinchartier.overture.app.playlist.PlaylistData;
import com.colinchartier.overture.app.playlist.PlaylistDatabaseHelper;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DefaultDatabaseHelper extends SQLiteOpenHelper implements PlaylistDatabaseHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Overture.db";

    public DefaultDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Playlist(" +
                "_id INTEGER PRIMARY KEY NOT NULL, " +
                "name TEXT NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS Playlist_Song(" +
                "playlist_id INTEGER NOT NULL, " +
                "song_id INTEGER NOT NULL, " +
                "PRIMARY KEY (playlist_id, song_id), " +
                "FOREIGN KEY (playlist_id) REFERENCES Playlist(_id)" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private Collection<Long> getPlaylistSongIds(int playlistId) {
        List<Long> ret = new ArrayList<Long>();
        Cursor cursor = getReadableDatabase().query(
                "Playlist_Song", //Table name
                new String[]{"song_id"}, //Columns to return
                "playlist_id=?", //Selection columns
                new String[]{String.valueOf(playlistId)}, //Selection values
                null, //Group by
                null, //Filter
                null //Sort by
        );
        try {
            int songIdColumn = cursor.getColumnIndexOrThrow("song_id");
            while (cursor.moveToNext()) {
                long songId = cursor.getLong(songIdColumn);
                ret.add(songId);
            }
        } finally {
            cursor.close();
        }
        return ret;
    }

    @Override
    public void setPlaylistSongs(String name, Collection<Long> songList) {
        try {
            getWritableDatabase().beginTransaction();
            getWritableDatabase().delete("Playlist_Song", "playlist_id=(SELECT _id FROM Playlist WHERE name=?)", new String[]{name});
            SQLiteStatement pst = getWritableDatabase().compileStatement("INSERT INTO Playlist_Song(playlist_id, song_id) VALUES((SELECT _id FROM Playlist WHERE name=?), ?)");
            pst.bindString(1, name);
            for (Long l : songList) {
                pst.bindLong(2, l);
            }
            pst.close();

            getWritableDatabase().setTransactionSuccessful();
        } finally {
            getWritableDatabase().endTransaction();
        }
    }

    @Override
    public List<Long> getPlaylistSongs(String playlistName) {
        List<Long> ret = new ArrayList<Long>();
        Cursor cursor = getReadableDatabase().query(
                "Playlist_Song", //Table name
                new String[]{"song_id"}, //Columns to return
                "playlist_id=(SELECT _id FROM Playlist WHERE name=?)", //Selection columns
                new String[]{playlistName}, //Selection values
                null, //Group by
                null, //Filter
                null  //Sort by
        );
        try {
            int songIdColumn = cursor.getColumnIndexOrThrow("song_id");
            while (cursor.moveToNext()) {
                ret.add(cursor.getLong(songIdColumn));
            }
        } finally {
            cursor.close();
        }
        return ret;
    }

    @Override
    public List<PlaylistData> getAllPlaylists() {
        List<PlaylistData> ret = new ArrayList<PlaylistData>();
        Cursor cursor = getReadableDatabase().query(
                "Playlist", //Table name
                new String[]{"name", "_id"}, //Columns to return
                null, //Selection columns
                null, //Selection values
                null, //Group by
                null, //Filter
                "name DESC" //Sort by name, descending
        );
        try {
            int nameColumn = cursor.getColumnIndexOrThrow("name");
            int uidColumn = cursor.getColumnIndexOrThrow("_id");
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumn);
                int uid = cursor.getInt(uidColumn);
                PlaylistData p = new PlaylistData(name, ImmutableList.copyOf(getPlaylistSongIds(uid)));
                ret.add(p);
            }
        } finally {
            cursor.close();
        }
        return ret;
    }

    @Override
    public void createPlaylist(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        getWritableDatabase().insert("Playlist", null, values);
    }

    @Override
    public void deletePlaylist(String name) {
        getWritableDatabase().delete(
                "Playlist_Song",
                "playlist_id=(SELECT _id FROM Playlist WHERE name=?)",
                new String[]{name}
        );
        getWritableDatabase().delete(
                "Playlist",
                "name=?",
                new String[]{name}
        );
    }
}
