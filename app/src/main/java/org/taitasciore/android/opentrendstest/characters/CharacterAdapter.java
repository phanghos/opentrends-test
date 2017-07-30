package org.taitasciore.android.opentrendstest.characters;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding.view.RxView;

import org.taitasciore.android.model.Character;
import org.taitasciore.android.model.Image;
import org.taitasciore.android.opentrendstest.R;
import org.taitasciore.android.opentrendstest.characterdetails.CharacterDetailsActivity;
import org.taitasciore.android.opentrendstest.databinding.ItemRowLayoutBinding;

import java.util.ArrayList;

/**
 * Created by roberto on 27/07/17.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private FragmentActivity context;

    private ArrayList<Character> characters;

    public CharacterAdapter(FragmentActivity context) {
        this.context = context;
        this.characters = new ArrayList<>();
    }

    public CharacterAdapter(FragmentActivity context, ArrayList<Character> characters) {
        this.context = context;
        this.characters = characters;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRowLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_row_layout,
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.name.setText(character.getName());
        holder.description.setText(character.getDescription());
        if (character.getThumbnail() != null) {
            Image thumbnail = character.getThumbnail();
            String thumbnailPath = thumbnail.getPath() + "." + thumbnail.getExtension();
            holder.draweeView.setImageURI(Uri.parse(thumbnailPath));
        }
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void add(Character character) {
        characters.add(character);
        notifyItemInserted(getItemCount());
    }

    public void launchCharacterDetailsActivity(Character character) {
        Intent i = new Intent(context, CharacterDetailsActivity.class);
        i.putExtra("character", character);
        context.startActivity(i);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView draweeView;
        TextView name;
        TextView description;

        public ViewHolder(ItemRowLayoutBinding binding) {
            super(binding.getRoot());
            draweeView = binding.draweeView;
            name = binding.name;
            description = binding.description;
            RxView.clicks(binding.getRoot()).subscribe(x -> launchCharacterDetailsActivity(
                    characters.get(getAdapterPosition())));
        }
    }
}
