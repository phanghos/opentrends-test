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

import com.jakewharton.rxbinding.view.RxView;

import org.taitasciore.android.di.component.CharacterDetailsComponent;
import org.taitasciore.android.di.component.DaggerCharacterDetailsComponent;
import org.taitasciore.android.di.module.CharacterDetailsModule;
import org.taitasciore.android.model.Character;
import org.taitasciore.android.model.ComicDataContainer;
import org.taitasciore.android.model.EventDataContainer;
import org.taitasciore.android.model.Image;
import org.taitasciore.android.opentrendstest.ActivityUtils;
import org.taitasciore.android.opentrendstest.OpentrendsApp;
import org.taitasciore.android.opentrendstest.R;
import org.taitasciore.android.opentrendstest.databinding.FragmentCharacterDetailsBinding;

import java.net.UnknownHostException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by roberto on 28/07/17.
 * Character details' screen
 */

public class CharacterDetailsFragment extends Fragment {

    private Character character;

    @Inject CharacterDetailsViewModel viewModel;

    private CompositeDisposable compositeDisposables;

    private ProgressDialog progressDialog;

    private FragmentCharacterDetailsBinding binding;

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
        initSubscriptions();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_character_details,
                container,
                false);
        binding.list.setNestedScrollingEnabled(false);
        initListeners();
        showCharacterInfo(character);
        return binding.getRoot();
    }

    /**
     * Restore previous state
     */
    @Override
    public void onResume() {
        super.onResume();
        viewModel.restoreState();
    }

    /**
     * Dismiss progress dialog before fragment goes to background
     */
    @Override
    public void onStop() {
        super.onStop();
        renderLoading(false);
    }

    /**
     * Unsubscribe from observables before destroying fragment
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposables.dispose();
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

    /**
     * Subscribe to observables
     */
    private void initSubscriptions() {
        compositeDisposables = new CompositeDisposable();

        compositeDisposables.add(viewModel.getStateObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> render(state)));

        compositeDisposables.add(viewModel.loading().observeOn(AndroidSchedulers.mainThread())
                .subscribe(bool -> renderLoading(bool)));
    }

    /**
     * Subscribe to UI events (onclick listeners)
     */
    private void initListeners() {
        RxView.clicks(binding.btnDetails).subscribe(x -> onClickedDetails(character));

        RxView.clicks(binding.btnWiki).subscribe(x -> onClickedWiki(character));

        RxView.clicks(binding.btnComicsDetails).subscribe(x -> onClickedComics(character));

        RxView.clicks(binding.btnComics).subscribe(x -> viewModel.getComics(character.getId()).subscribe());

        RxView.clicks(binding.btnEvents).subscribe(x -> viewModel.getEvents(character.getId()).subscribe());
    }

    /**
     * Render given state
     * @param viewState State
     */
    private void render(CharacterDetailsViewState viewState) {
        if (viewState instanceof CharacterDetailsViewState.SuccessComics) {
            renderComics(((CharacterDetailsViewState.SuccessComics) viewState).comicsContainer);
        } else if (viewState instanceof CharacterDetailsViewState.SuccessEvents) {
            renderEvents(((CharacterDetailsViewState.SuccessEvents) viewState).eventsContainer);
        } else if (viewState instanceof CharacterDetailsViewState.Error) {
            renderError(((CharacterDetailsViewState.Error) viewState).error);
        }
    }

    /**
     * Show list of comics and number of items retrieved
     * @param comicDataContainer Comic data
     */
    private void renderComics(ComicDataContainer comicDataContainer) {
        viewModel.setComics(comicDataContainer);
        binding.btnComics.setText("(" + comicDataContainer.getTotal() + ") COMICS");
        ComicAdapter adapter = new ComicAdapter(comicDataContainer.getResults());
        binding.list.setAdapter(adapter);
    }

    /**
     * Show list of events and number of items retrieved
     * @param eventDataContainer Event data
     */
    private void renderEvents(EventDataContainer eventDataContainer) {
        viewModel.setEvents(eventDataContainer);
        binding.btnEvents.setText("(" + eventDataContainer.getTotal() + ") EVENTOS");
        EventAdapter adapter = new EventAdapter(eventDataContainer.getResults());
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

    /**
     * Show/hide loading icon
     * @param show If true, show. Hide otherwise
     */
    private void renderLoading(boolean show) {
        if (show) {
            progressDialog = ProgressDialog.show(getActivity(), "",
                    getString(R.string.msg_loading), true, false);
        } else if (progressDialog != null && progressDialog.isShowing()
                && !getActivity().isFinishing() && !getActivity().isDestroyed()) {
            progressDialog.dismiss();
        }
    }

    private void onClickedDetails(Character character) {
        if (character.getUrls() != null && character.getUrls().size() > 0) {
            String url = character.getUrls().get(0).getUrl();
            ActivityUtils.launchBrowser(getContext(), url);
        }
    }

    private void onClickedWiki(Character character) {
        if (character.getUrls() != null && character.getUrls().size() > 1) {
            String url = character.getUrls().get(1).getUrl();
            ActivityUtils.launchBrowser(getContext(), url);
        }
    }

    private void onClickedComics(Character character) {
        if (character.getUrls() != null && character.getUrls().size() > 2) {
            String url = character.getUrls().get(2).getUrl();
            ActivityUtils.launchBrowser(getContext(), url);
        }
    }
}
