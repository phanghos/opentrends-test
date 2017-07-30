package org.taitasciore.android.opentrendstest.characters;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import org.taitasciore.android.di.component.CharactersComponent;
import org.taitasciore.android.di.component.DaggerCharactersComponent;
import org.taitasciore.android.di.module.CharactersModule;
import org.taitasciore.android.model.Character;
import org.taitasciore.android.opentrendstest.OpentrendsApp;
import org.taitasciore.android.opentrendstest.R;
import org.taitasciore.android.opentrendstest.databinding.FragmentCharactersBinding;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by roberto on 27/07/17.
 * Characters list (search) screen
 */

public class CharactersFragment extends Fragment {

    @Inject CharactersViewModel viewModel;

    private CompositeDisposable disposables;

    private Disposable searchDisposable;

    private CharacterAdapter adapter;

    private FragmentCharactersBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject dependencies
        OpentrendsApp app = (OpentrendsApp) getActivity().getApplication();
        CharactersComponent component = DaggerCharactersComponent.builder()
                .networkComponent(app.getNetworkComponent())
                .charactersModule(new CharactersModule())
                .build();
        component.inject(this);

        initSubscriptions();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false);
        showEmptyList();
        addTextListener();
        return binding.getRoot();
    }

    /**
     * Unsubscribe from observables before destroying fragment
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }

    /**
     * Subscribe to observables
     */
    private void initSubscriptions() {
        disposables = new CompositeDisposable();

        disposables.add(viewModel.getStateObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewState -> render(viewState)));

        disposables.add(viewModel.loading().observeOn(AndroidSchedulers.mainThread())
                .subscribe(bool -> binding.progressWheel.setVisibility(bool ?
                        View.VISIBLE : View.GONE)));
    }

    private void showEmptyList() {
        adapter = new CharacterAdapter(getActivity());
        binding.list.setAdapter(adapter);
    }

    /**
     * Subscribe to changes in text field
     */
    private void addTextListener() {
        RxTextView.textChanges(binding.etSearch)
                .subscribe(query -> {
                    if (searchDisposable != null && !searchDisposable.isDisposed()) {
                        searchDisposable.dispose();
                    }
                    searchDisposable = viewModel.searchCharacters(query+"").subscribe();
                });
    }

    /**
     * Render given state
     * @param viewState State
     */
    private void render(CharactersViewState viewState) {
        if (viewState instanceof CharactersViewState.Empty) {
            showEmptyList();
        } else if (viewState instanceof CharactersViewState.Success) {
            renderCharacters(((CharactersViewState.Success) viewState).characters);
        } else if (viewState instanceof CharactersViewState.Error) {
            renderError(((CharactersViewState.Error) viewState).error);
        }
    }

    /**
     * Show list of characters
     * @param characters List of characters
     */
    private void renderCharacters(ArrayList<Character> characters) {
        adapter = new CharacterAdapter(getActivity(), characters);
        binding.list.setAdapter(adapter);
    }

    /**
     * Show error message
     * @param error Error
     */
    private void renderError(Throwable error) {
        String msg;

        if (error instanceof UnknownHostException) {
            msg = getString(R.string.error_no_connection);
        } else {
            msg = getString(R.string.error_request_failed);
        }

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
