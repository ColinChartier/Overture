package com.colinchartier.overture.app.impl;

import com.colinchartier.overture.app.fragments.presenters.MusicBarsPresenter;
import com.colinchartier.overture.app.fragments.presenters.NavigationDrawerPresenter;
import com.colinchartier.overture.app.fragments.presenters.SongControlsPresenter;
import com.colinchartier.overture.app.fragments.presenters.SongListPresenter;
import dagger.Component;

import javax.inject.Singleton;

@Component(modules = {MainModule.class})
@Singleton
public interface MainComponent {
    MusicBarsPresenter musicBarsPresenter();

    NavigationDrawerPresenter navDrawerPresenter();

    SongControlsPresenter songControlsPresenter();

    SongListPresenter songListPresenter();
}
