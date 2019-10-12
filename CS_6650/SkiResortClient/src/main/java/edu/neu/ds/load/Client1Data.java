package edu.neu.ds.load;

public class Client1Data {

    private int threads;
    private long wallTime;

    public int getThreads() {
        return threads;
    }

    public long getWallTime() {
        return wallTime;
    }

    public Client1Data(int threads, long wallTime) {
        this.threads = threads;
        this.wallTime = wallTime;
    }
}
