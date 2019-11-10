package edu.neu.ds.dto.response;

import java.util.List;

public class SeasonResponse {

    private List<String> seasons;

    public SeasonResponse() {

    }
    public SeasonResponse(List<String> seasons) {
        this.seasons = seasons;
    }

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }
}
