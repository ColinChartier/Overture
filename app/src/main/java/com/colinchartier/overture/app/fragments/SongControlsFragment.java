package com.colinchartier.overture.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.colinchartier.overture.app.R;
import com.colinchartier.overture.app.fragments.presenters.SongControlsPresenter;
import com.colinchartier.overture.app.fragments.views.SongControlsView;
import com.google.common.base.Preconditions;

public class SongControlsFragment extends Fragment implements SongControlsView {
    @Bind(R.id.playpause_button)
    ImageButton playPauseButton;
    @Bind(R.id.repeat_button)
    ImageButton repeatButton;
    @Bind(R.id.rewind_button)
    ImageButton rewindButton;
    @Bind(R.id.fast_forward_button)
    ImageButton fastForwardButton;
    @Bind(R.id.set_playback_speed_button)
    Button setPlaybackSpeedButton;

    private SongControlsPresenter presenter;

    public SongControlsFragment() {
        //Required empty constructor
    }

    public void setPresenter(SongControlsPresenter presenter) {
        Preconditions.checkState(this.presenter == null, "Presenter is already set!");
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song_controls, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        presenter.init();
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.stop();
    }

    /*Primary media controls*/
    @OnClick(R.id.playpause_button)
    public void onPlayPauseButtonClicked(View button) {
        presenter.onPlayPauseButtonClicked(button);
    }

    @OnClick(R.id.skip_previous_button)
    public void onSkipPreviousButtonClicked(View button) {
        presenter.onSkipPreviousButtonClicked(button);
    }

    @OnClick(R.id.skip_next_button)
    public void onSkipNextButtonClicked(View button) {
        presenter.onSkipNextButtonClicked(button);
    }

    @OnClick(R.id.fast_forward_button)
    public void onFastForwardButtonClicked(View button) {
        presenter.onFastForwardButtonClicked(button);
    }

    @OnClick(R.id.rewind_button)
    public void onRewindButtonClicked(View button) {
        presenter.onRewindButtonClicked(button);
    }

    /*Secondary media controls*/
    @OnClick(R.id.repeat_button)
    public void onRepeatButtonClicked(View button) {
        presenter.onRepeatButtonClicked(button);
    }

    @OnClick(R.id.set_playback_speed_button)
    public void onPlaybackSpeedButtonClicked(View button) {
        presenter.onPlaybackSpeedButtonClicked(button);
    }

    @Override
    public void setLoopButtonToggled(boolean looping) {

    }
}
