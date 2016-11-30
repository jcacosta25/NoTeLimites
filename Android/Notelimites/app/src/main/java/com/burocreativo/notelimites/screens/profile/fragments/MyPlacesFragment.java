package com.burocreativo.notelimites.screens.profile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.screens.adapters.PlaceListAdapter;
import com.burocreativo.notelimites.io.models.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan C. Acosta on 9/3/2016.
 * bbxmstudios
 * juan.acosta@bbxmstudios.com
 */
public class MyPlacesFragment extends Fragment{

    private RecyclerView myPlaceRecycler;
    private List<Place> places = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_stuff, container, false);
        myPlaceRecycler = (RecyclerView) view.findViewById(R.id.my_stuff);
        startRecyclerView();
        return view;
    }

    private void startRecyclerView() {
        for (int i = 0; i < 4 ; i++) {
            places.add(new Place());
        }
        PlaceListAdapter pa = new PlaceListAdapter(places,getContext());
        myPlaceRecycler.clearOnChildAttachStateChangeListeners();
        myPlaceRecycler.setAdapter(pa);
        myPlaceRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myPlaceRecycler.setHasFixedSize(true);
        myPlaceRecycler.getAdapter().notifyDataSetChanged();
    }
}
