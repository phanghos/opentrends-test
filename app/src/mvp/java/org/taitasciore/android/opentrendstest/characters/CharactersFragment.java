package org.taitasciore.android.opentrendstest.characters;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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

/**
 * Created by roberto on 29/07/17.
 * Characters list (search) screen
 */

public class CharactersFragment extends Fragment implements CharactersContract.View {

    @Inject CharactersContract.Presenter presenter;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false);
        addTextListener(binding.etSearch);
        return binding.getRoot();
    }

    /**
     * Attach view to presenter
     */
    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    /**
     * Detach view from presenter
     */
    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

    /**
     * Add text change listener to text field
     * @param et EditText
     */
    private void addTextListener(EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.searchCharacters(editable.toString());
            }
        });
    }

    @Override
    public void showLoading() {
        binding.progressWheel.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.progressWheel.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyList() {
        adapter = new CharacterAdapter(getActivity());
        binding.list.setAdapter(adapter);
    }

    @Override
    public void showCharacters(ArrayList<Character> characters) {
        adapter = new CharacterAdapter(getActivity(), characters);
        binding.list.setAdapter(adapter);
    }

    @Override
    public void showError(Throwable error) {
        String msg;

        if (error instanceof UnknownHostException) {
            msg = getString(R.string.error_no_connection);
        } else {
            msg = getString(R.string.error_request_failed);
        }

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
