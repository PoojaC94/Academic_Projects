package edu.neu.ds.model;

public class Data {
    private long startTime;
    private long endTime;
    private long latency;
    private String requestType;
    private int responseCode;
    private String task;
    private String thread;
    private int numRun;


    public Data(long startTime, long endTime, long latency, String requestType, int responseCode, String task, String thread, int numRun) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.latency = latency;
        this.requestType = requestType;
        this.responseCode = responseCode;
        this.task = task;
        this.thread = thread;
        this.numRun = numRun;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getLatency() {
        return latency;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getTask() {
        return task;
    }

    public String getThread() {
        return thread;
    }

    public int getNumRun() {
        return numRun;
    }
}
