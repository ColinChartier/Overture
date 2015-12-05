package com.colinchartier.overture.app.song;

/**
 * This is a simple song class, with the id provided by the android content provider, and other fields populated in a human-friendly way.
 * <br>
 * Created by overcaste on 4/4/2015.
 */
public class Song {
    private final long id;
    private final String title;
    private final String artist;
    private final String album;

    public Song(long id, String title, String artist, String album) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (artist != null ? !artist.equals(song.artist) : song.artist != null) return false;
        return !(title != null ? !title.equals(song.title) : song.title != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        return result;
    }
}
