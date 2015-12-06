package com.colinchartier.overture.app.impl;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.colinchartier.overture.app.ContextType;
import com.colinchartier.overture.app.FromContext;
import com.colinchartier.overture.app.fragments.presenters.MusicBarsPresenter;
import com.colinchartier.overture.app.fragments.presenters.NavigationDrawerPresenter;
import com.colinchartier.overture.app.fragments.presenters.SongControlsPresenter;
import com.colinchartier.overture.app.fragments.presenters.SongListPresenter;
import com.colinchartier.overture.app.fragments.views.MusicBarsView;
import com.colinchartier.overture.app.fragments.views.NavigationDrawerView;
import com.colinchartier.overture.app.fragments.views.SongControlsView;
import com.colinchartier.overture.app.fragments.views.SongListView;
import com.colinchartier.overture.app.playlist.PlaylistDatabaseHelper;
import com.colinchartier.overture.app.playlist.PlaylistManager;
import com.colinchartier.overture.app.playlist.impl.DefaultPlaylistManager;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class MainModule {
    private final Context activityContext;
    private final Context applicationContext;
    private final FragmentManager fragmentManager;

    private final MusicBarsView musicBarsView;
    private final NavigationDrawerView navDrawerView;
    private final SongControlsView songControlsView;
    private final SongListView songListView;

    public MainModule(Context activityContext, Context applicationContext, FragmentManager fragmentManager,
                      MusicBarsView musicBarsView, NavigationDrawerView navDrawerView,
                      SongControlsView songControlsView, SongListView songListView) {
        this.activityContext = activityContext;
        this.applicationContext = applicationContext;
        this.fragmentManager = fragmentManager;
        this.musicBarsView = musicBarsView;
        this.navDrawerView = navDrawerView;
        this.songControlsView = songControlsView;
        this.songListView = songListView;
    }

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
    @Singleton
    public DefaultDatabaseHelper provideDefaultDatabaseHelper(@FromContext(ContextType.ACTIVITY) Context activityContext) {
        return new DefaultDatabaseHelper(activityContext);
    }

    @Provides
    public MusicBarsPresenter provideMusicBarsPresenter() {
        return null; //TODO
    }

    @Provides
    public NavigationDrawerPresenter provideNavigationDrawerPresenter(DefaultNavigationDrawerPresenter presenter) {
        return presenter;
    }

    @Provides
    public SongControlsPresenter provideSongControlsPresenter() {
        return null;
    }

    @Provides
    public SongListPresenter provideSongListPresenter() {
        return null;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return fragmentManager;
    }

    @Provides
    public PlaylistManager providePlaylistManager(DefaultPlaylistManager manager) {
        return manager;
    }

    @Provides
    public PlaylistDatabaseHelper providePlaylistDatabaseHelper(DefaultDatabaseHelper helper) {
        return helper;
    }
}
