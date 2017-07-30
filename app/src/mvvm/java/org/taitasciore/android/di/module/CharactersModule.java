package org.taitasciore.android.di.module;

import org.taitasciore.android.di.CustomScope;
import org.taitasciore.android.networking.MarvelService;
import org.taitasciore.android.opentrendstest.characters.CharactersInteractor;
import org.taitasciore.android.opentrendstest.characters.CharactersViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roberto on 27/07/17.
 */

@Module
public class CharactersModule {

    @Provides @CustomScope
    public CharactersInteractor provideCharactersInteractor(MarvelService service) {
        return new CharactersInteractor(service);
    }

    @Provides @CustomScope
    public CharactersViewModel provideCharactersViewModel(CharactersInteractor interactor) {
        return new CharactersViewModel(interactor);
    }
}
