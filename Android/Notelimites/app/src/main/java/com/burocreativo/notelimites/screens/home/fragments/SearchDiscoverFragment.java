package com.burocreativo.notelimites.screens.home.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juan C. Acosta on 5/24/2017.
 */

public class SearchDiscoverFragment extends DialogFragment {

  private Context context;
  private String query;
  private RecyclerView discoverList;
  private SimpleAdapter adapter;
  private List<Section> sections = new ArrayList<>();
  private Callback<DiscoverResponse> callback;
  private List<Discover> discovers = new ArrayList<>();

  public SearchDiscoverFragment() {
  }

  public static SearchDiscoverFragment newInstance(Context context, String query) {
    SearchDiscoverFragment searchDiscoverFragment = new SearchDiscoverFragment();
    searchDiscoverFragment.initialize(context, query);
    return searchDiscoverFragment;
  }

  public void initialize(Context context, String query) {
    this.context = context;
    this.query = query;
    this.callback = new Callback<DiscoverResponse>() {
      @Override
      public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
        int size = 0;
        Stream.of(response.body().getEvents())
            .forEach(
                event -> discovers.add(new Discover(event.getEventID(), event.getEventName())));
        Stream.of(response.body().getVenues())
            .forEach(venue ->
                discovers.add(new Discover("venue", venue.getVenueID(), venue.getVenueName(),
                    venue.getVenueLat(),
                    venue.getVenueLng())));
        Stream.of(response.body().getLocations())
            .forEach(locationsItem -> discovers
                .add(new Discover("location", locationsItem.getLocationID(),
                    locationsItem.getLocationName(),
                    locationsItem.getLocationLat(), locationsItem.getLocationLng())));

        if (response.body().getEvents().size() > 0) {
          sections.add(new SimpleSectionedRecyclerViewAdapter.Section(size, "Eventos"));
          size = response.body().getEvents().size();
        }
        if (response.body().getLocations().size() > 0) {
          sections.add(
              new SimpleSectionedRecyclerViewAdapter.Section(size,
                  "Ciudades"));
          size = +response.body().getLocations().size();
        }
        if (response.body().getVenues().size() > 0) {
          sections.add(new SimpleSectionedRecyclerViewAdapter.Section(size, "Arenas"));
        }

        adapter = new SimpleAdapter(getContext(), discovers);

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
    toolbar.setNavigationOnClickListener(v -> dismiss());
    discoverList = (RecyclerView) root.findViewById(R.id.discover_list);
    discoverList.setHasFixedSize(true);
    discoverList.setLayoutManager(new LinearLayoutManager(getActivity()));
    discoverList
        .addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    //adapter = new SimpleAdapter(getContext(),sCheeseStrings);

    Call<DiscoverResponse> call  = ServiceGenerator.getApiService().getDiscover(query);
    call.enqueue(callback);
    //Add your adapter to the sectionAdapter

    return root;
  }

  public interface DiscoverElementSelected {

    void itemSelected();
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