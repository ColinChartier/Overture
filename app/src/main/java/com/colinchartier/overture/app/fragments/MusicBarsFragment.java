package com.colinchartier.overture.app.fragments;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.*;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.colinchartier.overture.app.fragments.presenters.MusicBarsPresenter;
import com.colinchartier.overture.app.fragments.views.MusicBarsView;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class MusicBarsFragment extends Fragment implements MusicBarsView {
    private static final int BARS_HEIGHT = 30; // dp
    private static final int BAR_PADDING = 7; // dp
    private static final int MAX_PROGRESS = 10000;

    private MusicBarsPresenter presenter;

    private int lastHeight = 0;
    private final List<MusicBar> bars = new ArrayList<>();

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.init();
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.stop();
    }

    public void setPresenter(MusicBarsPresenter presenter) {
        Preconditions.checkState(this.presenter == null, "Presenter is already set!");
        this.presenter = presenter;
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
                        presenter.onMusicBarPress(percent);
                        return true;
                    }
                    return false;
                }
            });
            bars.add(bar);
        }
    }

    @Override
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

    @Override
    public int getBarCount() {
        return bars.size();
    }

    private static class MusicBar extends ProgressBar {
        private static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();

        public MusicBar(Context context) {
            this(context, null);
        }

        public MusicBar(Context context, AttributeSet attrs) {
            super(context, attrs, android.R.attr.progressBarStyleHorizontal);
            setProgressDrawable(
                    new LayerDrawable(new Drawable[]{
                            new MusicBarBackgroundDrawable(),
                            new ClipDrawable(
                                    new MusicBarForegroundDrawable(), Gravity.START, ClipDrawable.HORIZONTAL)
                    })
            );
        }

        private final class MusicBarForegroundDrawable extends Drawable {
            @Override
            public void draw(Canvas canvas) {
                Rect bounds = getBounds();
                Paint p = new Paint();
                int width = bounds.width();
                int height = bounds.height();
                p.setColor(Color.BLUE);
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, width, height), 1, 1, p);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {
                // TODO Auto-generated method stub

            }

            @Override
            public int getOpacity() {
                return PixelFormat.OPAQUE;
            }
        }

        private final class MusicBarBackgroundDrawable extends Drawable {
            @Override
            public void draw(Canvas canvas) {
                Rect bounds = getBounds();
                Paint p = new Paint();
                int width = bounds.width();
                int height = bounds.height();
                p.setColor(Color.CYAN);
                canvas.drawRoundRect(new RectF(0.0f, 0.0f, width, height), 1, 1, p);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter cf) {
                // TODO Auto-generated method stub

            }

            @Override
            public int getOpacity() {
                return PixelFormat.OPAQUE;
            }
        }
    }
}
