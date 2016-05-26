package com.ostfalia.domain.models;

import com.ostfalia.data.entity.Ride;

import java.util.ArrayList;

/**
 * Helper class to put a an MissingSearchType and an Array in one Object
 */
public class SearchResult {
    private MissingSearchType missingType;
    private ArrayList<Ride> rideList;

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
