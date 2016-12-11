package com.burocreativo.notelimites.screens.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.models.events.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import de.morrox.fontinator.FontTextView;

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
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_event, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(eventList.get(position).getInitDate());
        holder.day.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        holder.month.setText(new SimpleDateFormat("MMM").format(cal.getTime()));
        holder.name.setText(eventList.get(position).getEventName());
        holder.place.setText(eventList.get(position).getPlaceLng());
        holder.attendants.setText(String.valueOf(eventList.get(position).getAttendings()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        FontTextView day,month,name,place,attendants;
        ViewHolder(View item) {
            super(item);
            day  = (FontTextView) item.findViewById(R.id.event_day_txt);
            month = (FontTextView) item.findViewById(R.id.event_month_txt);
            name = (FontTextView) item.findViewById(R.id.event_name_txt);
            place = (FontTextView) item.findViewById(R.id.event_place_txt);
            attendants = (FontTextView) item.findViewById(R.id.event_people_attendants);
        }
    }
}
