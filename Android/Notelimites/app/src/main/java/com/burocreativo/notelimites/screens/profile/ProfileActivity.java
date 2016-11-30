package com.burocreativo.notelimites.screens.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.screens.profile.adapters.ViewPagerAdapter;
import com.burocreativo.notelimites.screens.profile.fragments.MyEventsFragment;
import com.burocreativo.notelimites.screens.profile.fragments.MyPlacesFragment;

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
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_left);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        tabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.profile_viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MyEventsFragment(),"Tus Eventos");
        adapter.addFrag(new MyPlacesFragment(), "Tus Lugares");
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
