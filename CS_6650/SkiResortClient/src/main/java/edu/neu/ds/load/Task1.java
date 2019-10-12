package edu.neu.ds.load;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;


public class Task1 implements Runnable {

    private static final int resortId = 12345;
    private static final String seasonId = "2019";
    private static final String dayId = "2019";

    private int skierIdStart;
    private int skierIdEnd;
    private int startTime;
    private int endTime;
    private int lifts;
    private ThreadLocalRandom random;
    private SkiersApi apiInstance;
    private CountDownLatch countDownLatch;
    private CountDownLatch countDownFullLatch;
    private int numRuns;

    public Task1(int skierIdStart, int skierIdEnd, int startTime, int endTime, int lifts, CountDownLatch countDownLatch,
                 CountDownLatch countDownFullLatch, int numRuns) {
        this.skierIdStart = skierIdStart;
        this.skierIdEnd = skierIdEnd;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lifts = lifts;
        this.numRuns = numRuns;
        this.countDownLatch = countDownLatch;
        this.countDownFullLatch = countDownFullLatch;
        this.numRuns = numRuns;
        apiInstance = new SkiersApi();
        ApiClient client = apiInstance.getApiClient();
        //client.setBasePath("http://localhost:8080/SkiResort/skiers"); // Local
        client.setBasePath("http://54.245.185.142:8080/SkiResort_war/skiers"); // Remote
    }

    public void run() {

        for (int j = 0; j < numRuns; j++) {
            for (int i = skierIdStart; i <= skierIdEnd; i++) {
                int randomSkierId = random.current().ints(1, skierIdStart, skierIdEnd+1)
                        .findFirst().getAsInt();
                int randomLiftId = random.current().ints(1, 1, lifts+1)
                        .findFirst().getAsInt();

                int randomTime = random.current().ints(1, startTime, endTime+1)
                        .findFirst().getAsInt();

                LiftRide ride = new LiftRide();
                ride.setLiftID(randomLiftId);
                ride.setTime(randomTime);

                try {
                    apiInstance.writeNewLiftRideWithHttpInfo(ride, resortId, seasonId, dayId, randomSkierId);
                } catch (ApiException e) {
                    System.out.println("API Error");
                } catch (Exception e) {
                    System.out.println("Unknown error");
                }
            }
        }
        countDownLatch.countDown();
        countDownFullLatch.countDown();
    }
}
