package com.colinchartier.overture.app.fragments.impl;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;

public class MusicBar extends ProgressBar {
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
                                new MusicBarForegroundDrawable(), 0x03, ClipDrawable.HORIZONTAL)
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
