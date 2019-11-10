package edu.neu.ds.dto.response;

import edu.neu.ds.model.EndPointStats;

import java.util.ArrayList;
import java.util.List;

public class StatsResponse {
    private List<EndPointStats> endPointStats;
    public StatsResponse(List<EndPointStats> endPointStatsList) {
        this.endPointStats = endPointStatsList;
    }

    public List<EndPointStats> getStats() {
        return endPointStats;
    }

    public void setStats(List<EndPointStats> resortList) {
        this.endPointStats = resortList;
    }

    public StatsResponse addStats(EndPointStats resortsItem) {
        if (this.endPointStats == null) {
            this.endPointStats = new ArrayList<EndPointStats>();
        }
        this.endPointStats.add(resortsItem);
        return this;
    }
}
