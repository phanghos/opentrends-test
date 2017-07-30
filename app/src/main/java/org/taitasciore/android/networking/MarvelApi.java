package org.taitasciore.android.networking;

import org.taitasciore.android.model.CharacterDataWrapper;
import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.ComicDataWrapper;
import org.taitasciore.android.model.Event;
import org.taitasciore.android.model.EventDataWrapper;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by roberto on 27/07/17.
 * Marvel API
 */

public interface MarvelApi {

    String BASE_URL = "http://gateway.marvel.com/v1/public/"; // Marvel API base URL
    String API_PUBLIC_KEY = "d5227a377cc68762ff9ca30d1352341b"; // Marvel API public key
    String API_PRIVATE_KEY = "5d47fa53aa486084ed3caf13161b813e13558102"; // Marvel API private key

    /**
     * This method searches for characters with names starting with @param{query}
     * @param query Query string
     * @param ts Unique timestamp value
     * @param apiKey Marvel API public key
     * @param hash MD5-encrypted hash
     * @return List of characters matching query
     */
    @GET("characters")
    Observable<CharacterDataWrapper> searchCharacters(
            @Query("nameStartsWith") String query, @Query("ts") long ts,
            @Query("apikey") String apiKey, @Query("hash") String hash);

    /**
     * This method fetches a list of comicsData of a given character (by ID)
     * @param characterId Character ID
     * @param ts Unique timestamp value
     * @param apiKey Marvel API public key
     * @param hash MD5-encrypted hash
     * @return List of comicsData of the given character
     */
    @GET("characters/{characterId}/comics")
    Observable<ComicDataWrapper> getComics(
            @Path("characterId") int characterId, @Query("ts") long ts,
            @Query("apikey") String apiKey, @Query("hash") String hash);

    /**
     * This method fetches a list of eventsData of a given character (by ID)
     * @param characterId Character ID
     * @param ts Unique timestamp value
     * @param apiKey Marvel API public key
     * @param hash MD5-encrypted hash
     * @return List of eventsData of the given character
     */
    @GET("characters/{characterId}/events")
    Observable<EventDataWrapper> getEvents(
            @Path("characterId") int characterId, @Query("ts") long ts,
            @Query("apikey") String apiKey, @Query("hash") String hash);
}
