package com.colinchartier.overture.app.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.colinchartier.overture.app.services.impl.DaggerMusicComponent;
import com.colinchartier.overture.app.services.impl.MusicModule;
import com.colinchartier.overture.app.services.presenters.MusicPresenter;

public class MusicService extends Service {
    private static final String LOG_TAG = "Overture Music Service";

    private final IBinder binder = new LocalBinder();
    private MusicPresenter presenter;

    public class LocalBinder extends Binder {
        public MusicPresenter getService() {
            return presenter;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "Music service successfully started: " + intent);

        this.presenter = DaggerMusicComponent.builder().musicModule(new MusicModule()).build().presenter();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return false;
    }
}
