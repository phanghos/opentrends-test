package org.taitasciore.android.opentrendstest.characterdetails;

import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.Event;
import org.taitasciore.android.opentrendstest.BasePresenter;

import java.util.ArrayList;

/**
 * Created by roberto on 30/07/17.
 */

public interface CharacterDetailsContract {

    interface View {

        void showLoading();
        void hideLoading();
        void showComics(ArrayList<Comic> comics);
        void showNumberOfComics(int number);
        void showEvents(ArrayList<Event> events);
        void showNumberOfEvents(int number);
        void showError(Throwable error);
    }

    interface Presenter extends BasePresenter<View> {

        void getComics(int characterId);
        void getEvents(int characterId);
    }
}
