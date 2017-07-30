package org.taitasciore.android.opentrendstest.characterdetails;

import org.taitasciore.android.model.ComicDataContainer;
import org.taitasciore.android.model.EventDataContainer;

/**
 * Created by roberto on 28/07/17.
 */

public interface CharacterDetailsViewState {

    final class SuccessComics implements CharacterDetailsViewState {

        public ComicDataContainer comicsContainer;

        public SuccessComics(ComicDataContainer comicsContainer) {
            this.comicsContainer = comicsContainer;
        }
    }

    final class SuccessEvents implements CharacterDetailsViewState {

        public EventDataContainer eventsContainer;

        public SuccessEvents(EventDataContainer eventsContainer) {
            this.eventsContainer = eventsContainer;
        }
    }

    final class Error implements CharacterDetailsViewState {

        public Throwable error;

        public Error(Throwable error) {
            this.error = error;
        }
    }
}
