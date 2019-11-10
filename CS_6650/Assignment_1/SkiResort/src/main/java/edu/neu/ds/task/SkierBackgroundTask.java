package edu.neu.ds.task;


import edu.neu.ds.controller.SkierServlet;
import edu.neu.ds.dao.StatisticsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class SkierBackgroundTask implements Runnable {
    private StatisticsDAO statisticsDAO;
    private List<Long> latencies;
    private String url;
    private String operation;
    private static final Logger LOGGER = LogManager.getLogger(SkierServlet.class.getName());

    public SkierBackgroundTask(List<Long> latencies, String url, String operation) {
        this.latencies = latencies;
        this.url = url;
        this.operation = operation;
        statisticsDAO = new StatisticsDAO();
    }

    @Override
    public void run() {
        LOGGER.info("Working on background job");
        try {
            statisticsDAO.insertStats(this.latencies, this.url, this.operation);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}