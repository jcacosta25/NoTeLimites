package com.burocreativo.notelimites.screens.search;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.locations.Location;
import com.burocreativo.notelimites.io.models.locations.Locations;
import com.burocreativo.notelimites.screens.home.HomeActivity;
import com.burocreativo.notelimites.utils.SearchFeedResultsAdapter;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {

    public static final String TAG = "LOGIN";
    private static final int REQUEST_LOCATION = 0;
    public static String[] columns;
    View layout;
    SearchView searchView;
    MatrixCursor cursor;
    SearchFeedResultsAdapter searchFeedResultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        layout = findViewById(R.id.view);
        columns = new String[]{"_id", "LOCATION_NAME", "LOCATION_SLUG", "LOCATION_LAT", "LOCATION_LON"};
        cursor = new MatrixCursor(columns);
        searchView = (SearchView) findViewById(R.id.search_city);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSuggestionListener(this);
        searchFeedResultsAdapter = new SearchFeedResultsAdapter(this, R.layout.element_search_adapter, cursor, columns, null, -1000);
        searchView.setSuggestionsAdapter(searchFeedResultsAdapter);
        findViewById(R.id.btn_find_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPreview(layout);
            }
        });
    }


    public void showPreview(View view) {
        Log.i(TAG, "Show camera button pressed. Checking permission.");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();

        } else {
            Log.i(TAG,
                    " permission has already been granted. Displaying camera preview.");

            Intent intent = new Intent(SearchActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

    }


    private void requestPermission() {
        Log.i(TAG, "Location permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(layout, R.string.permission_location_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(SearchActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_LOCATION);
                        }
                    })
                    .show();
            Intent intent = new Intent(SearchActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
        // END_INCLUDE(camera_permission_request)
    }

    private void loadLocations(final String searchText) {
        HashMap queryMap = new HashMap();
        queryMap.put("query", searchText);
        Call<Locations> call = ServiceGenerator.getApiService().getLocations();
        call.enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                MatrixCursor matrixCursor = convertToCursor(response.body().getLocations(), searchText);
                searchFeedResultsAdapter.changeCursor(matrixCursor);
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable t) {

            }
        });
    }

    private MatrixCursor convertToCursor(List<Location> locations, String searchText) {
        cursor = new MatrixCursor(columns);
        int i = 0;
        for (Location location : locations) {
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
    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 2) {
            loadLocations(query);
        }
        return true;
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
        Intent intent = new Intent(SearchActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (cursor.getString(3) == null && cursor.getString(4) == null) {
            Toast.makeText(SearchActivity.this, "No se puede encontrar este lugar", Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra("slug", cursor.getString(2));
            intent.putExtra("lat", cursor.getString(3));
            intent.putExtra("lng", cursor.getString(4));
            searchView.clearFocus();
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
        String feedName = cursor.getString(1);
        Intent intent = new Intent(SearchActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (cursor.getString(3) == null && cursor.getString(4) == null) {
            Toast.makeText(SearchActivity.this, "No se puede encontrar este lugar", Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra("slug", cursor.getString(2));
            intent.putExtra("lat", cursor.getString(3));
            intent.putExtra("lng", cursor.getString(4));
            searchView.clearFocus();
            startActivity(intent);
        }
        return true;
    }

}
