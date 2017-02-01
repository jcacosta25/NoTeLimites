package com.burocreativo.notelimites.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.Filterable;
import android.widget.TextView;

import com.burocreativo.notelimites.R;

/**
 * Created by Juan C. Acosta on 1/31/2017.
 */

public class SearchFeedResultsAdapter extends SimpleCursorAdapter implements Filterable {

    public static final String TAG = SearchFeedResultsAdapter.class.getSimpleName();
    private Context context = null;
    public Cursor cursor;

    public SearchFeedResultsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.context = context;
    }

    @Override
    public int getCount() {
        if(getCursor() == null)
            return 0;
        return getCursor().getCount();
    }


    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
            this.cursor = cursor;
            TextView textView = (TextView) view.findViewById(R.id.feed_url_text);
            textView.setText(cursor.getString(1));
    }
}
