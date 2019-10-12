package edu.neu.ds.analysis;

public class Client2Data {

    private int threads;
    private long throughput;
    private double maxResponseTime;

    public Client2Data(int threads, long throughput, double maxResponseTime) {
        this.threads = threads;
        this.throughput = throughput;
        this.maxResponseTime = maxResponseTime;
    }

    public int getThreads() {
        return threads;
    }

    public long getThroughput() {
        return throughput;
    }

    public double getMaxResponseTime() {
        return maxResponseTime;
    }
}
