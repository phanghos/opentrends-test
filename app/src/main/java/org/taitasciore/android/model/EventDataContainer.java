package org.taitasciore.android.model;

import java.util.ArrayList;

/**
 * Created by roberto on 28/07/17.
 */

public class EventDataContainer {

    private int total;
    private ArrayList<Event> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Event> getResults() {
        return results;
    }

    public void setResults(ArrayList<Event> results) {
        this.results = results;
    }
}
