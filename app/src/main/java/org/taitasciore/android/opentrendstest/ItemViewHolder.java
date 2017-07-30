package org.taitasciore.android.opentrendstest;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.taitasciore.android.opentrendstest.databinding.ItemRowLayoutBinding;

/**
 * Created by roberto on 28/07/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView draweeView;
    public TextView name;
    public TextView description;

    public ItemViewHolder(ItemRowLayoutBinding binding) {
        super(binding.getRoot());
        draweeView = binding.draweeView;
        name = binding.name;
        description = binding.description;
    }
}
