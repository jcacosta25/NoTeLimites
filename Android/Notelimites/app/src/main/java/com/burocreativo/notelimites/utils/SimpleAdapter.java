package com.burocreativo.notelimites.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.models.discover.Discover;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Juan C. Acosta on 5/25/2017.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

  private final Context mContext;
  private List<Discover> mData;

  public void add(Discover s,int position) {
    position = position == -1 ? getItemCount()  : position;
    mData.add(position,s);
    notifyItemInserted(position);
  }

  public void remove(int position){
    if (position < getItemCount()  ) {
      mData.remove(position);
      notifyItemRemoved(position);
    }
  }

  public static class SimpleViewHolder extends RecyclerView.ViewHolder {
    public final TextView title;

    public SimpleViewHolder(View view) {
      super(view);
      title = (TextView) view.findViewById(R.id.simple_text);
    }
  }

  public SimpleAdapter(Context context, List<Discover> data) {
    mContext = context;
    if (data != null)
      mData = new ArrayList<Discover>(data);
    else mData = new ArrayList<Discover>();
  }

  public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    final View view = LayoutInflater.from(mContext).inflate(R.layout.simple_item, parent, false);
    return new SimpleViewHolder(view);
  }

  @Override
  public void onBindViewHolder(SimpleViewHolder holder, final int position) {
    holder.title.setText(mData.get(position).getTitle());
    holder.title.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(mContext,"Position ="+position,Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }
}