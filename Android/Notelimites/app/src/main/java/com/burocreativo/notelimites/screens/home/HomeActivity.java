package com.burocreativo.notelimites.screens.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.events.Data;
import com.burocreativo.notelimites.io.models.events.EventsList;
import com.burocreativo.notelimites.io.models.locations.Locations;
import com.burocreativo.notelimites.screens.adapters.EventListAdapter;
import com.burocreativo.notelimites.screens.home.adapters.DrawerItem;
import com.burocreativo.notelimites.screens.home.adapters.DrawerListAdapter;
import com.burocreativo.notelimites.screens.page.PageEventActivity;
import com.burocreativo.notelimites.screens.profile.ProfileActivity;
import com.burocreativo.notelimites.utils.SearchFeedResultsAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.branch.referral.Branch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    private GoogleMap mMap;
    private RecyclerView eventList;
    private Toolbar mToolbar;
    private TabLayout tabs;
    private String[] mTitles;
    private DrawerLayout mDrawerLayout;
    private EventListAdapter adapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private GoogleApiClient googleApiClient;
    private View currentFocusedLayout, oldFocusedLayout;
    private String cityName;
    public static String[] columns;
    SearchView searchView;
    MatrixCursor cursor;
    SearchFeedResultsAdapter searchFeedResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (googleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        tabs = (TabLayout) findViewById(R.id.tabs);
        eventList = (RecyclerView) findViewById(R.id.eventList);
        String[] categoryList = getResources().getStringArray(R.array.Category);
        for (String category : categoryList) {
            tabs.addTab(tabs.newTab().setText(category));
        }
        TabLayout.Tab tab = tabs.getTabAt(2);
        tab.select();
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_selected));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                adapter.filter(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        startDrawer();
        columns = new String[]{"_id", "LOCATION_NAME", "LOCATION_SLUG", "LOCATION_LAT", "LOCATION_LON"};
        cursor = new MatrixCursor(columns);
        searchView = (SearchView) findViewById(R.id.search_city);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        searchFeedResultsAdapter = new SearchFeedResultsAdapter(this, R.layout.element_search_adapter, cursor, columns, null, -1000);
        searchView.setSuggestionsAdapter(searchFeedResultsAdapter);
        searchView.setQuery(getIntent().getStringExtra("city"),false);
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

    public void RecView(double lat, double lng) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        Data data = new Data(ServiceGenerator.authToken, String.valueOf(lat), String.valueOf(lng));
        Callback<EventsList> callback = new Callback<EventsList>() {
            @Override
            public void onResponse(Call<EventsList> call, Response<EventsList> response) {
                if (response.isSuccessful()) {
                    adapter = new EventListAdapter(response.body().getEvents(), HomeActivity.this);
                    eventList.setAdapter(adapter);
                    eventList.setLayoutManager(layoutManager);
                    eventList.setHasFixedSize(true);
                }
            }

            @Override
            public void onFailure(Call<EventsList> call, Throwable t) {

            }
        };
        Call<EventsList> call = ServiceGenerator.getApiService().getEventLocations(data);
        call.enqueue(callback);

        int firstVisiblePotition = layoutManager.findFirstVisibleItemPosition();
        View v = layoutManager.getChildAt(0);
        if (firstVisiblePotition > 0 && v != null) {
            int offsetTop = v.getTop();
            adapter.notifyDataSetChanged();
            if (firstVisiblePotition - 1 >= 0 && adapter.getItemCount() > 0) {
                layoutManager.scrollToPositionWithOffset(firstVisiblePotition - 1, offsetTop);
                Log.d("RecView Element: ", String.valueOf(firstVisiblePotition - 1));
            }
        }

        eventList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    int positionView = ((LinearLayoutManager) eventList.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                    int location = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    Log.i("VISISBLE", positionView + "");
                    if (positionView >= -1) {
                        currentFocusedLayout = eventList.getLayoutManager().findViewByPosition(positionView);
                        LatLng eventLocation = adapter.getEventLocation(location);
                        mMap.addMarker(new MarkerOptions().position(eventLocation));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLocation));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    }
                }
            }
        });

    }

    public void startDrawer() {
        List<DrawerItem> item = new ArrayList<>();
        String[] string = getResources().getStringArray(R.array.Tags);
        final TypedArray icon = getResources().obtainTypedArray(R.array.Icon);
        for (int i = 0; i < string.length; i++) {
            item.add(new DrawerItem(string[i], icon.getResourceId(i, -1)));
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

        mDrawerList.setAdapter(new DrawerListAdapter(getApplicationContext(), item));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (getIntent().hasExtra("lat")) {
            String latString = getIntent().getStringExtra("lat");
            String lngString = getIntent().getStringExtra("lng");
            cityName = getIntent().getStringExtra("slug");
            RecView(Double.parseDouble(latString), Double.parseDouble(lngString));
            LatLng sydney = new LatLng(Double.parseDouble(latString), Double.parseDouble(lngString));
            mMap.getUiSettings().setAllGesturesEnabled(false);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Tu estas Aqui"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
            }
            Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (location != null) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses.size() > 0) {
                        System.out.println(addresses.get(0).getLocality());
                        cityName = addresses.get(0).getLocality().toLowerCase().replace(' ', '_');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RecView(location.getLatitude(), location.getLongitude());
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.getUiSettings().setAllGesturesEnabled(false);
                mMap.addMarker(new MarkerOptions().position(sydney).title("Tu estas Aqui"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Home Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (googleApiClient == null) {
            // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
            // See https://g.co/AppIndexing/AndroidStudio for more information.
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(AppIndex.API).build();
        }
        googleApiClient.connect();

        Branch branch = Branch.getInstance();

        branch.initSession((referringParams, error) -> {
            if (error == null) {
                // params are the deep linked params as
                // sociated with the link that the user clicked -> was re-directed to this app
                // params will be empty if no data found
                // ... insert custom logic here ...
                if(referringParams.has("event")){

                    Intent intent = new Intent(HomeActivity.this, PageEventActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        intent.putExtra("EventId", String.valueOf(referringParams.get("event")));
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log.i("NoTeLimites", error.getMessage());
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        Log.e("selectItem: ", String.valueOf(position));
        // Create a new fragment and specify the planet to show based on position
        if (position == 1) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 2) {
            loadLocations(query);
        }
        return true;
    }

    private void loadLocations(final String searchText) {
        HashMap queryMap = new HashMap();
        queryMap.put("query", searchText);
        Call<Locations> call = ServiceGenerator.getApiService().getLocations();
        call.enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                MatrixCursor matrixCursor = convertToCursor(response.body().getLocations(), searchText);
                if(matrixCursor != null)
                    searchFeedResultsAdapter.changeCursor(matrixCursor);
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable t) {

            }
        });
    }

    private MatrixCursor convertToCursor(List<com.burocreativo.notelimites.io.models.locations.Location> locations, String searchText) {
        cursor = new MatrixCursor(columns);
        int i = 0;
        for (com.burocreativo.notelimites.io.models.locations.Location location : locations) {
            if (location.getLocationName() != null && location.getLocationName().contains(searchText)) {
                String[] temp = new String[columns.length];
                i = i + 1;
                temp[0] = Integer.toString(i);
                temp[1] = location.getLocationName();
                temp[2] = location.getLocationSlug();
                temp[3] = location.getLocationLat();
                temp[4] = location.getLocationLng();
                cursor.addRow(temp);
            }
        }

        return cursor;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 2) {
            loadLocations(newText);
        }
        return true;
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
        String feedName = cursor.getString(1);
        searchView.setQuery(feedName, true);
        if (cursor.getString(3) == null && cursor.getString(4) == null) {
            Toast.makeText(HomeActivity.this, "No se puede encontrar este lugar", Toast.LENGTH_SHORT).show();
        } else {
            RecView(Double.parseDouble(cursor.getString(3)),Double.parseDouble(cursor.getString(4)));
            searchView.setQuery(feedName,false);
            searchView.clearFocus();
        }
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
        String feedName = cursor.getString(1);
        if (cursor.getString(3) == null && cursor.getString(4) == null) {
            Toast.makeText(HomeActivity.this, "No se puede encontrar este lugar", Toast.LENGTH_SHORT).show();
        } else {
            RecView(Double.parseDouble(cursor.getString(3)),Double.parseDouble(cursor.getString(4)));
            searchView.setQuery(feedName,false);
            searchView.clearFocus();

        }
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            Intent i = new Intent();

            switch (position){
                case 0:
                    break;
                case 1:
                    i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://info.notelimites.com/"));
                    break;
                case 2:
                    i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://info.notelimites.com/preguntas-frecuentes/"));
                    break;
                case 3:
                    i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://info.notelimites.com/contacto/"));
                    break;
                case 4:
                    i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://info.notelimites.com/"));
                    break;
                case 5:
                    i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://info.notelimites.com/instituciones-culturales/"));
                    break;
            }
            startActivity(i);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();
    }
}
