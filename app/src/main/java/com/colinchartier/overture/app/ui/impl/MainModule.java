package com.colinchartier.overture.app.ui.impl;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.playlist.PlaylistDatabaseHelper;
import com.colinchartier.overture.app.ui.fragments.presenters.MusicBarsPresenter;
import com.colinchartier.overture.app.ui.fragments.presenters.NavigationDrawerPresenter;
import com.colinchartier.overture.app.ui.fragments.presenters.SongControlsPresenter;
import com.colinchartier.overture.app.ui.fragments.presenters.SongListPresenter;
import com.colinchartier.overture.app.ui.fragments.views.MusicBarsView;
import com.colinchartier.overture.app.ui.fragments.views.NavigationDrawerView;
import com.colinchartier.overture.app.ui.fragments.views.SongControlsView;
import com.colinchartier.overture.app.ui.fragments.views.SongListView;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class MainModule {
    private final Context activityContext;
    private final Context applicationContext;
    private final FragmentManager fragmentManager;
    private final LayoutInflater layoutInflater;

    private final MusicBarsView musicBarsView;
    private final NavigationDrawerView navDrawerView;
    private final SongControlsView songControlsView;
    private final SongListView songListView;

    public MainModule(Context activityContext, Context applicationContext, FragmentManager fragmentManager, LayoutInflater layoutInflater,
                      MusicBarsView musicBarsView, NavigationDrawerView navDrawerView,
                      SongControlsView songControlsView, SongListView songListView) {
        this.activityContext = activityContext;
        this.applicationContext = applicationContext;
        this.fragmentManager = fragmentManager;
        this.layoutInflater = layoutInflater;

        this.musicBarsView = musicBarsView;
        this.navDrawerView = navDrawerView;
        this.songControlsView = songControlsView;
        this.songListView = songListView;
    }

    /*Contextual stuff*/
    @Provides
    @FromContext(ContextType.ACTIVITY)
    public Context provideActivityContext() {
        return activityContext;
    }

    @Provides
    @FromContext(ContextType.APPLICATION)
    public Context provideApplicationContext() {
        return applicationContext;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return fragmentManager;
    }

    @Provides
    public LayoutInflater provideLayoutInflater() {
        return layoutInflater;
    }

    /*Contextual views*/
    @Provides
    public MusicBarsView provideMusicBarsView() {
        return musicBarsView;
    }

    @Provides
    public NavigationDrawerView provideNavDrawerView() {
        return navDrawerView;
    }

    @Provides
    public SongControlsView provideSongControlsView() {
        return songControlsView;
    }

    @Provides
    public SongListView provideSongListView() {
        return songListView;
    }

    /*Default implementations*/
    @Provides
    @Singleton
    public DefaultDatabaseHelper provideDefaultDatabaseHelper(@FromContext(ContextType.ACTIVITY) Context activityContext) {
        return new DefaultDatabaseHelper(activityContext);
    }

    /*Model*/
    @Provides
    public PlaylistDatabaseHelper providePlaylistDatabaseHelper(DefaultDatabaseHelper helper) {
        return helper;
    }

    /*Presenters*/
    @Provides
    public MusicBarsPresenter provideMusicBarsPresenter(DefaultMusicBarsPresenter presenter) {
        return presenter;
    }

    @Provides
    public NavigationDrawerPresenter provideNavigationDrawerPresenter(DefaultNavigationDrawerPresenter presenter) {
        return presenter;
    }

    @Provides
    public SongControlsPresenter provideSongControlsPresenter(DefaultSongControlsPresenter presenter) {
        return presenter;
    }

    @Provides
    public SongListPresenter provideSongListPresenter(DefaultSongListPresenter presenter) {
        return presenter;
    }
}
