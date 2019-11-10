package edu.neu.ds.controller;

import com.google.gson.Gson;
import edu.neu.ds.dao.SkierDAO;
import edu.neu.ds.dto.request.PostSkierRequest;
import edu.neu.ds.dto.response.SkierResponse;
import edu.neu.ds.dto.response.SkierVerticalResponse;
import edu.neu.ds.task.SkierBackgroundTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "edu.neu.ds.controller.SkierServlet")
public class SkierServlet extends HttpServlet {

    private Random random = new Random();
    private static final Logger LOGGER = LogManager.getLogger(SkierServlet.class.getName());
    private SkierDAO skierDAO;
    private List<Long> latencyForGetSkiers;
    private List<Long> latencyForPostSkiers;
    private List<Long> latencyForGetAllSkiers;
    private ScheduledExecutorService scheduler;
    private static final String postOp = "POST";
    private static final String getOp = "GET";
    private static final String postURL = "/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}";
    private static final String getVerticalURL = "/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}";
    private static final String getAllSeasonsURL = "/skiers/{skierID}/vertical";

    public void init() {
        skierDAO = new SkierDAO();
        latencyForGetSkiers = Collections.synchronizedList(new ArrayList());
        latencyForPostSkiers = Collections.synchronizedList(new ArrayList());
        latencyForGetAllSkiers = Collections.synchronizedList(new ArrayList());
        scheduler = Executors.newScheduledThreadPool(3);
        scheduler.scheduleAtFixedRate(new SkierBackgroundTask(latencyForPostSkiers, postURL, postOp),
                    1, 1, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(new SkierBackgroundTask(latencyForGetSkiers, getVerticalURL, getOp),
                1, 1, TimeUnit.MINUTES);
        scheduler.scheduleAtFixedRate(new SkierBackgroundTask(latencyForGetAllSkiers, getAllSeasonsURL, getOp),
                1, 1, TimeUnit.MINUTES);
    }

    public void destroy() {
        LOGGER.info("Destroy called");
        scheduler.shutdownNow();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
        //
        long startTimeForPostLift = System.currentTimeMillis();

        try {
            LOGGER.info("Start edu.neu.ds.controller.SkierServlet Post request");
            res.setContentType("application/json");
            String urlPath = request.getPathInfo();
            BufferedReader bufferedReader = request.getReader();


            LOGGER.info("UrlPath = " + urlPath);
            if (urlPath == null || urlPath.isEmpty()) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                res.getWriter().write("Missing parameters");

                return;
            }

            List<String> urlParts = new ArrayList(Arrays.asList(urlPath.split("/")));

            if (!isUrlValid(urlParts, new ArrayList())) {
                LOGGER.error("Invalid url");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                return;
            }
            String s = null;
            PostSkierRequest postSkierRequest  = new Gson().fromJson(request.getReader(), PostSkierRequest.class);

            if(!skierDAO.insertSkier(Integer.parseInt(urlParts.get(1)), urlParts.get(3), urlParts.get(5),
                    Integer.parseInt(urlParts.get(7)), postSkierRequest.getTime(), postSkierRequest.getLiftId())) {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                return;
            }

            LOGGER.info("End edu.neu.ds.controller.SkierServlet Post request");
            res.setStatus(HttpServletResponse.SC_CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } finally {
            long endTimeForPostLift = System.currentTimeMillis();
            long latency = endTimeForPostLift - startTimeForPostLift;
            synchronized (latencyForPostSkiers) {
                latencyForPostSkiers.add(latency);
            }

        }

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        long startTimeForGetLift = System.currentTimeMillis();
        boolean getAll = false;
        try {
            LOGGER.info("Start SkierServlet Get request");
            res.setContentType("application/json");
            String urlPath = req.getPathInfo();
            String queryString = req.getQueryString();

            LOGGER.info("UrlPath = " + urlPath);
            if (urlPath == null || urlPath.isEmpty()) {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                res.getWriter().write("Missing parameters");

                return;
            }

            List<String> urlParts = new ArrayList(Arrays.asList(urlPath.split("/")));
            List<String> queryParts;

            if (queryString == null || queryString.isEmpty()) {
                LOGGER.info("Get Vertical");
                queryParts = new ArrayList<String>();
                if (!isUrlValid(urlParts, queryParts)) {
                    LOGGER.error("Invalid url");
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    return;
                }

                int vertical = skierDAO.getVertical(Integer.parseInt(urlParts.get(1)), urlParts.get(3), urlParts.get(5), Integer.parseInt(urlParts.get(7)));
                if (vertical == 0) {
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
                SkierResponse skierResponse = new SkierResponse(vertical);
                String jsonString = new Gson().toJson(skierResponse.getVertical());
                res.getWriter().write(jsonString);

                LOGGER.info("End edu.neu.ds.controller.SkierServlet Get request");
                res.setStatus(HttpServletResponse.SC_OK);

                res.getWriter().flush();
                res.getWriter().close();

            } else {
                getAll = true;
                queryParts = Arrays.asList(queryString.split("&"));
                LOGGER.info("Get All Verticals");

                if (!isUrlValid(urlParts, queryParts)) {
                    LOGGER.error("Invalid url or query parameter");
                    res.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    return;
                }
                if (queryParts.size() == 1) {
                    SkierVerticalResponse skierVerticalResponse = skierDAO.getAllVerticals(Integer.parseInt(urlParts.get(1)), queryParts.get(0));
                    if (skierVerticalResponse == null) {
                        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                        return;
                    }
                    String jsonString = new Gson().toJson(skierVerticalResponse);
                    res.getWriter().write(jsonString);

                } else {
                    SkierVerticalResponse skierVerticalResponse = skierDAO.getAllVerticals(Integer.parseInt(urlParts.get(1)), queryParts.get(0), queryParts.get(1));
                    if (skierVerticalResponse == null) {
                        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                        return;
                    }
                    String jsonString = new Gson().toJson(skierVerticalResponse);
                    res.getWriter().write(jsonString);
                }
                LOGGER.info("End edu.neu.ds.controller.SkierServlet Get request");
                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().flush();
                res.getWriter().close();

            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            long endTimeForGetLift = System.currentTimeMillis();
            long latency = endTimeForGetLift - startTimeForGetLift;
            if (getAll) {
                synchronized (latencyForGetAllSkiers) {
                    latencyForGetAllSkiers.add(latency);
                }
            } else {
                synchronized (latencyForGetSkiers) {
                    latencyForGetSkiers.add(latency);
                }
            }
        }
    }

    private boolean isUrlValid(List<String> urlPath, List<String> queryParts) {
        if (urlPath == null || urlPath.isEmpty()) {
            return false;
        }
        urlPath.remove(0);

        boolean isValidUrl;
        switch (urlPath.size()) {
            case 3:
                isValidUrl = isValidVerticalUrl(urlPath, queryParts);
                break;
            case 8:
                isValidUrl = isValidLiftUrl(urlPath, queryParts);
                break;
            default:
                LOGGER.error("Invalid size");
                isValidUrl = false;
        }
        return isValidUrl;
    }

    private boolean isValidVerticalUrl(List<String> urlPath, List<String> queryParts) {
        if (queryParts == null || queryParts.size() < 1) {
            return false;
        }

        boolean isValidSkiersString = urlPath.get(0).equals("skiers");
        boolean isValidSkierId = isValidSkierId(urlPath.get(1));
        boolean isValidVerticalString = urlPath.get(2).equals("vertical");
        boolean isValidQueryString = isValidQueryStringForVerticalUrl(queryParts);

        return isValidSkierId && isValidVerticalString && isValidQueryString && isValidSkiersString;
    }

    private boolean isValidLiftUrl(List<String> urlPath, List<String> queryParts) {
        if (queryParts != null && queryParts.size() > 0) {
            LOGGER.error("Invalid query parts");
            return false;
        }

        boolean isValidResortId = isValidResortId(urlPath.get(1));
        boolean isValidSeasonsString = urlPath.get(2).equals("seasons");
        boolean isValidDaysString = urlPath.get(4).equals("days");
        boolean isValidSkiersString = urlPath.get(6).equals("skiers");
        boolean isValidSkierId = isValidSkierId(urlPath.get(7));

        return isValidResortId && isValidSeasonsString && isValidDaysString && isValidSkiersString && isValidSkierId;
    }

    private boolean isValidSkierId(String skierId) {
        try {
            Integer.parseInt(skierId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidResortId(String resortId) {
        try {
            Integer.parseInt(resortId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidQueryStringForVerticalUrl(List<String> queryParts) {
        String queryPart = queryParts.get(0);
        String[] resortParts = queryPart.split("=");
        if (resortParts != null && resortParts.length == 2) {
            return resortParts[0].equals("resort");
        }
        return false;
    }
}
