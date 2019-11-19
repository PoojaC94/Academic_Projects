package edu.neu.ds;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.api.ResortsApi;
import io.swagger.client.api.SkiersApi;
import io.swagger.client.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test1 {

    public static void postSkiers(SkiersApi apiInstance) {
        try {
            LiftRide ride = new LiftRide();
            ride.setLiftID(21);
            ride.setTime(10);

            //ApiResponse response = apiInstance.writeNewLiftRideWithHttpInfo(ride, 5, "2019", "28", 100);
            ApiResponse response = apiInstance.writeNewLiftRideWithHttpInfo(ride, 2, "2019", "24", 555);
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
            ApiResponse<Integer> apiResponse = apiInstance.getSkierDayVerticalWithHttpInfo(2, "2019", "24", 555);

            System.out.println("Value from GET = " + apiResponse.getData());
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
            List<String> list = new ArrayList<>();
            list.add("2");
            List<String> seasons = new ArrayList();
            //seasons.add("2019");
            ApiResponse<SkierVertical> vertical = apiInstance.getSkierResortTotalsWithHttpInfo(555, list, seasons);
            System.out.println("Value from GET1 = " + vertical.getStatusCode());
            System.out.println("Data: " + vertical.getData());
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
            Body body1 = new Body();
            body1.setYear("2019");
            //apiInstance.addSeason(body, 2);
            apiInstance.addSeason(body1, 4);
            //apiInstance.addSeason(body1, 4);

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
            SeasonsList seasonsList = apiInstance.getResortSeasons(4);
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
            System.out.println("Value from GET Resorts = " + resortsList.getResorts());
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
        //client.setBasePath("http://localhost:8080/SkiResort/skiers");
        //client.setBasePath("http://34.216.114.233:8080/SkiResort_war/skiers");
        //client.setBasePath("http://54.202.88.174:8080/SkiResort_war/skiers");
        //client.setBasePath("http://34.213.99.58:8080/SkiResort_war/skiers");
        client.setBasePath("http://20191118t221844-dot-meta-origin-258803.appspot.com/skiers");
        //client.setBasePath("http://54.211.201.179:8080/SkiResort_war/skiers");
        //client.setBasePath("http://3.87.198.22:8080/SkiResort_war/skiers");
        //client.setBasePath("http://54.86.81.176:8080/SkiResort_war/skiers");
        //client.setBasePath("http://SkiResortLB-21eeac47a4c0ece4.elb.us-east-1.amazonaws.com:8080/SkiResort_war/skiers");
        postSkiers(apiInstance);
        getSkiersDay(apiInstance);
        getSkiersTotal(apiInstance);

//        ResortsApi apiInstance1 = new ResortsApi();
//        ApiClient client1 = apiInstance1.getApiClient();
//        client1.setBasePath("http://localhost:8080/SkiResort/resorts");
//        client1.setBasePath("http://54.245.185.142:8080/SkiResort_war/skiers");


//        postResorts(apiInstance1);
//        getResortSeasons(apiInstance1);
        //getAllResorts(apiInstance1);
    }
}
