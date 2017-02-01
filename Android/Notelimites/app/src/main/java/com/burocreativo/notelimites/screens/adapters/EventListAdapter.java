package com.burocreativo.notelimites.screens.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.screens.page.PageEventActivity;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.morrox.fontinator.FontTextView;

/**
 * Created by Juan C. Acosta on 9/3/2016.
 * bbxmstudios
 * juan.acosta@bbxmstudios.com
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_event, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(filterList.get(position).getInitDate());
        holder.day.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        holder.month.setText(new SimpleDateFormat("MMM").format(cal.getTime()));
        holder.name.setText(eventList.get(position).getEventName());
        holder.place.setText(filterList.get(position).getVenueName());
        holder.attendants.setText(String.valueOf(filterList.get(position).getAttendings()));
        Glide.with(context)
                .load(filterList.get(position).getImageURL())
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .into(holder.background);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PageEventActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("EventId", String.valueOf(filterList.get(position).getEventID()));
                context.startActivity(intent);
            }
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                filterList.clear();
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
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        FontTextView day,month,name,place,attendants;
        ImageView background;
        View item;
        ViewHolder(View item) {
            super(item);
            day  = (FontTextView) item.findViewById(R.id.event_day_txt);
            month = (FontTextView) item.findViewById(R.id.event_month_txt);
            name = (FontTextView) item.findViewById(R.id.event_name_txt);
            place = (FontTextView) item.findViewById(R.id.event_place_txt);
            attendants = (FontTextView) item.findViewById(R.id.event_people_attendants);
            background = (ImageView) item.findViewById(R.id.event_image);
            this.item = item;
        }
    }
}
