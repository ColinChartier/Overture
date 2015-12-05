package com.colinchartier.overture.app.impl;

import com.colinchartier.overture.app.fragments.presenters.MusicBarsPresenter;
import com.colinchartier.overture.app.fragments.presenters.NavigationDrawerPresenter;
import com.colinchartier.overture.app.fragments.presenters.SongControlsPresenter;
import com.colinchartier.overture.app.fragments.presenters.SongListPresenter;
import com.colinchartier.overture.app.fragments.views.MusicBarsView;
import com.colinchartier.overture.app.fragments.views.NavigationDrawerView;
import com.colinchartier.overture.app.fragments.views.SongControlsView;
import com.colinchartier.overture.app.fragments.views.SongListView;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private final MusicBarsView musicBarsView;
    private final NavigationDrawerView navDrawerView;
    private final SongControlsView songControlsView;
    private final SongListView songListView;

    public MainModule(MusicBarsView musicBarsView, NavigationDrawerView navDrawerView, SongControlsView songControlsView, SongListView songListView) {
        this.musicBarsView = musicBarsView;
        this.navDrawerView = navDrawerView;
        this.songControlsView = songControlsView;
        this.songListView = songListView;
    }

    @Provides
    public MusicBarsPresenter provideMusicBarsPresenter() {
        return null; //TODO
    }

    @Provides
    public NavigationDrawerPresenter provideNavigationDrawerPresenter() {
        return null;
    }

    @Provides
    public SongControlsPresenter provideSongControlsPresenter() {
        return null;
    }

    @Provides
    public SongListPresenter provideSongListPresenter() {
        return null;
    }
}
