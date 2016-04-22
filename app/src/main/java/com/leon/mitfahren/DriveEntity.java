package com.leon.mitfahren;

/**
 * Created by Leon on 22.04.16.
 */
public class DriveEntity {
    /*content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FROM, from);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TO, to);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DEPARTURE, timestampAbfahrt);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ARRIVAL, timestampAnkunft);
    content.put(FeedReaderContract.FeedEntry.COLUMN_NAME_DESCRIPTION, description);*/

    private String from;
    private String to;
    private long timestampAbfahrt;
    private long timestampAnkunft;
    private String description;

    public DriveEntity(String from, String to, long timestampAbfahrt, long timestampAnkunft, String description){
        this.from = from;
        this.to = to;
        this.timestampAbfahrt = timestampAbfahrt;
        this.timestampAnkunft = timestampAnkunft;
        this.description = description;
    }

    public String getHour(){
        return "10";
    }

    public String getMinute(){ return "30"; }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getTimestampAbfahrt() {
        return timestampAbfahrt;
    }

    public void setTimestampAbfahrt(long timestampAbfahrt) {
        this.timestampAbfahrt = timestampAbfahrt;
    }

    public long getTimestampAnkunft() {
        return timestampAnkunft;
    }

    public void setTimestampAnkunft(long timestampAnkunft) {
        this.timestampAnkunft = timestampAnkunft;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
