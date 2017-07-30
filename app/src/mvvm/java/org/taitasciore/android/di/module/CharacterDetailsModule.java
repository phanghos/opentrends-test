package org.taitasciore.android.di.module;

import org.taitasciore.android.di.CustomScope;
import org.taitasciore.android.networking.MarvelService;
import org.taitasciore.android.opentrendstest.characterdetails.CharacterDetailsInteractor;
import org.taitasciore.android.opentrendstest.characterdetails.CharacterDetailsViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roberto on 28/07/17.
 */

@Module
public class CharacterDetailsModule {

    @Provides @CustomScope
    public CharacterDetailsInteractor provideCharacterDetailsInteractor(MarvelService service) {
        return new CharacterDetailsInteractor(service);
    }

    @Provides @CustomScope
    public CharacterDetailsViewModel provideCharacterDetailsViewModel(CharacterDetailsInteractor interactor) {
        return new CharacterDetailsViewModel(interactor);
    }
}
