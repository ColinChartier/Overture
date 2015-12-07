package com.colinchartier.overture.app.services.impl;

import com.colinchartier.overture.app.services.presenters.MusicPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class MusicModule {
    @Provides
    public MusicPresenter providePresenter(DefaultMusicPresenter presenter) {
        return presenter;
    }
}
