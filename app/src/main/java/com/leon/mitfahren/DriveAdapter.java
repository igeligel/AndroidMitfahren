package com.leon.mitfahren;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Leon on 22.04.16.
 */
public class DriveAdapter extends ArrayAdapter<DriveEntity> {

    public DriveAdapter(Context context, ArrayList<DriveEntity> drives) {
        super(context, 0, drives);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DriveEntity driveEntitys = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_drive, parent, false);
        }
        // Lookup view for data population
        TextView driveFrom = (TextView) convertView.findViewById(R.id.searchedFrom);
        TextView driveTo = (TextView) convertView.findViewById(R.id.searchedTo);
        TextView departureTimeHour = (TextView) convertView.findViewById(R.id.searchedDepartureTimeHour);
        TextView departureTimeMinute = (TextView) convertView.findViewById(R.id.searchedDepartureTimeMinute);
        // Populate the data into the template view using the data object
        driveFrom.setText(driveEntitys.getFrom());
        driveTo.setText(driveEntitys.getTo());
        departureTimeHour.setText(driveEntitys.getHour() + ":");
        departureTimeMinute.setText(driveEntitys.getMinute());
        // Return the completed view to render on screen
        return convertView;
    }
}
