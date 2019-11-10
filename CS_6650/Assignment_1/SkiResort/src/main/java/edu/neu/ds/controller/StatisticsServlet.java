package edu.neu.ds.controller;

import com.google.gson.Gson;
import edu.neu.ds.dao.DatabaseDAO;
import edu.neu.ds.dto.response.StatsResponse;
import edu.neu.ds.model.EndPointStats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "StatisticsServlet")
public class StatisticsServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(StatisticsServlet.class.getName());
    private DatabaseDAO databaseDAO;

    public void init() {
        databaseDAO = new DatabaseDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Start edu.neu.ds.model.StatisticsServlet Get request");
        response.setContentType("application/json");
        try {
            List<EndPointStats> endPointStats = databaseDAO.getStats();
            if (endPointStats.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            StatsResponse statsResponse = new StatsResponse(endPointStats);
            String jsonString = new Gson().toJson(statsResponse);
            response.getWriter().write(jsonString);
            LOGGER.info("End edu.neu.ds.controller.SkierServlet Get request");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
