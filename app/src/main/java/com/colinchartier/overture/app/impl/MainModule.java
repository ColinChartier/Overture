package com.colinchartier.overture.app.impl;

import android.content.Context;
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
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private final Context activityContext;
    private final Context applicationContext;

    private final MusicBarsView musicBarsView;
    private final NavigationDrawerView navDrawerView;
    private final SongControlsView songControlsView;
    private final SongListView songListView;

    public MainModule(Context activityContext, Context applicationContext, MusicBarsView musicBarsView, NavigationDrawerView navDrawerView, SongControlsView songControlsView, SongListView songListView) {
        this.activityContext = activityContext;
        this.applicationContext = applicationContext;
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
}
