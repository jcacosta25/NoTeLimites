package com.burocreativo.notelimites.utils;

import android.content.ClipData.Item;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.utils.ItemListAdapter.ItemViewHolder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Juan C. Acosta on 5/24/2017.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemViewHolder> {
  private final Context context;
  private List<String> listData;

  public void add(String s, int position){
    position =  position == -1? getItemCount() : position;
    listData.add(position,s);
    notifyItemInserted(position);
  }

  public void remove(int position){
    if(position < getItemCount()) {
      listData.remove(position);
      notifyItemRemoved(position);
    }
  }

  public static class ItemViewHolder extends RecyclerView.ViewHolder{
    public final TextView title;

    public ItemViewHolder(View view) {
      super(view);
      title = (TextView) view.findViewById(R.id.section_text);
    }
  }

  public ItemListAdapter(Context context, String[] listData) {
    this.context = context;
    if(listData != null){
      this.listData = new ArrayList<String>(Arrays.asList(listData));
    } else {
      this.listData = new ArrayList<String>();
    }
  }

  public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(context).inflate(R.layout.section,parent,false);
    return new ItemViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ItemViewHolder holder, final int position) {
    holder.title.setText(listData.get(position));
    holder.title.setOnClickListener(v -> {
      Toast.makeText(context,"Position = " +position,Toast.LENGTH_SHORT).show();
    });
  }

  @Override
  public int getItemCount(){
    return listData.size();
}

}