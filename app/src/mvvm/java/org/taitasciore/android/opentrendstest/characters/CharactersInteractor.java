package org.taitasciore.android.opentrendstest.characters;

import org.taitasciore.android.model.Character;
import org.taitasciore.android.networking.MarvelService;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by roberto on 27/07/17.
 */

public class CharactersInteractor {

    private MarvelService service;

    @Inject
    public CharactersInteractor(MarvelService service) {
        this.service = service;
    }

    public Observable<CharactersViewState> searchCharacters(String query) {
        return service.searchCharacters(query)
                .subscribeOn(Schedulers.io())
                .map(wrapper -> {
                    ArrayList<Character> characters = wrapper.getData().getResults();
                    if (characters.isEmpty()) return new CharactersViewState.Empty();
                    return new CharactersViewState.Success(characters);
                });
    }
}
