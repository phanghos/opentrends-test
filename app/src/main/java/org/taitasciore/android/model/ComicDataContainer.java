package org.taitasciore.android.model;

import java.util.ArrayList;

/**
 * Created by roberto on 10/05/17.
 */

public class ComicDataContainer {

    private int total;
    private ArrayList<Comic> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Comic> getResults() {
        return results;
    }

    public void setResults(ArrayList<Comic> results) {
        this.results = results;
    }
}
