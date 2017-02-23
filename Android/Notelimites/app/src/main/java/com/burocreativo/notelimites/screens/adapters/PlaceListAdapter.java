package com.burocreativo.notelimites.screens.adapters;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burocreativo.notelimites.BR;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.models.relationship.RelationVenue;
import com.burocreativo.notelimites.screens.page.PagePlaceActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan C. Acosta on 9/3/2016.
 * juancacosta25@gmail.com.com
 */
public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ViewHolder> {

    private List<RelationVenue> placeList = new ArrayList<>();
    private Context context;

    public PlaceListAdapter(List<RelationVenue> placeList, Context context) {
        this.placeList = placeList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.element_list_place,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RelationVenue venue = placeList.get(position);
        ViewDataBinding binding = holder.getBind();
        binding.setVariable(BR.relationVenue,venue);
        holder.bind(venue, (venue1, view) -> {
            Intent intent = new Intent(context, PagePlaceActivity.class);
            intent.putExtra("VenueId",String.valueOf(venue1.getVenueID()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        private ViewDataBinding  bindElement;
        private View itemView;
        public ViewHolder(ViewDataBinding v) {
            super(v.getRoot());
            itemView = v.getRoot();
            bindElement=v;
            bindElement.executePendingBindings();
        }

        ViewDataBinding getBind(){return bindElement;}

        public void bind(final RelationVenue venue, final OnItemClickListener listener){
            itemView.setOnClickListener(view -> listener.onItemClick(venue,view));
        }
    }

    interface OnItemClickListener{
        void onItemClick(RelationVenue venue, View view);
    }

}
