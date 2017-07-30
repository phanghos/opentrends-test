package org.taitasciore.android.opentrendstest;

/**
 * Created by roberto on 29/07/17.
 * Base presenter interface which any presenter must implement
 */

public interface BasePresenter<V> {

    void attachView(V view);
    void detachView();
}
