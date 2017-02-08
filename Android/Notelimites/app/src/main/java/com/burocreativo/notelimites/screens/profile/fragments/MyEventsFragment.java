package com.burocreativo.notelimites.screens.profile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.models.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan C. Acosta on 9/3/2016.

 * juancacosta25@gmail.com.com
 */
public class MyEventsFragment extends Fragment{

    private RecyclerView myEventRecycler;
    private List<Event> events = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_stuff, container, false);
        myEventRecycler = (RecyclerView) view.findViewById(R.id.my_stuff);
        startRecyclerView();
        return view;
    }

    private void startRecyclerView() {
        for (int i = 0; i < 4 ; i++) {
            events.add(new Event());
        }
        //EventListAdapter ea = new EventListAdapter(events,getContext());
        //myEventRecycler.clearOnChildAttachStateChangeListeners();
        //myEventRecycler.setAdapter(ea);
        //myEventRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //myEventRecycler.setHasFixedSize(true);
        //myEventRecycler.getAdapter().notifyDataSetChanged();
    }
}
