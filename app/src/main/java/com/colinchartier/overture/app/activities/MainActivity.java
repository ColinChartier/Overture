package com.colinchartier.overture.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.colinchartier.overture.app.R;
import com.colinchartier.overture.app.fragments.MusicBarsFragment;
import com.colinchartier.overture.app.fragments.NavigationDrawerFragment;
import com.colinchartier.overture.app.fragments.SongControlsFragment;
import com.colinchartier.overture.app.fragments.SongListFragment;
import com.colinchartier.overture.app.impl.DaggerMainComponent;
import com.colinchartier.overture.app.impl.MainComponent;
import com.colinchartier.overture.app.impl.MainModule;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.app_bar)
    Toolbar toolbar;

    MusicBarsFragment musicBarsFragment;
    NavigationDrawerFragment navDrawerFragment;
    SongControlsFragment songControlsFragment;
    SongListFragment songListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert (getSupportActionBar() != null); //silly compiler, we're setting this already.
        getSupportActionBar().setDisplayShowTitleEnabled(false); //Don't show title on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);

        //Get fragments
        musicBarsFragment = (MusicBarsFragment) getSupportFragmentManager().findFragmentById(R.id.music_bars_fragment);
        navDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        songControlsFragment = (SongControlsFragment) getSupportFragmentManager().findFragmentById(R.id.song_controls_fragment);
        songListFragment = (SongListFragment) getSupportFragmentManager().findFragmentById(R.id.song_list_fragment);

        //Inject presenters
        MainModule module = new MainModule(musicBarsFragment, navDrawerFragment, songControlsFragment, songListFragment);
        MainComponent component = DaggerMainComponent.builder().mainModule(module).build();
        musicBarsFragment.setPresenter(component.musicBarsPresenter());
        navDrawerFragment.setPresenter(component.navDrawerPresenter());
        songControlsFragment.setPresenter(component.songControlsPresenter());
        songListFragment.setPresenter(component.songListPresenter());
    }
}
