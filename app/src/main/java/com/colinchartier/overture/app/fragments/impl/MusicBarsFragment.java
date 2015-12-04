package com.colinchartier.overture.app.fragments.impl;

import android.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.colinchartier.overture.app.fragments.listeners.OnMusicBarPressListener;

import java.util.ArrayList;
import java.util.List;

public class MusicBarsFragment extends Fragment {
    private static final int BARS_HEIGHT = 30; // dp
    private static final int BAR_PADDING = 7; // dp
    private static final int MAX_PROGRESS = 10000;

    private int lastHeight = 0;
    private final List<MusicBar> bars = new ArrayList<>();

    private final List<OnMusicBarPressListener> musicBarPressListeners = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // View view = inflater.inflate(android.R.layout., container, false);
        LinearLayout l = new LinearLayout(getActivity()) {
            @Override
            protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
                super.onSizeChanged(xNew, yNew, xOld, yOld);

                MusicBarsFragment.this.generateBars(yNew);
            }
        };
        //l.setBackgroundColor(0);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    private void generateBars(int height) { // This is ugly, but I ran out of patience with .resize()
        if (height == lastHeight) {
            return;
        }
        lastHeight = height;
        LinearLayout l = (LinearLayout) getView();
        int barHeightPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BARS_HEIGHT, getResources().getDisplayMetrics());
        int barPaddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BAR_PADDING, getResources().getDisplayMetrics());

        if (height < ((barPaddingPx * 2) + barHeightPx)) { // It's too small for even one bar, just exit.
            return;
        }
        final int barCount =
                (height - barPaddingPx) // An extra padding for the margin at the top
                        / (barHeightPx + barPaddingPx); // then N padding/bar counts
        for (int i = 0; i < barCount; i++) {
            final MusicBar bar = new MusicBar(getActivity());
            // ProgressBar bar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, barHeightPx);
            lp.setMargins(0, barPaddingPx, 0, 0);
            bar.setLayoutParams(lp);
            l.addView(bar);
            bar.setMax(MAX_PROGRESS);
            bar.setProgress(0);
            bar.invalidate();
            final int barIndex = i;
            bar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        double percentPerBar = 100d / barCount;
                        double percent =
                                percentPerBar * barIndex + //add the percentages of all of the bars above this one,
                                        percentPerBar * ((double) event.getX() / (double) v.getWidth()); //and the percentage of the specific click
                        for (OnMusicBarPressListener l : musicBarPressListeners) {
                            l.onMusicBarPress(percent);
                        }
                        return true;
                    }
                    return false;
                }
            });
            bars.add(bar);
        }
    }

    public void setPercent(final double percent) {
        double percentPerBar = 100d / bars.size();
        double pmod = percent % percentPerBar;
        int filledBars = (int) (percent / percentPerBar);
        for (int i = 0; i < filledBars; i++) {
            ProgressBar bar = bars.get(i);
            bar.setProgress(bar.getMax());
        }
        double barProgress = pmod / percentPerBar; // this is on a scale from 0-percentPerBar here, normalize to 0-1
        int progress = (int) (MAX_PROGRESS * barProgress);
        bars.get(filledBars).setProgress(progress);
        for (int i = filledBars + 1; i < bars.size(); i++) {
            bars.get(i).setProgress(0);
        }
    }

    public int getBarCount() {
        return bars.size();
    }

    public void addMusicBarPressListener(OnMusicBarPressListener listener) {
        musicBarPressListeners.add(listener);
    }

    public void removeMusicBarPressListener(OnMusicBarPressListener listener) {
        musicBarPressListeners.remove(listener);
    }
}
