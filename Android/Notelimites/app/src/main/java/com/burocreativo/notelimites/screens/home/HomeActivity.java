package com.burocreativo.notelimites.screens.home;

import static com.burocreativo.notelimites.utils.Utils.createDrawableFromView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.burocreativo.notelimites.NTLApplication;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.events.Category;
import com.burocreativo.notelimites.io.models.events.Data;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.events.EventsList;
import com.burocreativo.notelimites.io.models.events.Venues;
import com.burocreativo.notelimites.io.models.locations.Locations;
import com.burocreativo.notelimites.io.models.user.UserResponse;
import com.burocreativo.notelimites.screens.adapters.EventListAdapter;
import com.burocreativo.notelimites.screens.home.adapters.DrawerItem;
import com.burocreativo.notelimites.screens.home.adapters.DrawerListAdapter;
import com.burocreativo.notelimites.screens.login.StartActivity;
import com.burocreativo.notelimites.screens.page.PageEventActivity;
import com.burocreativo.notelimites.screens.page.PagePlaceActivity;
import com.burocreativo.notelimites.screens.profile.ProfileActivity;
import com.burocreativo.notelimites.utils.SearchFeedResultsAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.morrox.fontinator.FontTextView;
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

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

  public static GoogleMap mMap;
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
  private TextView searchCityTxt;
  public static String[] columns;
  public double lat, lng;
  SearchView searchView;
  MatrixCursor cursor;
  private int mLastScrollTo;
  SearchFeedResultsAdapter searchFeedResultsAdapter;
  private float ZOOM_TO = 15.0f;
  private float FIRST_ZOOM = 11.0f;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    cityName = getIntent().getStringExtra("city");

    if (googleApiClient == null) {
      googleApiClient = new GoogleApiClient.Builder(this)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API).build();
    }
    mToolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(mToolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    tabs = (TabLayout) findViewById(R.id.tabs);
    eventList = (RecyclerView) findViewById(R.id.eventList);
    String[] categoryList = getResources().getStringArray(R.array.Category);
    for (String category : categoryList) {

    }
    tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.tab_selected));
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
    tabs.setScrollX(tabs.getWidth());
    tabs.setSmoothScrollingEnabled(true);
    //new Handler().postDelayed(() -> tabs.getTabAt(3).select(),100);
    tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {

        if (adapter != null) {
          adapter.filter(tab.getPosition());
        }

        View selectedChild = tabs.getChildAt(tab.getPosition());
        if (selectedChild != null && selectedChild.getMeasuredWidth() != 0) {

          int targetScrollX =
              ((tab.getPosition() + selectedChild.getLeft()) - tabs.getScrollBarSize() / 2)
                  + selectedChild.getWidth() / 2;
          if (targetScrollX != mLastScrollTo) {
            tabs.scrollTo(targetScrollX, 0);
            mLastScrollTo = targetScrollX;
          }

        }

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
    searchCityTxt = (TextView) findViewById(R.id.search_city_txt);
    searchView.setOnSearchClickListener(view -> searchCityTxt.setVisibility(View.GONE));
    searchView.setOnCloseListener(() -> {
      searchCityTxt.setVisibility(View.VISIBLE);
      return false;
    });
    searchView.setOnClickListener(v -> {
      searchView.setIconified(false);
    });
    searchCityTxt.setOnClickListener(v -> {
      searchView.setIconified(false);
    });
    searchCityTxt.setText(cityName);
    searchView.setQuery(cityName, false);
    searchView.setQueryHint(cityName);
    searchView.setOnQueryTextListener(this);
    searchView.setOnSuggestionListener(this);
    searchView.clearFocus();
    searchFeedResultsAdapter = new SearchFeedResultsAdapter(this, R.layout.element_search_adapter,
        cursor, columns, null, -1000);
    searchView.setSuggestionsAdapter(searchFeedResultsAdapter);

  }


  public void RecView(double lat, double lng) {
    this.lat = lat;
    this.lng = lng;
    searchView.setQuery(cityName, false);
    LatLng latlng = new LatLng(lat, lng);
    //mMap.getUiSettings().setAllGesturesEnabled(false);
    BitmapDescriptor markerIcon = getMarkerIconFromDrawable(
        getResources().getDrawable(R.drawable.location_marker));
    mMap.addMarker(new MarkerOptions().position(latlng).icon(markerIcon));
    CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(latlng)              // Center Set
        .zoom(FIRST_ZOOM)                // Zoom
        .build();
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
        LinearLayoutManager.HORIZONTAL, false);
    Data data = new Data(ServiceGenerator.authToken, String.valueOf(lat), String.valueOf(lng));
    Callback<EventsList> callback = new Callback<EventsList>() {
      @Override
      public void onResponse(Call<EventsList> call, Response<EventsList> response) {
        if (response.isSuccessful()) {
          setMarkers(response.body().getVenues());
          setCategories(response.body().getCategories());
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
    String follower = NTLApplication.tinyDB.getString("user_id");
    if (follower.equals("")) {
      follower = null;
    }
    Call<EventsList> call = ServiceGenerator.getApiService().getEventLocations(data, follower);
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
          int positionView = ((LinearLayoutManager) eventList.getLayoutManager())
              .findLastCompletelyVisibleItemPosition();
          int location = ((LinearLayoutManager) recyclerView.getLayoutManager())
              .findFirstVisibleItemPosition();
          Log.i("VISISBLE", positionView + "");
          if (positionView >= -1) {
            currentFocusedLayout = eventList.getLayoutManager().findViewByPosition(positionView);
            LatLng eventLocation = adapter.getEventLocation(location);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(eventLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_TO));
          }
        }
      }
    });

  }

  public void setMarkers(List<Venues> venuesList) {
    mMap.clear();
    int currentPosition = 0;
    for (Venues venue : venuesList) {
      LatLng eventLocation = new LatLng(Double.parseDouble(venue.getVenueLat()),
          Double.parseDouble(venue.getVenueLng()));
      View markerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
      FontTextView numTxt = (FontTextView) markerView.findViewById(R.id.marker_id );
      numTxt.setText(String.valueOf(currentPosition));
      Marker marker = mMap.addMarker(
          new MarkerOptions().
              title(venue.getVenueName())
              .position(eventLocation).icon(BitmapDescriptorFactory
              .fromBitmap(createDrawableFromView(this,markerView))));
      marker.setTag(currentPosition);
      currentPosition++;
    }
  }

  public void setCategories(List<Category> categoryList){
    Stream.of(categoryList)
        .forEach(category -> tabs.addTab(tabs.newTab().setText(category.getCategoryName())));
  }

  public void startDrawer() {
    List<DrawerItem> item = new ArrayList<>();
    String[] string = getResources().getStringArray(R.array.Tags);
    final TypedArray icon = getResources().obtainTypedArray(R.array.Icon);
    for (int i = 0; i < string.length; i++) {
      item.add(new DrawerItem(string[i], icon.getResourceId(i, -1)));
    }

    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open,
        R.string.drawer_close) {

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
    mMap.setOnMarkerClickListener(marker -> {
      eventList.smoothScrollToPosition(((int) marker.getTag()));
      CameraPosition cameraPosition = new CameraPosition.Builder()
          .target(marker.getPosition())              // Center Set
          .zoom(20.0f)                // Zoom
          .build();
      mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
      return false;
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.my_location) {
      LatLng latlng = new LatLng(lat, lng);
      CameraPosition cameraPosition = new CameraPosition.Builder()
          .target(latlng)              // Center Set
          .zoom(FIRST_ZOOM)                // Zoom
          .build();
      mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    if (getIntent().hasExtra("lat")) {
      lat = Double.parseDouble(getIntent().getStringExtra("lat"));
      lng = Double.parseDouble(getIntent().getStringExtra("lng"));
      cityName = getIntent().getStringExtra("city");
      RecView(lat, lng);
    } else {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
      }
      Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
      if (location != null) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
          List<Address> addresses = geocoder
              .getFromLocation(location.getLatitude(), location.getLongitude(), 1);
          if (addresses.size() > 0) {
            System.out.println(addresses.get(0).getLocality());
            searchView.setQuery(addresses.get(0).getLocality(), false);
            searchView.setQueryHint(addresses.get(0).getLocality());
            cityName = addresses.get(0).getLocality();
            searchCityTxt.setText(cityName);
            searchView.clearFocus();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }

        RecView(location.getLatitude(), location.getLongitude());
      }
    }
  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
      googleApiClient = new GoogleApiClient.Builder(this)
          .addConnectionCallbacks(this)
          .addOnConnectionFailedListener(this)
          .addApi(LocationServices.API).build();
    }
    googleApiClient.connect();

    Branch branch = Branch.getInstance();

    branch.initSession((referringParams, error) -> {
      if (error == null) {
        // params are the deep linked params as
        // sociated with the link that the user clicked -> was re-directed to this app
        // params will be empty if no data found
        // ... insert custom logic here ...
        if (referringParams.has("event")) {

          Intent intent = new Intent(HomeActivity.this, PageEventActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          try {
            intent.putExtra("EventId", String.valueOf(referringParams.get("event")));
            startActivity(intent);
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }

        if (referringParams.has("venue")) {
          Intent intent = new Intent(HomeActivity.this, PagePlaceActivity.class);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          try {
            intent.putExtra("VenueId", String.valueOf(referringParams.get("venue")));
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
        if (matrixCursor != null) {
          searchFeedResultsAdapter.changeCursor(matrixCursor);
        }
      }

      @Override
      public void onFailure(Call<Locations> call, Throwable t) {

      }
    });
  }

  private MatrixCursor convertToCursor(
      List<com.burocreativo.notelimites.io.models.locations.Location> locations,
      String searchText) {
    cursor = new MatrixCursor(columns);
    int i = 0;
    for (com.burocreativo.notelimites.io.models.locations.Location location : locations) {
      if (location.getLocationName() != null &&
          location.getLocationName().contains(searchText) ||
          location.getLocationName().toLowerCase().contains(searchText) ||
          location.getLocationName().toUpperCase().contains(searchText)) {
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
      Toast.makeText(HomeActivity.this, "No se puede encontrar este lugar", Toast.LENGTH_SHORT)
          .show();
    } else {
      mMap.clear();
      RecView(Double.parseDouble(cursor.getString(3)), Double.parseDouble(cursor.getString(4)));
      searchView.setQuery(feedName, false);
      searchView.setQueryHint(feedName);
      searchCityTxt.setText(feedName);
      searchView.clearFocus();
    }
    return true;
  }

  @Override
  public boolean onSuggestionClick(int position) {
    Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
    String feedName = cursor.getString(1);
    if (cursor.getString(3) == null && cursor.getString(4) == null) {
      Toast.makeText(HomeActivity.this, "No se puede encontrar este lugar", Toast.LENGTH_SHORT)
          .show();
    } else {
      mMap.clear();
      RecView(Double.parseDouble(cursor.getString(3)), Double.parseDouble(cursor.getString(4)));
      searchView.setQuery(feedName, false);
      searchCityTxt.setText(feedName);
      searchView.setQueryHint(feedName);
      searchView.clearFocus();

    }
    return true;
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      selectItem(position);
      Intent i;

      switch (position) {
        case 1:
          if (NTLApplication.tinyDB.getObject("user", UserResponse.class) != null) {
            i = new Intent(getApplicationContext(), ProfileActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          } else {
            i = new Intent(getApplicationContext(), StartActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("profile", true);
          }
          startActivity(i);
          break;
        case 2:
          i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse("http://info.notelimites.com/"));
          startActivity(i);
          break;
        case 3:
          i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse("http://info.notelimites.com/preguntas-frecuentes/"));
          startActivity(i);
          break;
        case 4:
          i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse("http://info.notelimites.com/contacto/"));
          startActivity(i);
          break;
        case 5:
          i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse("http://info.notelimites.com/"));
          startActivity(i);
          break;
        case 6:
          i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse("http://info.notelimites.com/instituciones-culturales/"));
          startActivity(i);
          break;
        case 7:
          finish();
          break;
      }
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

  private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
    Canvas canvas = new Canvas();
    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888);
    canvas.setBitmap(bitmap);
    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    drawable.draw(canvas);
    return BitmapDescriptorFactory.fromBitmap(bitmap);
  }
}
