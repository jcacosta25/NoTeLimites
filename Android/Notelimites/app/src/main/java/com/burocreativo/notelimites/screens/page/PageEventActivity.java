package com.burocreativo.notelimites.screens.page;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.burocreativo.notelimites.NTLApplication;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.databinding.ActivityPageEventBinding;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.events.VenueEvents;
import com.burocreativo.notelimites.io.models.relationship.EventFollowed;
import com.burocreativo.notelimites.io.models.relationship.Follow;
import com.burocreativo.notelimites.io.models.user.UserResponse;
import com.burocreativo.notelimites.screens.adapters.PageListAdapter;
import com.burocreativo.notelimites.screens.login.StartActivity;

import java.util.List;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PageEventActivity extends AppCompatActivity {

    private RecyclerView eventList;
    private Toolbar toolbar;
    private ActivityPageEventBinding binding;
    BranchUniversalObject branchUniversalObject;
    LinkProperties linkProperties;


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

        String follower = NTLApplication.tinyDB.getString("user_id");
        if(follower.equals("")){
            follower = null;
        }
        Call<Event> call = ServiceGenerator.getApiService().getEvent(this.getIntent().getStringExtra("EventId"), ServiceGenerator.authToken,follower);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if(response.isSuccessful()) {
                    Event event = response.body();
                    binding.setEvent(event);
                    RecView(response.body().getVenueEvents());
                    branchUniversalObject = new BranchUniversalObject()
                            .setCanonicalIdentifier(binding.getEvent().getEventUID())
                            .setTitle(binding.getEvent().getEventName())
                            .setContentDescription(binding.getEvent().getDescription())
                            .setContentImageUrl(binding.getEvent().getImageURL())
                            .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                            .addContentMetadata("event",String.valueOf(binding.getEvent().getEventID()));

                    linkProperties = new LinkProperties()
                            .setChannel("facebook")
                            .setFeature("sharing")
                            .addControlParameter("$desktop_url","http://www.notelimites.com/eventos/"+binding.getEvent().getEventID())
                            .addControlParameter("$ios_url","http://www.notelimites.com/eventos/"+binding.getEvent().getEventID());

                    branchUniversalObject.generateShortUrl(PageEventActivity.this, linkProperties, new Branch.BranchLinkCreateListener() {
                        @Override
                        public void onLinkCreate(String url, BranchError error) {
                            if (error == null) {
                                Log.i("MyApp", "got my Branch link to share: " + url);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {

            }
        });

        ShareSheetStyle shareSheetStyle = new ShareSheetStyle(PageEventActivity.this, "Mira!", "Este Evento: ")
                .setCopyUrlStyle(getResources().getDrawable(android.R.drawable.ic_menu_send), "Copiar", "Copiado")
                .setMoreOptionStyle(getResources().getDrawable(android.R.drawable.ic_menu_search), "Ver Mas")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .setAsFullWidthStyle(true)
                .setSharingTitle("compartir con");

        findViewById(R.id.share).setOnClickListener(view -> branchUniversalObject.showShareSheet(PageEventActivity.this,
                linkProperties,
                shareSheetStyle,
                new Branch.BranchLinkShareListener() {
                    @Override
                    public void onShareLinkDialogLaunched() {
                    }
                    @Override
                    public void onShareLinkDialogDismissed() {
                    }
                    @Override
                    public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                    }
                    @Override
                    public void onChannelSelected(String channelName) {
                    }
                }));

        findViewById(R.id.page_place_location_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageEventActivity.this,PagePlaceActivity.class);
                intent.putExtra("VenueId",String.valueOf(binding.getEvent().getVenueID()));
                startActivity(intent);
            }
        });

        Button like = (Button) findViewById(R.id.likeButton);
        like.setOnClickListener(view -> {
            if(NTLApplication.tinyDB.getObject("user", UserResponse.class) == null){
                Intent intent = new Intent(PageEventActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                Call<EventFollowed> followedCall;
                if(!binding.getEvent().getFollowed()) {
                    followedCall= ServiceGenerator.getApiService().followEvent(new Follow(NTLApplication.tinyDB.getString("user_id"),String.valueOf(binding.getEvent().getEventID())));
                } else {
                    followedCall = ServiceGenerator.getApiService().unFollowEvent(new Follow(NTLApplication.tinyDB.getString("user_id"),String.valueOf(binding.getEvent().getEventID())));
                }

                followedCall.enqueue(new Callback<EventFollowed>() {
                    @Override
                    public void onResponse(Call<EventFollowed> call1, Response<EventFollowed> response) {
                        if(response.isSuccessful()){
                            Drawable follow;
                            if(response.body().getFollowed()){
                                follow = getResources().getDrawable(R.drawable.ic_favorite);
                            } else {
                                follow = getResources().getDrawable(R.drawable.ic_favorite_border);
                            }
                            like.setCompoundDrawables(follow,null,null,null);
                        }
                    }

                    @Override
                    public void onFailure(Call<EventFollowed> call1, Throwable t) {

                    }
                });

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
