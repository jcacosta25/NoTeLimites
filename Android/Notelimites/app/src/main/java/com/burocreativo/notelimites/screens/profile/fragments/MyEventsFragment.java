package com.burocreativo.notelimites.screens.profile.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burocreativo.notelimites.NTLApplication;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.relationship.UserFollowedEvent;
import com.burocreativo.notelimites.screens.adapters.EventListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Call<UserFollowedEvent> userFollow = ServiceGenerator.getApiService().userFollowedEvents(NTLApplication.tinyDB.getString("user_id"));
        userFollow.enqueue(new Callback<UserFollowedEvent>() {
            @Override
            public void onResponse(Call<UserFollowedEvent> call, Response<UserFollowedEvent> response) {
                if(response.isSuccessful()){
                    EventListAdapter adapter = new EventListAdapter(response.body().getRelationEvents(),getContext());
                    myEventRecycler.clearOnChildAttachStateChangeListeners();
                    myEventRecycler.setAdapter(adapter);
                    myEventRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    myEventRecycler.setHasFixedSize(true);
                    myEventRecycler.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserFollowedEvent> call, Throwable t) {

            }
        });
    }
}
