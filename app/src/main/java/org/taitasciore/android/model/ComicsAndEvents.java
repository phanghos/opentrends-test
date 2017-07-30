package org.taitasciore.android.model;

/**
 * Created by roberto on 28/07/17.
 */

public class ComicsAndEvents {

    public ComicDataContainer comicsData;
    public EventDataContainer eventsData;

    public ComicsAndEvents(ComicDataContainer comicsData, EventDataContainer eventsData) {
        this.comicsData = comicsData;
        this.eventsData = eventsData;
    }
}
