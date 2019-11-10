package edu.neu.ds.dao;

import edu.neu.ds.controller.SkierServlet;
import edu.neu.ds.model.EndPointStats;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO {
    private static final Logger LOGGER = LogManager.getLogger(SkierServlet.class.getName());
    private static final String postOp = "POST";
    private static final String getOp = "GET";
    private static final String postURL = "/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}";
    private static final String getVerticalURL = "/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<EndPointStats> getStats() throws SQLException {

        List<EndPointStats> result = new ArrayList<>();
        String sql = "SELECT latency FROM Statistics WHERE api = ? AND operation = ?";

        insertStats(sql, postURL, postOp, result);
        insertStats(sql, getVerticalURL, getOp, result);
        return result;
    }

    private void insertStats(String sql, String url, String operation, List<EndPointStats> result) throws SQLException {

        try (Connection jdbcConnection = DBCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, url);
            statement.setString(2, operation);

            ResultSet resultSet = statement.executeQuery();
            int max = 0;
            int count = 0;
            int sumOfLatencies = 0;
            while (resultSet.next()) {
                int latency = resultSet.getInt("latency");
                sumOfLatencies += latency;
                max = Math.max(max, latency);
                count++;
            }

            int mean = sumOfLatencies / count;
            EndPointStats stats = new EndPointStats(url, operation, mean, max);
            result.add(stats);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
