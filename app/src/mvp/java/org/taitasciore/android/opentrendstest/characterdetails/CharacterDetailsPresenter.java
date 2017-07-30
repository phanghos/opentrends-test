package org.taitasciore.android.opentrendstest.characterdetails;

import org.taitasciore.android.model.ComicDataContainer;
import org.taitasciore.android.model.EventDataContainer;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by roberto on 30/07/17.
 * Presenter for the character details' screen
 */

public class CharacterDetailsPresenter implements CharacterDetailsContract.Presenter {

    private CharacterDetailsContract.View view;

    private CharacterDetailsInteractor interactor;

    private ComicDataContainer comicDataContainer;

    private EventDataContainer eventDataContainer;

    @Inject
    public CharacterDetailsPresenter(CharacterDetailsInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Attach view to presenter
     * @param view View
     */
    @Override
    public void attachView(CharacterDetailsContract.View view) {
        this.view = view;
    }

    /**
     * Detach view from presenter
     */
    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getComics(int characterId) {
        /**
         * Show comics immediately if they've already been fetched previously
         * Subscribe to observable otherwise
         */
        if (comicDataContainer != null) {
            view.showComics(comicDataContainer.getResults());
            return;
        }

        // Fetch comics through interactor
        interactor.getComics(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(x -> view.showLoading())
                .doOnTerminate(() -> view.hideLoading())
                .onErrorReturn(error -> {
                    view.showError(error);
                    return new ComicDataContainer();
                })
                .subscribe(comicDataContainer -> {
                    if (comicDataContainer.getResults() != null) {
                        this.comicDataContainer = comicDataContainer;
                        view.showComics(comicDataContainer.getResults());
                        view.showNumberOfComics(comicDataContainer.getTotal());
                    }
                });
    }

    @Override
    public void getEvents(int characterId) {
        /**
         * Show comics immediately if they've already been fetched previously
         * Subscribe to observable otherwise
         */
        if (eventDataContainer != null) {
            view.showEvents(eventDataContainer.getResults());
            return;
        }

        // Fetch events through interactor
        interactor.getEvents(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(x -> view.showLoading())
                .doOnTerminate(() -> view.hideLoading())
                .onErrorReturn(error -> {
                    view.showError(error);
                    return new EventDataContainer();
                })
                .subscribe(eventDataContainer -> {
                    if (eventDataContainer.getResults() != null) {
                        this.eventDataContainer = eventDataContainer;
                        view.showEvents(eventDataContainer.getResults());
                        view.showNumberOfEvents(eventDataContainer.getTotal());
                    }
                });
    }
}
