package org.taitasciore.android.networking;

import org.taitasciore.android.auth.AuthData;
import org.taitasciore.android.auth.AuthUtils;
import org.taitasciore.android.model.CharacterDataWrapper;
import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.ComicDataWrapper;
import org.taitasciore.android.model.Event;
import org.taitasciore.android.model.EventDataWrapper;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by roberto on 27/07/17.
 * This class represents the Model layer in the MVP arquitecture
 */

public class MarvelService {

    private MarvelApi mApi;

    public MarvelService(Retrofit retrofit) {
        mApi = retrofit.create(MarvelApi.class);
    }

    public Observable<CharacterDataWrapper> searchCharacters(String query) {
        AuthData authData = AuthUtils.generateHash();
        return mApi.searchCharacters(
                query,
                authData.getTimestamp(),
                MarvelApi.API_PUBLIC_KEY,
                authData.getHash());
    }

    public Observable<ComicDataWrapper> getComics(int id) {
        AuthData authData = AuthUtils.generateHash();
        return mApi.getComics(id, authData.getTimestamp(),
                MarvelApi.API_PUBLIC_KEY, authData.getHash());
    }

    public Observable<EventDataWrapper> getEvents(int id) {
        AuthData authData = AuthUtils.generateHash();
        return mApi.getEvents(id, authData.getTimestamp(),
                MarvelApi.API_PUBLIC_KEY, authData.getHash());
    }
}
