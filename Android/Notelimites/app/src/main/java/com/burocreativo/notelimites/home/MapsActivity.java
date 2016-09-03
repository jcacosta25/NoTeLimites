package com.burocreativo.notelimites.home;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.home.adapters.DrawerItem;
import com.burocreativo.notelimites.home.adapters.DrawerListAdapter;
import com.burocreativo.notelimites.home.adapters.EventListAdapter;
import com.burocreativo.notelimites.io.models.Event;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RecyclerView eventList;
    private Toolbar mToolbar;
    private TabLayout tabs;
    private String[] mTitles;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        tabs = (TabLayout) findViewById(R.id.tabs);
        eventList = (RecyclerView) findViewById(R.id.eventList);
        tabs.addTab(tabs.newTab().setText("OTROS"));
        tabs.addTab(tabs.newTab().setText("TODOS LOS EVENTOS"));
        tabs.addTab(tabs.newTab().setText("MUSICA"));
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_selected));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        RecView();
        startDrawer();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    public void RecView(){
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 4 ; i++) {
            events.add(new Event());
        }
        eventList.setAdapter(new EventListAdapter(events,getApplicationContext()));
        eventList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        eventList.setHasFixedSize(true);
    }

    public void startDrawer(){
        List<DrawerItem> item = new ArrayList<>();
        String[] string = getResources().getStringArray(R.array.Tags);
        final TypedArray icon = getResources().obtainTypedArray(R.array.Icon);
        for (int i = 0; i < string.length ; i++) {
            item.add(new DrawerItem(string[i],icon.getResourceId(i,-1)));
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
                //drawerOpened = false;
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
                //drawerOpened = true;
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.drawer_header, mDrawerList,
                false);
        mDrawerList.addHeaderView(header, null, false);

        mDrawerList.setAdapter(new DrawerListAdapter(getApplicationContext(),item));
        // Set the list's click listener
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(25.680855,-100.288276);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Arena Monterrey"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(zoom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*  if (id == R.id.logo_small) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
