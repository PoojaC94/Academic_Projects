package edu.neu.ds.model;

public class ResortSkierVertical {

    private String seasonID;
    private int totalVert;

    public ResortSkierVertical(String seasonID) {
        this.seasonID = seasonID;
    }

    public ResortSkierVertical totalVert(Integer totalVert) {
        this.totalVert = totalVert;
        return this;
    }

    public String getSeasonID() {
        return seasonID;
    }

    public void setSeasonID(String seasonID) {
        this.seasonID = seasonID;
    }

    public int getTotalVert() {
        return totalVert;
    }

    public void setTotalVert(int totalVert) {
        this.totalVert = totalVert;
    }
}
