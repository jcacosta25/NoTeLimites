package com.burocreativo.notelimites.screens.adapters;

import static com.burocreativo.notelimites.screens.home.HomeActivity.mMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;
import com.annimon.stream.Stream;
import com.burocreativo.notelimites.BR;
import com.burocreativo.notelimites.NTLApplication;
import com.burocreativo.notelimites.R;
import com.burocreativo.notelimites.io.ServiceGenerator;
import com.burocreativo.notelimites.io.models.events.Event;
import com.burocreativo.notelimites.io.models.relationship.EventFollowed;
import com.burocreativo.notelimites.io.models.relationship.Follow;
import com.burocreativo.notelimites.io.models.user.UserResponse;
import com.burocreativo.notelimites.screens.login.StartActivity;
import com.burocreativo.notelimites.screens.page.PageEventActivity;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import de.morrox.fontinator.FontTextView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juan C. Acosta on 9/3/2016.
 * juancacosta25@gmail.com.com
 */
public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

  private List<Event> eventList = new ArrayList<>(), filterList = new ArrayList<>();
  private Context context;

  public EventListAdapter(List<Event> eventList, Context context) {
    this.eventList = eventList;
    this.context = context;
    this.filterList = new ArrayList<>();
    this.filterList.addAll(eventList);
    setMarkers();
  }

  @Override
  public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
    ViewDataBinding binding;
    binding = DataBindingUtil
        .inflate(LayoutInflater.from(parent.getContext()), R.layout.element_list_event, parent,
            false);
    return new ViewHolder(binding);
  }

  @SuppressLint("SimpleDateFormat")
  @Override
  public void onBindViewHolder(ViewHolder holder,
      @SuppressLint("RecyclerView") final int position) {

    Event event = filterList.get(position);
    ViewDataBinding binding = holder.getBind();
    binding.setVariable(BR.event, event);
    holder.bind(event, (event1, view) -> {
      Intent intent = new Intent(context, PageEventActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.putExtra("EventId", String.valueOf(event.getEventID()));
      context.startActivity(intent);
    });
    ImageButton like = (ImageButton) holder.itemView.findViewById(R.id.like_btn);
    like.setOnClickListener(view -> {
      if (NTLApplication.tinyDB.getObject("user", UserResponse.class) == null) {
        Intent intent = new Intent(context, StartActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
      } else {
        Call<EventFollowed> followedCall;
        if (!event.getFollowed()) {
          followedCall = ServiceGenerator.getApiService().followEvent(
              new Follow(NTLApplication.tinyDB.getString("user_id"),
                  String.valueOf(event.getEventID())));
        } else {
          followedCall = ServiceGenerator.getApiService().unFollowEvent(
              new Follow(NTLApplication.tinyDB.getString("user_id"),
                  String.valueOf(event.getEventID())));
        }

        followedCall.enqueue(new Callback<EventFollowed>() {
          @Override
          public void onResponse(Call<EventFollowed> call1, Response<EventFollowed> response) {
            if (response.isSuccessful()) {
              Drawable follow;
              if (response.body().getFollowed()) {
                follow = context.getResources().getDrawable(R.drawable.ic_favorite);
              } else {
                follow = context.getResources().getDrawable(R.drawable.ic_favorite_border);
              }
              like.setBackground(follow);
            }
          }

          @Override
          public void onFailure(Call<EventFollowed> call1, Throwable t) {
          }
        });

      }
    });

    holder.itemView.findViewById(R.id.add_calendar_btn).setOnClickListener(v -> {
      Intent addtoCalendar = new Intent(Intent.ACTION_INSERT);
      addtoCalendar.setType("vnd.android.cursor.item/event");
      addtoCalendar.putExtra(Events.TITLE, event.getEventName());
      addtoCalendar.putExtra(Events.EVENT_LOCATION, event.getVenueName());
      addtoCalendar.putExtra(Events.DESCRIPTION, event.getDescription());
      addtoCalendar
          .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.getInitDate().getTime());
      addtoCalendar.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.getEndDate().getTime());
      context.startActivity(addtoCalendar);
    });
  }

  public LatLng getEventLocation(int position) {
    if (position == -1) {
      position = 0;
    }
    Event event = filterList.get(position);
    return new LatLng(Double.parseDouble(event.getPlaceLat()),
        Double.parseDouble(event.getPlaceLng()));
  }

  public String getEventPlace(int position) {
    if (position == -1) {
      position = 0;
    }
    Event event = filterList.get(position);
    return event.getVenueName();
  }

  @Override
  public int getItemCount() {
    return (null != filterList ? filterList.size() : 0);
  }

  public void filter(final int cat) {
    filterList = new ArrayList<>();
    notifyDataSetChanged();
    if (cat == 3) {
      filterList.addAll(eventList);
    } else {

      if (cat > 3) {
        Stream.of(eventList)
            .filter(event -> event.getEventtypeID() == cat - 1)
            .forEach(event -> {
              filterList.add(event);
              notifyDataSetChanged();
            });
      } else {
        Stream.of(eventList)
            .filter(event -> event.getEventtypeID() == cat + 1)
            .forEach(event -> {
              filterList.add(event);
              notifyDataSetChanged();
            });
      }

    }
    setMarkers();
  }

  public void setMarkers() {
    mMap.clear();
    int currentPosition = 0;
    for (Event event : filterList) {
      LatLng eventLocation = new LatLng(Double.parseDouble(event.getPlaceLat()),
          Double.parseDouble(event.getPlaceLng()));
      View markerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
      FontTextView numTxt = (FontTextView) markerView.findViewById(R.id.marker_id );
      numTxt.setText(String.valueOf(currentPosition));
      Marker marker = mMap.addMarker(
          new MarkerOptions().
              title(event.getVenueName())
              .position(eventLocation).icon(BitmapDescriptorFactory
              .fromBitmap(createDrawableFromView(context,markerView))));
      marker.setTag(currentPosition);
      currentPosition++;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding bindElement;
    private View itemView;

    ViewHolder(ViewDataBinding v) {
      super(v.getRoot());
      itemView = v.getRoot();
      bindElement = v;
      bindElement.executePendingBindings();
    }

    ViewDataBinding getBind() {
      return bindElement;
    }

    public void bind(final Event event, final OnItemClickListener listener) {
      itemView.setOnClickListener(view -> listener.onItemClick(event, view));
    }
  }

  interface OnItemClickListener {

    void onItemClick(Event event, View view);
  }

  public static Bitmap createDrawableFromView(Context context, View view) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
    view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
    view.buildDrawingCache();

    //Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
    Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),view.getMeasuredHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    view.draw(canvas);
    return bitmap;
  }

}
