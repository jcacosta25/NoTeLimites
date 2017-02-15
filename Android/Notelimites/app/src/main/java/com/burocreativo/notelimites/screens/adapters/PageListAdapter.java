package com.burocreativo.notelimites.screens.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.BR;
import com.burocreativo.notelimites.io.models.events.VenueEvents;
import com.burocreativo.notelimites.screens.page.PageEventActivity;

import java.util.List;


/**
 * Created by Juan C. Acosta on 9/4/2016.
 * juancacosta25@gmail.com.com
 */
public class PageListAdapter extends RecyclerView.Adapter<PageListAdapter.ViewHolder> {
    private List<VenueEvents> venueEventsList;
    private Context context;

    public PageListAdapter(List<VenueEvents> venueEventsList, Context context) {
        this.venueEventsList = venueEventsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.element_page_list,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VenueEvents event = venueEventsList.get(position);
        ViewDataBinding binding = holder.getBind();

        binding.setVariable(BR.venueevent,event);
        holder.bind(event, (event1, view) -> {
            Intent intent = new Intent(context, PageEventActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("EventId", String.valueOf(event1.getEventId()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return venueEventsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding bindElement;
        private View itemView;

        ViewHolder(ViewDataBinding v) {
            super(v.getRoot());
            itemView = v.getRoot();
            bindElement= v;
            bindElement.executePendingBindings();
        }

        ViewDataBinding getBind(){
            return bindElement;
        }

        public void bind(final VenueEvents event, final OnItemClickListener listener){
            itemView.setOnClickListener(view -> listener.onItemClick(event,view));
        }
    }

    interface OnItemClickListener{
        void onItemClick(VenueEvents event,View view);
    }
}
