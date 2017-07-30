package org.taitasciore.android.di.component;

import org.taitasciore.android.di.CustomScope;
import org.taitasciore.android.di.module.CharacterDetailsModule;
import org.taitasciore.android.opentrendstest.characterdetails.CharacterDetailsFragment;

import dagger.Component;

/**
 * Created by roberto on 28/07/17.
 */

@CustomScope
@Component(dependencies = NetworkComponent.class, modules = CharacterDetailsModule.class)
public interface CharacterDetailsComponent {

    void inject(CharacterDetailsFragment fragment);
}
