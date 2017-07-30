package org.taitasciore.android.opentrendstest.characters;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by roberto on 29/07/17.
 * Presenter for the characters list screen (search)
 */

public class CharactersPresenter implements CharactersContract.Presenter {

    private CharactersContract.View view;

    private CharactersInteractor interactor;

    private Disposable disposable;

    @Inject
    public CharactersPresenter(CharactersInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void attachView(CharactersContract.View view) {
        this.view = view;
    }

    /**
     * Detach view from presenter and unsubscribe from observables
     */
    @Override
    public void detachView() {
        this.view = null;
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * Search for characters matching given query
     * @param query Query
     */
    @Override
    public void searchCharacters(String query) {
        /**
         * Unsubscribe from previous observable in order to cancel previous request
         * in case it was still in progress
         */
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        /**
         * Hide loader and empty screen if query length < 3
         * Subscribe to observable otherwise
         */
        if (query.length() < 3) {
            view.hideLoading();
            view.showEmptyList();
            return;
        }

        // Search for characters through interactor
        disposable = interactor.searchCharacters(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(x -> view.showLoading())
                .doOnTerminate(() -> view.hideLoading())
                .onErrorReturn(error -> {
                    view.showError(error);
                    return new ArrayList<>();
                })
                .subscribe(characters -> view.showCharacters(characters));
    }
}
