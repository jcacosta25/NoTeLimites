package com.burocreativo.notelimites.screens.page;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.screens.adapters.PageListAdapter;
import com.burocreativo.notelimites.io.models.Page;

import java.util.ArrayList;
import java.util.List;

public class PageEventActivity extends AppCompatActivity {

    private RecyclerView eventList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_event);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        eventList = (RecyclerView) findViewById(R.id.moreEventPlaceList);

        RecView();
    }

    public void RecView(){
        List<Page> events = new ArrayList<>();
        for (int i = 0; i < 4 ; i++) {
            events.add(new Page());
        }
        eventList.setAdapter(new PageListAdapter(events,getApplicationContext()));
        eventList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        eventList.setHasFixedSize(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
