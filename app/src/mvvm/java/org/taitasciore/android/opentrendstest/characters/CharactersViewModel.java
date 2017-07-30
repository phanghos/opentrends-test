package org.taitasciore.android.opentrendstest.characters;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by roberto on 27/07/17.
 */

public class CharactersViewModel {

    private CharactersInteractor interactor;

    private BehaviorSubject<CharactersViewState> stateObservable;

    private BehaviorSubject<Boolean> loading;

    @Inject
    public CharactersViewModel(CharactersInteractor interactor) {
        this.interactor = interactor;
        this.stateObservable = BehaviorSubject.create();
        this.loading = BehaviorSubject.create();
    }

    public Observable<CharactersViewState> searchCharacters(String query) {
        if (query.length() < 3) {
            loading.onNext(false);
            stateObservable.onNext(new CharactersViewState.Empty());
            return Observable.just(new CharactersViewState.Empty());
        }

        return interactor.searchCharacters(query)
                .doOnSubscribe(x -> loading.onNext(true))
                .doOnTerminate(() -> loading.onNext(false))
                .doOnNext(viewState -> stateObservable.onNext(viewState))
                .onErrorReturn(error -> {
                    stateObservable.onNext(new CharactersViewState.Error(error));
                    return new CharactersViewState.Error(error);
                });
    }

    public Observable<CharactersViewState> getStateObservable() {
        return stateObservable;
    }

    public Observable<Boolean> loading() {
        return loading;
    }
}
