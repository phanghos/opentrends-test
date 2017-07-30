package org.taitasciore.android.opentrendstest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.taitasciore.android.di.component.DaggerNetworkComponent;
import org.taitasciore.android.di.component.NetworkComponent;
import org.taitasciore.android.di.module.NetworkModule;

/**
 * Created by roberto on 27/07/17.
 */

public class OpentrendsApp extends Application {

    private NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        setNetworkComponent();
    }

    public NetworkComponent getNetworkComponent() {
        return mNetworkComponent;
    }

    public void setNetworkComponent() {
        mNetworkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }
}
