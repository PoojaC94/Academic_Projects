package edu.neu.ds;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.ResortsApi;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Test1 {

    public static void postSkiers(SkiersApi apiInstance) {
        try {
            LiftRide ride = new LiftRide();
            ride.setLiftID(5);
            ride.setTime(10);
            ApiResponse response = apiInstance.writeNewLiftRideWithHttpInfo(ride, 12345, "2019", "28", 100);
            System.out.println("code = " + response.getStatusCode());
            System.out.println("Success");
        } catch (ApiException e) {
            System.out.println("Error");
            System.out.println("Code = " + e.getCode());
            e.printStackTrace();
        } finally {
            System.out.println("Complete");
        }
    }

    public static void getSkiersDay(SkiersApi apiInstance) {
        try {
            int value = apiInstance.getSkierDayVertical(12345, "2019", "28", 100);
            System.out.println("Value from GET = " + value);
            System.out.println("Success");
        } catch (ApiException e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            System.out.println("Complete");
        }
    }

    public static void getSkiersTotal(SkiersApi apiInstance) {
        try {

            ApiResponse vertical = apiInstance.getSkierResortTotalsWithHttpInfo(12345, new ArrayList<String>(), new ArrayList<>());
            System.out.println("Value from GET = " + vertical.getStatusCode());
            System.out.println("Success");
        } catch (ApiException e) {
            System.out.println("Error");
            System.out.println(e.getCode());
            System.out.println(e);
            e.printStackTrace();
        } finally {
            System.out.println("Complete");
        }
    }

    public static void postResorts(ResortsApi apiInstance) {
        try {
            Body body = new Body();
            body.setYear("2020");
            apiInstance.addSeason(body, 25);
            System.out.println("Success");
        } catch (ApiException e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            System.out.println("Complete");
        }
    }

    public static void getResortSeasons(ResortsApi apiInstance) {
        try {
            SeasonsList seasonsList = apiInstance.getResortSeasons(25);
            System.out.println("Value from GET = " + seasonsList.getSeasons());
            System.out.println("Success");
        } catch (ApiException e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            System.out.println("Complete");
        }
    }

    public static void getAllResorts(ResortsApi apiInstance) {
        try {
            ResortsList resortsList = apiInstance.getResorts();
            System.out.println("Value from GET = " + resortsList.getResorts());
            System.out.println("Success");
        } catch (ApiException e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            System.out.println("Complete");
        }
    }


    public static void main(String[] args) {
        SkiersApi apiInstance = new SkiersApi();
        ApiClient client = apiInstance.getApiClient();
        client.setBasePath("http://localhost:8080/SkiResort/skiers");
        //client.setBasePath("http://54.245.185.142:8080/SkiResort_war/skiers");

        postSkiers(apiInstance);
        //getSkiersDay(apiInstance);
        //getSkiersTotal(apiInstance);

        ResortsApi apiInstance1 = new ResortsApi();
        ApiClient client1 = apiInstance1.getApiClient();
        client1.setBasePath("http://localhost:8080/SkiResort/resorts");

        //postResorts(apiInstance1);
        //getResortSeasons(apiInstance1);
        //getAllResorts(apiInstance1);
    }
}
