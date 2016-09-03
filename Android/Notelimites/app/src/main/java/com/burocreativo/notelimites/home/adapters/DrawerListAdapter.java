package com.burocreativo.notelimites.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.burocreativo.notelimites.R;

import java.util.List;

/**
 * Created by Juan C. Acosta on 9/3/2016.
 * bbxmstudios
 * juan.acosta@bbxmstudios.com
 */
public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private List<DrawerItem> items;

    public DrawerListAdapter(Context context, List<DrawerItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_drawer, null);
        }


        ImageView icon = (ImageView) convertView.findViewById(R.id.drawer_icon);
        TextView name = (TextView) convertView.findViewById(R.id.drawer_name);

        icon.setImageResource(items.get(position).getIconId());
        name.setText(items.get(position).getName());

        return convertView;
    }
}