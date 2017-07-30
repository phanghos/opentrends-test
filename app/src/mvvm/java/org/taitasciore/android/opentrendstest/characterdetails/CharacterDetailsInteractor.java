package org.taitasciore.android.opentrendstest.characterdetails;

import org.taitasciore.android.networking.MarvelService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by roberto on 28/07/17.
 */

public class CharacterDetailsInteractor {

    private MarvelService service;

    @Inject
    public CharacterDetailsInteractor(MarvelService service) {
        this.service = service;
    }

    public Observable<CharacterDetailsViewState> getComics(int characterId) {
        return service.getComics(characterId)
                .subscribeOn(Schedulers.io())
                .map(wrapper -> new CharacterDetailsViewState.SuccessComics(wrapper.getData()));
    }

    public Observable<CharacterDetailsViewState> getEvents(int characterId) {
        return service.getEvents(characterId)
                .subscribeOn(Schedulers.io())
                .map(wrapper -> new CharacterDetailsViewState.SuccessEvents(wrapper.getData()));
    }
}
