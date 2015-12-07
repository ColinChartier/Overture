package com.colinchartier.overture.app.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.colinchartier.overture.app.services.MusicService;
import com.colinchartier.overture.app.services.presenters.MusicPresenter;
import com.google.common.base.Preconditions;

public abstract class MusicBindingPresenter {
    private boolean musicServiceBound = false;
    private MusicPresenter musicService;
    private final Object musicServiceLock = new Object();

    private final Context bindContext;

    private ServiceConnection musicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            musicService = binder.getService();
            synchronized (musicServiceLock) {
                musicServiceLock.notifyAll();
            }
            musicServiceBound = true;
            MusicBindingPresenter.this.onMusicServiceConnected();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
            musicServiceBound = false;
        }
    };

    public MusicBindingPresenter(Context bindContext) {
        Preconditions.checkNotNull(bindContext);
        this.bindContext = bindContext;
    }


    protected MusicPresenter getMusicService(boolean wait) {
        if (musicService == null) {
            synchronized (musicServiceLock) {
                if (musicService == null) { //Double checked locking
                    if (!wait) {
                        return null;
                    }
                    try {
                        musicServiceLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return musicService;
    }

    protected MusicPresenter getMusicService() {
        return getMusicService(true);
    }

    protected void bindMusicService() {
        Intent intent = new Intent(bindContext, MusicService.class);
        bindContext.bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void unbindMusicService() {
        if (musicServiceBound) {
            bindContext.unbindService(musicServiceConnection);
            musicServiceBound = false;
        }
    }

    protected abstract void onMusicServiceConnected();
}
