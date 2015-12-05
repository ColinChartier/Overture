package com.colinchartier.overture.app.song;

import java.util.Comparator;

/**
 * Created by overcaste on 11/4/2015.
 */
public enum SongSortMethod implements Comparator<Song> {
    ARTIST {
        @Override
        public int compare(Song lhs, Song rhs) {
            String artist1 = lhs.getArtist();
            String artist2 = rhs.getArtist();
            if (artist1 == null) {
                if (artist2 == null) {
                    return 0;
                }
                return 1;
            }
            if (artist2 == null) {
                return -1;
            }
            return artist1.compareTo(artist2);
        }
    },
    ALBUM {
        @Override
        public int compare(Song lhs, Song rhs) {
            String album1 = lhs.getAlbum();
            String album2 = rhs.getAlbum();
            if (album1 == null) {
                if (album2 == null) {
                    return 0;
                }
                return 1;
            }
            if (album2 == null) {
                return -1;
            }
            return album1.compareTo(album2);
        }
    },
    TITLE {
        @Override
        public int compare(Song lhs, Song rhs) {
            String title1 = lhs.getTitle();
            String title2 = rhs.getTitle();
            if (title1 == null) {
                if (title2 == null) {
                    return 0;
                }
                return 1;
            }
            if (title2 == null) {
                return -1;
            }
            return title1.compareTo(title2);
        }
    },
    SHUFFLE {
        @Override
        public int compare(Song lhs, Song rhs) {
            throw new UnsupportedOperationException();
        }
    }
}
