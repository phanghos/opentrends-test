package org.taitasciore.android.opentrendstest.characterdetails;

import org.taitasciore.android.model.ComicDataContainer;
import org.taitasciore.android.model.EventDataContainer;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by roberto on 28/07/17.
 */

public class CharacterDetailsViewModel {

    private CharacterDetailsInteractor interactor;

    private BehaviorSubject<CharacterDetailsViewState> stateObservable;

    private BehaviorSubject<Boolean> loading;

    private CharacterDetailsViewState currentState;

    private ComicDataContainer comicDataContainer;

    private EventDataContainer eventDataContainer;

    @Inject
    public CharacterDetailsViewModel(CharacterDetailsInteractor interactor) {
        this.interactor = interactor;
        stateObservable = BehaviorSubject.create();
        loading = BehaviorSubject.create();
    }

    /**
     * Restore previous state
     */
    public void restoreState() {
        if (currentState != null) stateObservable.onNext(currentState);
    }

    public Observable<CharacterDetailsViewState> getComics(int characterId) {
        /**
         * Show comics immediately if they've already been fetched previously
         * Subscribe to observable otherwise
         */
        if (comicDataContainer != null) {
            currentState = new CharacterDetailsViewState.SuccessComics(comicDataContainer);
            stateObservable.onNext(currentState);
            return Observable.just(currentState);
        }

        return interactor.getComics(characterId)
                .doOnSubscribe(x -> loading.onNext(true))
                .doOnTerminate(() -> loading.onNext(false))
                .doOnNext(state -> {
                    currentState = state;
                    stateObservable.onNext(state);
                })
                .onErrorReturn(error -> {
                    currentState = new CharacterDetailsViewState.Error(error);
                    stateObservable.onNext(currentState);
                    return currentState;
                });
    }

    public Observable<CharacterDetailsViewState> getEvents(int characterId) {
        /**
         * Show events immediately if they've already been fetched previously
         * Subscribe to observable otherwise
         */
        if (eventDataContainer != null) {
            currentState = new CharacterDetailsViewState.SuccessEvents(eventDataContainer);
            stateObservable.onNext(currentState);
            return Observable.just(currentState);
        }

        return interactor.getEvents(characterId)
                .doOnSubscribe(x -> loading.onNext(true))
                .doOnTerminate(() -> loading.onNext(false))
                .doOnNext(state -> {
                    currentState = state;
                    stateObservable.onNext(state);
                })
                .onErrorReturn(error -> {
                    currentState = new CharacterDetailsViewState.Error(error);
                    stateObservable.onNext(currentState);
                    return currentState;
                });
    }

    public Observable<CharacterDetailsViewState> getStateObservable() {
        return stateObservable;
    }

    public Observable<Boolean> loading() {
        return loading;
    }

    public void setComics(ComicDataContainer comicDataContainer) {
        this.comicDataContainer = comicDataContainer;
    }

    public void setEvents(EventDataContainer eventDataContainer) {
        this.eventDataContainer = eventDataContainer;
    }
}
