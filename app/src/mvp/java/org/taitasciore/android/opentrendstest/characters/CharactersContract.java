package org.taitasciore.android.opentrendstest.characters;

import org.taitasciore.android.model.Character;
import org.taitasciore.android.opentrendstest.BasePresenter;

import java.util.ArrayList;

/**
 * Created by roberto on 29/07/17.
 */

public interface CharactersContract {

    interface View {

        void showLoading();
        void hideLoading();
        void showEmptyList();
        void showCharacters(ArrayList<Character> characters);
        void showError(Throwable error);
    }

    interface Presenter extends BasePresenter<View> {

        void searchCharacters(String query);
    }
}
