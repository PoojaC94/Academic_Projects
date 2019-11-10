package edu.neu.ds.dto.response;

import edu.neu.ds.model.ResortSkierVertical;

import java.util.ArrayList;
import java.util.List;

public class SkierVerticalResponse {

    private List<ResortSkierVertical> resorts;

    public SkierVerticalResponse(List<ResortSkierVertical> resortList) {
        this.resorts = resortList;
    }

    public List<ResortSkierVertical> getResorts() {
        return resorts;
    }

    public void setResorts(List<ResortSkierVertical> resortList) {
        this.resorts = resortList;
    }

    public SkierVerticalResponse addResortsItem(ResortSkierVertical resortsItem) {
        if (this.resorts == null) {
            this.resorts = new ArrayList<ResortSkierVertical>();
        }
        this.resorts.add(resortsItem);
        return this;
    }
}
