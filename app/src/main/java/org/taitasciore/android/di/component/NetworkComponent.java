package org.taitasciore.android.di.component;

import org.taitasciore.android.di.module.NetworkModule;
import org.taitasciore.android.networking.MarvelService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by roberto on 27/07/17.
 */

@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    MarvelService marvelService();
}
