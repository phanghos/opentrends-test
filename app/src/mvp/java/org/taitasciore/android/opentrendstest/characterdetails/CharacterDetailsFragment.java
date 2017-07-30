package org.taitasciore.android.opentrendstest.characterdetails;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.taitasciore.android.di.component.CharacterDetailsComponent;
import org.taitasciore.android.di.component.DaggerCharacterDetailsComponent;
import org.taitasciore.android.di.module.CharacterDetailsModule;
import org.taitasciore.android.model.Character;
import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.Event;
import org.taitasciore.android.model.Image;
import org.taitasciore.android.opentrendstest.ActivityUtils;
import org.taitasciore.android.opentrendstest.OpentrendsApp;
import org.taitasciore.android.opentrendstest.R;
import org.taitasciore.android.opentrendstest.databinding.FragmentCharacterDetailsBinding;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by roberto on 29/07/17.
 * Character details' screen
 */

public class CharacterDetailsFragment extends Fragment implements CharacterDetailsContract.View {

    private Character character;

    @Inject CharacterDetailsContract.Presenter presenter;

    private ComicAdapter comicAdapter;

    private EventAdapter eventAdapter;

    private ProgressDialog progressDialog;

    FragmentCharacterDetailsBinding binding;

    public static CharacterDetailsFragment newInstance(Character character) {
        CharacterDetailsFragment f = new CharacterDetailsFragment();
        Bundle extras = new Bundle();
        extras.putSerializable("character", character);
        f.setArguments(extras);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject dependencies
        OpentrendsApp app = (OpentrendsApp) getActivity().getApplication();
        CharacterDetailsComponent component = DaggerCharacterDetailsComponent.builder()
                .networkComponent(app.getNetworkComponent())
                .characterDetailsModule(new CharacterDetailsModule())
                .build();
        component.inject(this);

        // Retrieve serializable from extras
        character = (Character) getArguments().getSerializable("character");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false);
        binding.list.setNestedScrollingEnabled(false);
        addListeners();
        showCharacterInfo(character);
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
     * Dismiss progress dialog before fragment goes to background
     */
    @Override
    public void onStop() {
        super.onStop();
        hideLoading();
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
     * Add onclick listeners to buttons
     */
    private void addListeners() {
        binding.btnDetails.setOnClickListener(v -> {
            if (character.getUrls() != null && character.getUrls().size() > 0) {
                String url = character.getUrls().get(0).getUrl();
                ActivityUtils.launchBrowser(getContext(), url);
            }
        });

        binding.btnWiki.setOnClickListener(v -> {
            if (character.getUrls() != null && character.getUrls().size() > 1) {
                String url = character.getUrls().get(0).getUrl();
                ActivityUtils.launchBrowser(getContext(), url);
            }
        });

        binding.btnComicsDetails.setOnClickListener(v -> {
            if (character.getUrls() != null && character.getUrls().size() > 2) {
                String url = character.getUrls().get(0).getUrl();
                ActivityUtils.launchBrowser(getContext(), url);
            }
        });

        binding.btnComics.setOnClickListener(v -> presenter.getComics(character.getId()));

        binding.btnEvents.setOnClickListener(v -> presenter.getEvents(character.getId()));
    }

    /**
     * Show character info (image, name, description)
     * @param character Character
     */
    private void showCharacterInfo(Character character) {
        binding.characterDetails.name.setText(character.getName());
        binding.characterDetails.description.setText(character.getDescription());
        showThumbnail(character.getThumbnail());
    }

    /**
     * Show character thumbnail if != null
     * @param thumbnail Thumbnail
     */
    private void showThumbnail(Image thumbnail) {
        if (thumbnail != null) {
            String thumbnailPath = thumbnail.getPath() + "." + thumbnail.getExtension();
            binding.characterDetails.draweeView.setImageURI(Uri.parse(thumbnailPath));
        }
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(getActivity(), "",
                getString(R.string.msg_loading), true, false);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()
                && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showComics(ArrayList<Comic> comics) {
        comicAdapter = new ComicAdapter(comics);
        binding.list.setAdapter(comicAdapter);
    }

    @Override
    public void showNumberOfComics(int number) {
        binding.btnComics.setText("(" + number + ") COMICS");
    }

    @Override
    public void showEvents(ArrayList<Event> events) {
        eventAdapter = new EventAdapter(events);
        binding.list.setAdapter(eventAdapter);
    }

    @Override
    public void showNumberOfEvents(int number) {
        binding.btnEvents.setText("(" + number + ") EVENTOS");
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
