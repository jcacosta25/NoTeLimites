package com.burocreativo.notelimites.screens.page;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.databinding.ActivityPageEventBinding;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.events.VenueEvents;
import com.burocreativo.notelimites.screens.adapters.PageListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageEventActivity extends AppCompatActivity {

    private RecyclerView eventList;
    private Toolbar toolbar;
    private ActivityPageEventBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_page_event);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        eventList = (RecyclerView) findViewById(R.id.moreEventPlaceList);

        Call<Event> call = ServiceGenerator.getApiService().getEvent(this.getIntent().getStringExtra("EventId"), ServiceGenerator.authToken);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(response.isSuccessful()) {
                    binding.setEvent(response.body());
                    RecView(response.body().getVenueEvents());
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

            }
        });
    }

    public void RecView(List<VenueEvents> venueEvents){
        eventList.setAdapter(new PageListAdapter(venueEvents,getApplicationContext()));
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
