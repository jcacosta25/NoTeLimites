package com.burocreativo.notelimites.screens.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.models.Event;
import com.burocreativo.notelimites.screens.page.PageEventActivity;

import java.util.List;

/**
 * Created by Juan C. Acosta on 9/3/2016.
 * bbxmstudios
 * juan.acosta@bbxmstudios.com
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private List<Event> eventList;
    private Context context;

    public EventListAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_event, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PageEventActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
