package com.burocreativo.notelimites.screens.profile;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.burocreativo.notelimites.NTLApplication;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.databinding.ActivityProfileBinding;
import com.burocreativo.notelimites.io.models.user.UserResponse;
import com.burocreativo.notelimites.screens.profile.adapters.ViewPagerAdapter;
import com.burocreativo.notelimites.screens.profile.fragments.MyEventsFragment;

public class  ProfileActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabs;
    private Toolbar toolbar;
    private ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        UserResponse user =(UserResponse) NTLApplication.tinyDB.getObject("user",UserResponse.class);
        binding.setUser(user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ImageView profile = (ImageView) findViewById(R.id.profile_pic_img);
        Glide.with(this)
                .load(user.getImage())
                .asBitmap()
                .into(profile);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.profile_viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MyEventsFragment(),"Tus Eventos");
        //adapter.addFrag(new MyPlacesFragment(), "Tus Lugares");
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
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
