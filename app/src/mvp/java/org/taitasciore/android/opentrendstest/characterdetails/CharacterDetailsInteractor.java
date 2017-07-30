package org.taitasciore.android.opentrendstest.characterdetails;

import org.taitasciore.android.model.ComicDataContainer;
import org.taitasciore.android.model.EventDataContainer;
import org.taitasciore.android.networking.MarvelService;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by roberto on 30/07/17.
 * Interactor that communicates with the Marvel API and then communicates
 * the results back to the corresponding view model
 */

public class CharacterDetailsInteractor {

    private MarvelService service;

    @Inject
    public CharacterDetailsInteractor(MarvelService service) {
        this.service = service;
    }

    /**
     * Fetch comics of a character given its id
     * @param characterId Character ID
     * @return Comic data
     */
    public Observable<ComicDataContainer> getComics(int characterId) {
        return service.getComics(characterId)
                .map(wrapper -> wrapper.getData());
    }

    /**
     * Fetch events of a character given its id
     * @param characterId Character ID
     * @return Event data
     */
    public Observable<EventDataContainer> getEvents(int characterId) {
        return service.getEvents(characterId)
                .map(wrapper -> wrapper.getData());
    }
}
