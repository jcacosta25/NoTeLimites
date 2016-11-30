package com.burocreativo.notelimites.screens.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.models.Page;

import java.util.List;

/**
 * Created by Juan C. Acosta on 9/4/2016.
 * bbxmstudios
 * juan.acosta@bbxmstudios.com
 */
public class PageListAdapter extends RecyclerView.Adapter<PageListAdapter.ViewHolder> {
    private List<Page> pageList;
    private Context context;

    public PageListAdapter(List<Page> pageList, Context context) {
        this.pageList = pageList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_page_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
