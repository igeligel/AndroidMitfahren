package com.ostfalia.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.ostfalia.mitfahren.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Adapter for the list of possible rides
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

  private Context _context;
  private List<String> _listDataHeader;
  private HashMap<String, List<String>> _listDataChild;

  /**
   * Constructor for the ExpandableList.
   * @param context context of the application
   * @param listDataHeader List of data headers for our data
   * @param listChildData List of data
   */
  public ExpandableListAdapter(Context context, List<String> listDataHeader,
                               HashMap<String, List<String>> listChildData) {
    this._context = context;
    this._listDataHeader = listDataHeader;
    this._listDataChild = listChildData;
  }

  /**
   * gets data out of the ExpandableList
   * @param groupPosition position of the element
   * @param childPosititon position of the child element
   * @return Object with our data
   */
  @Override
  public Object getChild(int groupPosition, int childPosititon) {
    return this._listDataChild.get(this._listDataHeader.get(groupPosition));
  }

  /**
   * Method to get the Position of data
   * @param groupPosition position of the element
   * @param childPosition position of the child element
   * @return position of the child/data
   */
  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  /**
   * Method to get the View of one Child Element of the Expandable List.
   * @param groupPosition group Position of the Element.
   * @param childPosition child Position of the Element.
   * @param isLastChild boolean if the child is the last child.
   * @param convertView convertView
   * @param parent parentView
   * @return View of the Child
   */
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

  /**
   * Gets the size of the childrens for one data header.
   * @param groupPosition position of the data header.
   * @return # of the childrens.
   */
  @Override
  public int getChildrenCount(int groupPosition) {
    return this._listDataChild.get(this._listDataHeader.get(groupPosition))
      .size();
  }

  /**
   * Gets the Group for a group position
   * @param groupPosition group position
   * @return data header on the given position
   */
  @Override
  public Object getGroup(int groupPosition) {
    return this._listDataHeader.get(groupPosition);
  }

  /**
   * Gets the # of Groups in the Expandable List
   * @return + of Groups
   */
  @Override
  public int getGroupCount() {
    if (this._listDataHeader == null) return 0;
    return this._listDataHeader.size();
  }

  /**
   * Gets the group id of a group position.
   * @param groupPosition group position.
   * @return id of the group position.
   */
  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  /**
   * Method to get the View of a group.
   * @param groupPosition position of the group.
   * @param isExpanded boolean if it is expandet.
   * @param convertView actual view.
   * @param parent parent element of the group.
   * @return View of a group.
   */
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
