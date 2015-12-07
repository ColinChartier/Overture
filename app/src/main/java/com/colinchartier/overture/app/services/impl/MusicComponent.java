package com.colinchartier.overture.app.services.impl;

import com.colinchartier.overture.app.services.presenters.MusicPresenter;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {MusicModule.class})
public interface MusicComponent {
    MusicPresenter presenter();
}
