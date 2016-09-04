package com.burocreativo.notelimites.profile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.profile.adapters.ViewPagerAdapter;
import com.burocreativo.notelimites.profile.fragments.MyEventsFragment;
import com.burocreativo.notelimites.profile.fragments.MyPlacesFragment;

public class ProfileActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TabLayout tabs;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.profile_viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MyEventsFragment(),"Tus Eventos");
        adapter.addFrag(new MyPlacesFragment(), "Tus Lugares");
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }
}
