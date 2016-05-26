package com.ostfalia.domain.models;

import com.ostfalia.data.entity.Ride;

import java.util.ArrayList;

/**
 *
 */
public class SearchResult {
    public MissingSearchType missingType;
    public ArrayList<Ride> rideList;

    public SearchResult (MissingSearchType searchType, ArrayList <Ride> rideList) {
        this.missingType = searchType;
        this.rideList = rideList;
    }

    public MissingSearchType getMissingType() {
        return missingType;
    }
    public ArrayList<Ride> getRideList() {
        return  rideList;
    }
}
