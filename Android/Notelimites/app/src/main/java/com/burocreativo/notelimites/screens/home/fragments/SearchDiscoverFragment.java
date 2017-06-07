package com.burocreativo.notelimites.screens.home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.annimon.stream.Stream;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.discover.Discover;
import com.burocreativo.notelimites.io.models.discover.DiscoverResponse;
import com.burocreativo.notelimites.utils.SimpleAdapter;
import com.burocreativo.notelimites.utils.SimpleSectionedRecyclerViewAdapter;
import com.burocreativo.notelimites.utils.SimpleSectionedRecyclerViewAdapter.Section;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juan C. Acosta on 5/24/2017.
 */

public class SearchDiscoverFragment extends DialogFragment implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener{

  private Context context;
  private String query = "";
  private RecyclerView discoverList;
  private SimpleAdapter adapter;
  private List<Section> sections = new ArrayList<>();
  private Callback<DiscoverResponse> callback;
  private List<Discover> discovers = new ArrayList<>();
  private SearchView searchView;
  private Call<DiscoverResponse> call;
  private TextView searchCityTxt;

  public SearchDiscoverFragment() {
  }

  public static SearchDiscoverFragment newInstance(Context context, String query,
      OnDiscoverElementSelectedListener listener) {
    SearchDiscoverFragment searchDiscoverFragment = new SearchDiscoverFragment();
    searchDiscoverFragment.initialize(context, query, listener);
    return searchDiscoverFragment;
  }

  public void initialize(Context context, String query,
      OnDiscoverElementSelectedListener listener) {
    this.context = context;
    this.query = query;
    this.callback = new Callback<DiscoverResponse>() {
      @Override
      public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
        int size = 0;
        if (response.body().getEvents().size() > 0) {
          Stream.of(response.body().getEvents())
              .forEach(
                  event -> discovers.add(new Discover(event.getEventID(), event.getEventName())));
          sections.add(new SimpleSectionedRecyclerViewAdapter.Section(size, "Eventos"));
          size = response.body().getEvents().size();
        }
        if (response.body().getLocations().size() > 0) {
          Stream.of(response.body().getLocations())
              .forEach(locationsItem -> discovers
                  .add(new Discover("location", locationsItem.getLocationID(),
                      locationsItem.getLocationName(),
                      locationsItem.getLocationLat(), locationsItem.getLocationLng())));
          sections.add(
              new SimpleSectionedRecyclerViewAdapter.Section(size,
                  "Ciudades"));
          size = +response.body().getLocations().size();
        }
        if (response.body().getVenues().size() > 0) {
          Stream.of(response.body().getVenues())
              .forEach(venue ->
                  discovers.add(new Discover("venue", venue.getVenueID(), venue.getVenueName(),
                      venue.getVenueLat(),
                      venue.getVenueLng())));
          sections.add(new SimpleSectionedRecyclerViewAdapter.Section(size, "Arenas"));
        }

        adapter = new SimpleAdapter(getContext(), discovers,
            item -> {
              listener.onDiscoverElementSelected(item.getType(), item.getId(),
                  item.getLat(), item.getLng(), item.getTitle());
              dismiss();
            });

        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections
            .size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
            SimpleSectionedRecyclerViewAdapter(getContext(), R.layout.section, R.id.section_text,
            adapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        discoverList.setAdapter(mSectionedAdapter);
      }

      @Override
      public void onFailure(Call<DiscoverResponse> call, Throwable t) {

      }
    };
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_search_discover, container, false);
    Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_arrow_left);
    searchView = (SearchView) root.findViewById(R.id.search_discover);
    searchCityTxt = (TextView) root.findViewById(R.id.search_city_txt);
    toolbar.setNavigationOnClickListener(v -> dismiss());
    discoverList = (RecyclerView) root.findViewById(R.id.discover_list);
    discoverList.setHasFixedSize(true);
    discoverList.setLayoutManager(new LinearLayoutManager(getActivity()));
    discoverList
        .addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

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
    searchCityTxt.setText(query);
    searchView.setQuery(query, false);
    searchView.setQueryHint(query);
    call = ServiceGenerator.getApiService().getDiscover(query);
    call.enqueue(callback);

    return root;
  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    adapter.removeAll();
    call = ServiceGenerator.getApiService().getDiscover(query);
    call.enqueue(callback);
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    return false;
  }

  @Override
  public boolean onSuggestionSelect(int position) {
    return false;
  }

  @Override
  public boolean onSuggestionClick(int position) {
    return false;
  }

  public interface OnDiscoverElementSelectedListener {

    void onDiscoverElementSelected(String type, int id, String lat, String lng, String cityName);
  }


  @Override
  public void onStart() {
    super.onStart();
    if (getDialog() == null) {
      return;
    }

    getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT);
  }


}
