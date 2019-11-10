package edu.neu.ds.dto.request;

public class PostSkierRequest {
    private int time;
    private int liftID;


    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLiftId() {
        return liftID;
    }

    public void setLiftId(int liftId) {
        this.liftID = liftId;
    }
}
