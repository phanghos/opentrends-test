package org.taitasciore.android.opentrendstest.characters;

import org.taitasciore.android.model.Character;

import java.util.ArrayList;

/**
 * Created by roberto on 28/07/17.
 */

public interface CharactersViewState {

    final class Empty implements CharactersViewState{}

    final class Success implements CharactersViewState {

        public ArrayList<Character> characters;

        public Success(ArrayList<Character> characters) {
            this.characters = characters;
        }
    }

    final class Error implements CharactersViewState {

        public Throwable error;

        public Error(Throwable error) {
            this.error = error;
        }
    }
}
