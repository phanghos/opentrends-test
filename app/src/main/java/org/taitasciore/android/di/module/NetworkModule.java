package org.taitasciore.android.di.module;

import org.taitasciore.android.networking.MarvelApi;
import org.taitasciore.android.networking.MarvelService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by roberto on 27/07/17.
 */

@Module
public class NetworkModule {

    @Provides @Singleton
    public Retrofit provideRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MarvelApi.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    @Provides @Singleton
    public MarvelService provideMarvelService(Retrofit retrofit) {
        return new MarvelService(retrofit);
    }
}
