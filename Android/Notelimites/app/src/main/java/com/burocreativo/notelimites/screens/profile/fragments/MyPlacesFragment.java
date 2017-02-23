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
import com.burocreativo.notelimites.io.models.relationship.RelationVenue;
import com.burocreativo.notelimites.io.models.relationship.UserFollowedVenues;
import com.burocreativo.notelimites.screens.adapters.PlaceListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juan C. Acosta on 9/3/2016.
 * juancacosta25@gmail.com.com
 */
public class MyPlacesFragment extends Fragment{

    private RecyclerView myPlaceRecycler;
    private List<RelationVenue> places = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_stuff, container, false);
        myPlaceRecycler = (RecyclerView) view.findViewById(R.id.my_stuff);
        startRecyclerView();
        return view;
    }

    private void startRecyclerView() {
        Call<UserFollowedVenues> userFollow = ServiceGenerator.getApiService().userFollowedVenues(NTLApplication.tinyDB.getString("user_id"));
        userFollow.enqueue(new Callback<UserFollowedVenues>() {
            @Override
            public void onResponse(Call<UserFollowedVenues> call, Response<UserFollowedVenues> response) {
                if(response.isSuccessful()){
                    places = response.body().getRelationVenues();
                    PlaceListAdapter pa = new PlaceListAdapter(places,getContext());
                    myPlaceRecycler.setAdapter(pa);
                    myPlaceRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    myPlaceRecycler.setHasFixedSize(true);
                    myPlaceRecycler.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserFollowedVenues> call, Throwable t) {

            }
        });

    }
}
