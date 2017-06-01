package com.burocreativo.notelimites.utils;


import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Juan C. Acosta on 5/24/2017.
 */

public class ItemSectionedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final Context context;
  private static final int SECTION_TYPE = 0;

  private boolean isValid = true;
  private int sectionResourceId;
  private int textResourceId;
  private LayoutInflater layoutInflater;
  private RecyclerView.Adapter mybaseAdapter;
  private SparseArrayCompat<Section> sections = new SparseArrayCompat<>();

  public ItemSectionedRecyclerViewAdapter(Context context, int sectionResourceId, int textResourceId,RecyclerView.Adapter baseAdapter){
    layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    this.textResourceId = textResourceId;
    this.sectionResourceId = sectionResourceId;
    mybaseAdapter = baseAdapter;
    this.context = context;

    mybaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
      @Override
      public void onChanged(){
        isValid = baseAdapter.getItemCount() > 0;
        notifyDataSetChanged();
      }

      @Override
      public void onItemRangeChanged(int positionStart, int itemCount){
        isValid = baseAdapter.getItemCount() > 0 ;
        notifyItemRangeChanged(positionStart,itemCount);
      }

      @Override
      public void onItemRangeInserted(int positionStart, int itemCount){
        isValid = baseAdapter.getItemCount() > 0 ;
        notifyItemRangeInserted(positionStart,itemCount);
      }

      @Override
      public void onItemRangeRemoved(int positionStart, int itemCount) {
          isValid = baseAdapter.getItemCount() > 0 ;
          notifyItemRangeRemoved(positionStart,itemCount);
      }
    });
  }
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType == SECTION_TYPE){
      final View view = LayoutInflater.from(context).inflate(sectionResourceId,parent,false);
      return new SectionViewHolder(view,textResourceId);
    } else {
      return mybaseAdapter.onCreateViewHolder(parent,viewType -1);
    }
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
      if(isSectionHeaderPosition(position)){
        ((SectionViewHolder) holder).title.setText(sections.get(position).getTitle());
      } else {
        mybaseAdapter.onBindViewHolder(holder,sectionedPositionToPosition(position));
      }
  }

  public void setSections(Section[] sections){
    this.sections.clear();

    Arrays.sort(sections, new Comparator<Section>() {
      @Override
      public int compare(Section o1, Section o2) {
        return (o1.firstPosition == o2.firstPosition)
            ? 0
            : ((o1.firstPosition < o2.firstPosition) ? -1 : 1);
        }
      });
    int offset = 0;
    for (Section section:sections) {
      section.sectionedPosition = section.firstPosition + offset;
      this.sections.append(section.sectionedPosition,section);
      ++offset;
    }
    notifyDataSetChanged();
  }

  public  int positionToSectionedPosition(int position){
    int offset = 0;
    for (int i = 0; i < sections.size() ; i++) {
      if(sections.valueAt(i).firstPosition > position){
        break;
      }
      ++offset;
    }
    return  position + offset;
  }
  public boolean isSectionHeaderPosition(int position){
    return sections.get(position) != null;
  }

  public int sectionedPositionToPosition(int sectionedPosition){
    if(isSectionHeaderPosition(sectionedPosition)){
      return RecyclerView.NO_POSITION;
    }
    int offset = 0;
    for (int i = 0; i < sections.size(); i++) {
      if(sections.valueAt(i).sectionedPosition > sectionedPosition){
        break;
      }
      -- offset;
    }
    return sectionedPosition + offset;
  }
  @Override
  public long getItemId(int position) {
    return isSectionHeaderPosition(position)
        ? Integer.MAX_VALUE - sections.indexOfKey(position)
        : mybaseAdapter.getItemId(sectionedPositionToPosition(position));
  }

  @Override
  public int getItemCount() {
    return (isValid ? mybaseAdapter.getItemCount() + sections.size() : 0);
  }

  public static class SectionViewHolder extends RecyclerView.ViewHolder{
    public TextView title;

    public SectionViewHolder(View view, int textResourceId){
      super(view);
      title = (TextView) view.findViewById(textResourceId);
    }
  }

  public static class Section{
    int firstPosition;
    int sectionedPosition;
    CharSequence title;

    public Section(int firstPosition, CharSequence title) {
      this.firstPosition = firstPosition;
      this.title = title;
    }

    public CharSequence getTitle(){
      return title;
    }
  }
}

