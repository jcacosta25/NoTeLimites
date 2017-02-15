package com.burocreativo.notelimites.screens.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.burocreativo.notelimites.BR;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.screens.page.PageEventActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan C. Acosta on 9/3/2016.
 * juancacosta25@gmail.com.com
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private List<Event> eventList, filterList;
    private Context context;

    public EventListAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
        this.filterList = new ArrayList<>();
        this.filterList.addAll(eventList);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.element_list_event,parent,false);
        return  new ViewHolder(binding);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Event event = filterList.get(position);
        ViewDataBinding binding = holder.getBind();
        binding.setVariable(BR.event,event);
        holder.bind(event,(event1,view)->{
            Intent intent = new Intent(context, PageEventActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("EventId", String.valueOf(event.getEventID()));
            context.startActivity(intent);
        });
    }

    public LatLng getEventLocation(int position) {
        Event event = filterList.get(position);
        return new LatLng(Double.parseDouble(event.getPlaceLat()), Double.parseDouble(event.getPlaceLng()));
    }

    @Override
    public int getItemCount() {
        return (null != filterList ? filterList.size() : 0);
    }

    public void filter(final int cat) {
        filterList = new ArrayList<>();
        notifyDataSetChanged();
        if(cat == 2){
            filterList.addAll(eventList);
        } else {

            if(cat>2){
                Stream.of(eventList)
                        .filter(event -> event.getEventtypeID() == cat -1)
                        .forEach(event -> {
                            filterList.add(event);
                            notifyDataSetChanged();
                        });
            } else {
                Stream.of(eventList)
                        .filter(event -> event.getEventtypeID() == cat +1)
                        .forEach(event -> {
                            filterList.add(event);
                            notifyDataSetChanged();
                        });
            }

        }
       /* filterList.clear();
        if(cat == 2){
            filterList.addAll(eventList);
        } else {
            Stream.of(eventList)
                    .filter(event -> {
                        int category = cat+1;
                        if(cat > 2)
                            category = cat -1;
                        return event.getEventtypeID() == category;
                    }).forEach(event -> {
                filterList.add(event);
                notifyDataSetChanged();

            });
        }*/



        /*new Thread(() -> {
            if (cat == 2) {
                filterList.addAll(eventList);
            } else {
                int category = cat + 1;
                if (cat > 2) {
                    category = cat - 1;
                }
                for (Event event : eventList) {
                    if (event.getEventtypeID() == category) {
                        filterList.add(event);
                    }
                }
            }

            // Set on UI Thread
            ((Activity) context).runOnUiThread(() -> {
                // Notify the List that the DataSet has changed...
                notifyDataSetChanged();
            });
        }).start();*/
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ViewDataBinding bindElement;
        private View itemView;

        ViewHolder(ViewDataBinding v) {
            super(v.getRoot());
            itemView = v.getRoot();
            bindElement=v;
            bindElement.executePendingBindings();
        }

        ViewDataBinding getBind() {return bindElement;}

        public void bind(final Event event, final OnItemClickListener listener){
            itemView.setOnClickListener(view -> listener.onItemClick(event,view));
        }
    }

    interface OnItemClickListener{
        void onItemClick(Event event, View view);
    }
}
