package edu.neu.ds.model;

public class EndPointStats {
    private String url;

    private String operation;
    private int meanResponse;
    private int maxResponse;

    public EndPointStats(String url, String operation, int meanResponse, int maxResponse) {
        this.url = url;
        this.operation = operation;
        this.meanResponse = meanResponse;
        this.maxResponse = maxResponse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getMeanResponse() {
        return meanResponse;
    }

    public void setMeanResponse(int meanResponse) {
        this.meanResponse = meanResponse;
    }

    public int getMaxResponse() {
        return maxResponse;
    }

    public void setMaxResponse(int maxResponse) {
        this.maxResponse = maxResponse;
    }




}
