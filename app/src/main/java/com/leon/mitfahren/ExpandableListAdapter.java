package com.leon.mitfahren;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Leon on 29.04.16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

  private Context _context;
  private List<String> _listDataHeader; // header titles
  // child data in format of header title, child title
  private HashMap<String, List<String>> _listDataChild;

  public ExpandableListAdapter(Context context, List<String> listDataHeader,
                               HashMap<String, List<String>> listChildData) {
    this._context = context;
    this._listDataHeader = listDataHeader;
    this._listDataChild = listChildData;
  }

  @Override
  public Object getChild(int groupPosition, int childPosititon) {
    return this._listDataChild.get(this._listDataHeader.get(groupPosition));
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

    ArrayList<String> childText = (ArrayList<String>) getChild(groupPosition, childPosition);
    if (convertView == null) {
      LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.list_item, null);
    }

    TextView listItem = (TextView) convertView.findViewById(R.id.listItem);

    String textViewText = "NaN";
    if (childPosition == 0) {
      textViewText = "Von: " + childText.get(childPosition);
    } else if (childPosition == 1) {
      textViewText = "Nach: " + childText.get(childPosition);
    }else if (childPosition == 2) {
      textViewText = "Abfahrt: " + childText.get(childPosition);
    }else if (childPosition == 3) {
      textViewText = "Ankunft: " + childText.get(childPosition);
    } else if (childPosition == 4) {
      textViewText = "Beschreibung: " + childText.get(childPosition);
    }

    listItem.setText(textViewText);
    return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return this._listDataChild.get(this._listDataHeader.get(groupPosition))
      .size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return this._listDataHeader.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    if (this._listDataHeader == null) return 0;
    return this._listDataHeader.size();
  }

  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    if (convertView == null) {
      LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = infalInflater.inflate(R.layout.list_group, null);
    }
    TextView departureCity = (TextView) convertView.findViewById(R.id.departurecity);
    TextView arrivalCity = (TextView) convertView.findViewById(R.id.arrivalcity);
    TextView time = (TextView) convertView.findViewById(R.id.timeTextView);


    departureCity.setText(_listDataChild.get(_listDataHeader.get(groupPosition)).get(0));
    arrivalCity.setText(_listDataChild.get(_listDataHeader.get(groupPosition)).get(1));
    time.setText(_listDataChild.get(_listDataHeader.get(groupPosition)).get(2));
    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
}
