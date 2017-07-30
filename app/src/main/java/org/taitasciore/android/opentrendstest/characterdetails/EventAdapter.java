package org.taitasciore.android.opentrendstest.characterdetails;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.taitasciore.android.model.Event;
import org.taitasciore.android.model.Image;
import org.taitasciore.android.opentrendstest.EmptyViewHolder;
import org.taitasciore.android.opentrendstest.R;
import org.taitasciore.android.opentrendstest.ItemViewHolder;
import org.taitasciore.android.opentrendstest.databinding.ItemRowLayoutBinding;

import java.util.ArrayList;

/**
 * Created by roberto on 28/07/17.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Event> events;

    public EventAdapter(ArrayList<Event> events) {
        this.events = events;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (events.isEmpty()) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.empty_row_layout, parent, false);
            return new EmptyViewHolder(v);
        } else {
            ItemRowLayoutBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.item_row_layout,
                    parent,
                    false);
            return new ItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (events.isEmpty()) return;
        Event event = events.get(position);
        ItemViewHolder holder = (ItemViewHolder) vh;
        holder.name.setText(event.getTitle());
        holder.description.setText(event.getDescription());
        if (event.getThumbnail() != null) {
            Image thumbnail = event.getThumbnail();
            String thumbnailPath = thumbnail.getPath() + "." + thumbnail.getExtension();
            holder.draweeView.setImageURI(Uri.parse(thumbnailPath));
        }
    }

    @Override
    public int getItemCount() {
        return events.isEmpty() ? 1 : events.size();
    }
}
