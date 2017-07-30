package org.taitasciore.android.di.component;

import org.taitasciore.android.di.CustomScope;
import org.taitasciore.android.di.module.CharactersModule;
import org.taitasciore.android.opentrendstest.characters.CharactersFragment;

import dagger.Component;

/**
 * Created by roberto on 27/07/17.
 */

@CustomScope
@Component(dependencies = NetworkComponent.class, modules = CharactersModule.class)
public interface CharactersComponent {

    void inject(CharactersFragment fragment);
}
