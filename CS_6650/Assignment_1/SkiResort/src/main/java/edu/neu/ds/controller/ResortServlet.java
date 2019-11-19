package edu.neu.ds.controller;

import com.google.gson.Gson;
import edu.neu.ds.dao.ConnectionPoolContextListener;
import edu.neu.ds.dao.ResortsDAO;
import edu.neu.ds.dao.SeasonsDAO;
import edu.neu.ds.dto.request.PostSeasonRequest;
import edu.neu.ds.dto.response.ResortResponse;
import edu.neu.ds.dto.response.SeasonResponse;
import edu.neu.ds.model.Resort;
import edu.neu.ds.task.SkierBackgroundTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//@WebServlet(name = "edu.neu.ds.controller.ResortServlet")
public class ResortServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(ResortServlet.class.getName());
    private ResortsDAO resortDAO;
    private SeasonsDAO seasonsDAO;
    private List<Long> latencyForGetResorts;
    private List<Long> latencyForPostSeasons;
    private List<Long> latencyForGetSeasons;
    private ScheduledExecutorService scheduler;
    private static final String postOp = "POST";
    private static final String getOp = "GET";
    private static final String getResortURL = "/resorts";
    private static final String getSeasonURL = "/resorts/{resortID}/seasons";
    private static final String postSeasonURL = "/resorts/{resortID}/seasons";
    private static DataSource pool;

    public void init() {
        DataSource pool = (DataSource) getServletContext().getAttribute("my-pool");
        resortDAO = new ResortsDAO(pool);
        seasonsDAO = new SeasonsDAO(pool);
        latencyForGetResorts = Collections.synchronizedList(new ArrayList());
        latencyForPostSeasons = Collections.synchronizedList(new ArrayList());
        latencyForGetSeasons = Collections.synchronizedList(new ArrayList());
        scheduler = Executors.newScheduledThreadPool(3);
        //pool = ConnectionPoolContextListener.createConnectionPool();



    }

    public void destroy() {
        LOGGER.info("Destroy called");
        scheduler.shutdownNow();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long startTimeForPostSeason = System.currentTimeMillis();

//        DataSource pool = (DataSource) request.getServletContext().getAttribute("my-pool");
        scheduler.scheduleAtFixedRate(new SkierBackgroundTask(latencyForGetResorts, getResortURL, postOp, pool),
                1, 1, TimeUnit.MINUTES);
        long latency = 0;

        try {
            LOGGER.info("Start edu.neu.ds.controller.ResortServlet Post request");
            response.setContentType("application/json");
            String urlPath = request.getPathInfo();

            LOGGER.info("UrlPath = " + urlPath);
            if (urlPath == null || urlPath.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Missing parameters");
                return;
            }

            List<String> urlParts = new ArrayList(Arrays.asList(urlPath.split("/")));

            if (!isUrlValid(urlParts)) {
                LOGGER.error("Invalid url");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }


            PostSeasonRequest postSeasonRequest = new Gson().fromJson(request.getReader(), PostSeasonRequest.class);
            System.out.println(postSeasonRequest.getYear());
            seasonsDAO.insertSeason(Integer.parseInt(urlParts.get(1)), postSeasonRequest.getYear());

            LOGGER.info("End edu.neu.ds.controller.ResortServlet Post request");

            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            long endTimeForPostSeason = System.currentTimeMillis();
            latency = endTimeForPostSeason - startTimeForPostSeason;
            synchronized (latencyForPostSeasons) {
                latencyForPostSeasons.add(latency);
            }
        }
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        long startTimeForGet = System.currentTimeMillis();
        long latency = 0;
        boolean getSeason = false;
        try {
            LOGGER.info("Start edu.neu.ds.model.Resort Servlet Get request");
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
            String action = req.getServletPath();

            if (!isUrlValid(urlParts)) {
                LOGGER.error("Invalid url");
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            DataSource pool = (DataSource) req.getServletContext().getAttribute("my-pool");

            if (urlParts.size() == 1) {
                scheduler.scheduleAtFixedRate(new SkierBackgroundTask(latencyForPostSeasons, getSeasonURL, getOp, pool),
                        1, 1, TimeUnit.MINUTES);
                List<Resort> resorts = resortDAO.listAllResorts(pool);

                ResortResponse resortResponse = new ResortResponse(resorts);
                String jsonString = new Gson().toJson(resortResponse);
                res.getWriter().write(jsonString);

                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().flush();
                res.getWriter().close();
            } else {
                getSeason = true;
                scheduler.scheduleAtFixedRate(new SkierBackgroundTask(latencyForGetSeasons, postSeasonURL, getOp, pool),
                        1, 1, TimeUnit.MINUTES);
                  List<String> seasons = seasonsDAO.listAllSeasons(Integer.parseInt(urlParts.get(1)));

                SeasonResponse seasonResponse = new SeasonResponse(seasons);

                String jsonString = new Gson().toJson(seasonResponse);
                res.getWriter().write(jsonString);
                res.setStatus(HttpServletResponse.SC_OK);
                res.getWriter().flush();
                res.getWriter().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            long endTimeForGet = System.currentTimeMillis();
            latency = endTimeForGet - startTimeForGet;
            if (getSeason) {
                synchronized (latencyForGetSeasons) {
                    latencyForGetSeasons.add(latency);
                }
            } else {
                synchronized (latencyForGetResorts) {
                    latencyForGetResorts.add(latency);
                }
            }
        }
    }

    private boolean isUrlValid(List<String> urlPath) {
        if (urlPath == null || urlPath.isEmpty()) {
            return false;
        }
        urlPath.remove(0);

        boolean isValidUrl;
        switch (urlPath.size()) {
            case 3:
                isValidUrl = isValidSeasonsUrl(urlPath);
                break;
            case 1:
                isValidUrl = urlPath.get(0).equals("resorts");
                break;
            default:
                LOGGER.error("Invalid size");
                isValidUrl = false;
        }
        return isValidUrl;
    }

    private boolean isValidSeasonsUrl(List<String> urlPath) {
        boolean isValidResortsString = urlPath.get(0).equals("resorts");
        boolean isValidSeasonsString = urlPath.get(2).equals("seasons");
        return isValidResortsString && isValidResortId(urlPath.get(1)) && isValidSeasonsString;
    }

    private boolean isValidResortId(String resortId) {
        try {
            Integer.parseInt(resortId);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
