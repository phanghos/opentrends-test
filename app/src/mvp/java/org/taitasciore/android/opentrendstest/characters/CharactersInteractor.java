package org.taitasciore.android.opentrendstest.characters;

import org.taitasciore.android.model.Character;
import org.taitasciore.android.networking.MarvelService;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by roberto on 29/07/17.
 * Interactor that communicates with the Marvel API and then communicates
 * the results back to the corresponding view model
 */

public class CharactersInteractor {

    private MarvelService service;

    @Inject
    public CharactersInteractor(MarvelService service) {
        this.service = service;
    }

    /**
     * Search for characters matching given query
     * @param query Query
     * @return List of characters matching query
     */
    public Observable<ArrayList<Character>> searchCharacters(String query) {
        return service.searchCharacters(query)
                .map(wrapper -> wrapper.getData().getResults());
    }
}
