package org.taitasciore.android.di.module;

import org.taitasciore.android.di.CustomScope;
import org.taitasciore.android.networking.MarvelService;
import org.taitasciore.android.opentrendstest.characters.CharactersContract;
import org.taitasciore.android.opentrendstest.characters.CharactersInteractor;
import org.taitasciore.android.opentrendstest.characters.CharactersPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roberto on 29/07/17.
 */

@Module
public class CharactersModule {

    @Provides @CustomScope
    public CharactersInteractor provideCharactersInteractor(MarvelService service) {
        return new CharactersInteractor(service);
    }

    @Provides @CustomScope
    public CharactersContract.Presenter provideCharactersPresenter(CharactersInteractor interactor) {
        return new CharactersPresenter(interactor);
    }
}
