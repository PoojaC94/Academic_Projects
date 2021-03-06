package edu.neu.ds.analysis;

import edu.neu.ds.model.Data;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.LiftRide;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Task2 implements Runnable {

    private static final int resortId = 2;
    private static final String seasonId = "2019";
    private static final String dayId = "2";

    private int skierIdStart;
    private int skierIdEnd;
    private int startTime;
    private int endTime;
    private int lifts;
    private ThreadLocalRandom random;
    private SkiersApi apiInstance;
    private AtomicInteger requestsCompleted;
    private AtomicInteger requestsErrored;
    private CountDownLatch countDownLatch;
    private CountDownLatch countDownFullLatch;
    private int numRuns;
    private List<Data> result;
    private String taskName;
    private String threadName;

    public Task2(int skierIdStart, int skierIdEnd, int startTime, int endTime, int lifts, AtomicInteger requestsCompleted,
                 AtomicInteger requestsErrored, CountDownLatch countDownLatch, CountDownLatch countDownFullLatch,
                 int numRuns, List<Data> result, String taskName, String threadName) {
        this.skierIdStart = skierIdStart;
        this.skierIdEnd = skierIdEnd;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lifts = lifts;
        this.requestsCompleted = requestsCompleted;
        this.requestsErrored = requestsErrored;
        this.numRuns = numRuns;
        this.countDownLatch = countDownLatch;
        this.countDownFullLatch = countDownFullLatch;
        this.numRuns = numRuns;
        this.result = result;
        this.taskName = taskName;
        this.threadName = threadName;
        apiInstance = new SkiersApi();
        ApiClient client = apiInstance.getApiClient();
        //client.setBasePath("http://SkierResortLB-9dda70f60e2e3089.elb.us-west-2.amazonaws.com:8080/SkiResort_war/skiers");
        //client.setBasePath("http://localhost:8080/SkiResort/skiers"); // Local
        client.setBasePath("https://20191119t082523-dot-meta-origin-258803.appspot.com/skiers");
        //client.setBasePath("http://34.213.99.58:8080/SkiResort_war/skiers"); // Remote
    }

    public void run() {
        Thread.currentThread().setName(threadName);
        List<Data> localDataList = new ArrayList<>();

        for (int j = 0; j < numRuns; j++) {
            for (int i = skierIdStart; i <= skierIdEnd; i++) {
                int randomSkierId = random.current().ints(1, skierIdStart, skierIdEnd+1)
                        .findFirst().getAsInt();
                int randomLiftId = random.current().ints(1, 1, lifts+1)
                        .findFirst().getAsInt();

                int randomTime = random.current().ints(1, startTime, endTime+1)
                        .findFirst().getAsInt();
                String randomDayId = String.valueOf(random.current().ints(1, 1, 31).findFirst().getAsInt());

                LiftRide ride = new LiftRide();
                ride.setLiftID(randomLiftId);
                ride.setTime(randomTime);


                ApiResponse response = null;

                long start = System.currentTimeMillis();
                long end = 0;

                int responseCode = 500;

                try {
                    response = apiInstance.writeNewLiftRideWithHttpInfo(ride, resortId, seasonId, randomDayId, randomSkierId);
                    responseCode = response.getStatusCode();
                    end = System.currentTimeMillis();
                    requestsCompleted.getAndIncrement();

                } catch (ApiException e) {
                    requestsErrored.getAndIncrement();
                    end = System.currentTimeMillis();
                    System.out.println("API Error");
                    responseCode = e.getCode();
                    e.printStackTrace();
                } catch (Exception e) {
                    requestsErrored.getAndIncrement();
                    System.out.println("Unknown error");
                    end = System.currentTimeMillis();
                    e.printStackTrace();
                } finally {
                    String threadName = Thread.currentThread().getName();
                    Data data = new Data(start, end, end - start,
                            "Request.POST", responseCode, taskName, threadName, j);

                    localDataList.add(data);

                }

                long startTimeForGetLift = System.currentTimeMillis();
                long getLiftRideEnd = 0;
                int getLiftRideCode = 500;
                if (this.taskName.equals("Task3")) {
                    try {

                        ApiResponse<Integer> getNewLiftRideResponse = apiInstance.getSkierDayVerticalWithHttpInfo(resortId, seasonId, randomDayId, randomSkierId);
                        getLiftRideCode = getNewLiftRideResponse.getStatusCode();
                        getLiftRideEnd = System.currentTimeMillis();
                        requestsCompleted.getAndIncrement();

                    } catch (ApiException e) {

                        requestsErrored.getAndIncrement();
                        getLiftRideEnd = System.currentTimeMillis();
                        System.out.println("API Error");
                        getLiftRideCode = e.getCode();
                        e.printStackTrace();

                    } catch (Exception e) {
                        requestsErrored.getAndIncrement();
                        System.out.println("Unknown error");
                        getLiftRideEnd = System.currentTimeMillis();
                        e.printStackTrace();
                    } finally {
                        String threadName = Thread.currentThread().getName();
                        Data data = new Data(startTimeForGetLift, getLiftRideEnd, getLiftRideEnd - startTimeForGetLift,
                                "Request.GET", getLiftRideCode, taskName, threadName, j);

                        localDataList.add(data);

                    }
                }
            }
        }
        synchronized (result) {
            result.addAll(localDataList);
        }
        countDownLatch.countDown();
        countDownFullLatch.countDown();
    }
}
