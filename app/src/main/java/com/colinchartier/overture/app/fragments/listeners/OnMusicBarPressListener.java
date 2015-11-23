package com.colinchartier.overture.app.fragments.listeners;

/**
 * Created by overcaste on 1/5/2015.
 */
public interface OnMusicBarPressListener {
    /**
     * Fired when any of the individual bars in the music bars fragment is clicked.
     *
     * @param musicPercent the percent of the entire set of music bars which was pressed.
     */
    void onMusicBarPress(double musicPercent);
}
