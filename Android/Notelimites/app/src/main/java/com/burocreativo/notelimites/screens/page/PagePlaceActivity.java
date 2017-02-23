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
import android.widget.Button;

import com.burocreativo.notelimites.NTLApplication;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.databinding.ActivityPagePlaceBinding;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.places.Venue;
import com.burocreativo.notelimites.io.models.relationship.EventFollowed;
import com.burocreativo.notelimites.io.models.relationship.Follow;
import com.burocreativo.notelimites.io.models.user.UserResponse;
import com.burocreativo.notelimites.screens.adapters.EventListAdapter;
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

public class PagePlaceActivity extends AppCompatActivity {

    private RecyclerView eventList;
    private Toolbar toolbar;
    private ActivityPagePlaceBinding binding;
    BranchUniversalObject branchUniversalObject;
    LinkProperties linkProperties;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_page_place);
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

        Call<Venue> call = ServiceGenerator.getApiService().getVenue(this.getIntent().getStringExtra("VenueId"),ServiceGenerator.authToken,follower);
        call.enqueue(new Callback<Venue>() {
            @Override
            public void onResponse(Call<Venue> call, Response<Venue> response) {
                if(response.isSuccessful()){
                    Venue venue = response.body();
                    binding.setVenue(venue);
                    RecView(response.body().getEvents());

                    branchUniversalObject = new BranchUniversalObject()
                            .setCanonicalIdentifier(binding.getVenue().getVenueUID())
                            .setTitle(binding.getVenue().getVenueName())
                            .setContentDescription(binding.getVenue().getDescription())
                            .setContentImageUrl(binding.getVenue().getImageURL())
                            .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                            .addContentMetadata("venue",String.valueOf(binding.getVenue().getVenueID()));

                    linkProperties = new LinkProperties()
                            .setChannel("facebook")
                            .setFeature("sharing")
                            .addControlParameter("$desktop_url","http://www.notelimites.com/venues/"+binding.getVenue().getVenueID())
                            .addControlParameter("$ios_url","http://www.notelimites.com/venues/"+binding.getVenue().getVenueID());

                    branchUniversalObject.generateShortUrl(PagePlaceActivity.this, linkProperties, new Branch.BranchLinkCreateListener() {
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
            public void onFailure(Call<Venue> call, Throwable t) {

            }
        });

        ShareSheetStyle shareSheetStyle = new ShareSheetStyle(PagePlaceActivity.this, "Mira!", "Este Lugar: ")
                .setCopyUrlStyle(getResources().getDrawable(android.R.drawable.ic_menu_send), "Copiar", "Copiado")
                .setMoreOptionStyle(getResources().getDrawable(android.R.drawable.ic_menu_search), "Ver Mas")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .setAsFullWidthStyle(true)
                .setSharingTitle("compartir con");

        findViewById(R.id.share).setOnClickListener(view -> branchUniversalObject.showShareSheet(PagePlaceActivity.this,
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

        Button like = (Button) findViewById(R.id.likeButton);
        like.setOnClickListener(view -> {
            if(NTLApplication.tinyDB.getObject("user", UserResponse.class) == null){
                Intent intent = new Intent(PagePlaceActivity.this, StartActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            } else {
                Call<EventFollowed> followedCall;
                if(!binding.getVenue().getFollowed()) {
                    followedCall= ServiceGenerator.getApiService().followVenue(new Follow(NTLApplication.tinyDB.getString("user_id"),String.valueOf(binding.getVenue().getVenueID())));
                } else {
                    followedCall = ServiceGenerator.getApiService().unFollowVenue(new Follow(NTLApplication.tinyDB.getString("user_id"),String.valueOf(binding.getVenue().getVenueID())));
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

    public void RecView(List<Event> events){
        eventList.setAdapter(new EventListAdapter(events,getApplicationContext()));
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
