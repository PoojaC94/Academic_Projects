package edu.neu.ds.dao;

import edu.neu.ds.controller.SkierServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class StatisticsDAO {
    private static final Logger LOGGER = LogManager.getLogger(SkierServlet.class.getName());
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertStats(List<Long> latencies, String url, String operation) throws SQLException {

        if (latencies.isEmpty()) {
            LOGGER.info("LATENCIES IS EMPTY");
            return;
        }

        List<Long> latencyCopy = null;
        synchronized (latencies) {
            latencyCopy = new ArrayList(latencies);
            latencies.clear();
        }

        final int columnCount = 3;
        final StringBuilder builder = new StringBuilder("INSERT INTO Statistics (api, operation, latency) VALUES ");
        final String placeholders = "(?, ?, ?)";
        for ( int i = 0; i < latencyCopy.size(); i++ ) {
            if ( i != 0 ) {
                builder.append(",");
            }
            builder.append(placeholders);

        }


        final int maxParameter = (latencyCopy.size()) * columnCount;
        final String query = builder.toString();
        try (Connection jdbcConnection = DBCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(query)) {
            for (int parameterIndex = 1, valueIndex = 0; parameterIndex <= maxParameter; valueIndex++) {
                statement.setString(parameterIndex++, url);
                statement.setString(parameterIndex++, operation);
                statement.setObject(parameterIndex++, latencyCopy.get(valueIndex));
            }
            statement.execute();

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }
}
