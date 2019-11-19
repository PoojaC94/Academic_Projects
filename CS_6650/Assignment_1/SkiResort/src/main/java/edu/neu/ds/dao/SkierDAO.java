package edu.neu.ds.dao;

import edu.neu.ds.controller.ResortServlet;
import edu.neu.ds.dto.response.SkierVerticalResponse;
import edu.neu.ds.model.Resort;
import edu.neu.ds.model.ResortSkierVertical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import javax.sql.ConnectionPoolDataSource;
import javax.servlet.ServletContextListener;
import java.sql.*;

import java.util.*;

public class SkierDAO {
    private static final Logger LOGGER = LogManager.getLogger(SkierDAO.class.getName());
    private static DataSource pool;
//    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public SkierDAO(DataSource pool) {
        LOGGER.info("Initializing pool in SkierDao");
        this.pool = pool;
    }

    public boolean insertSkier(int resortId, String seasonId, String dayId, int skierId, int timeTaken, int liftId) throws Exception {
        String sql = "INSERT INTO Skiers (resort_id, season_id, day_id, skier_id, time, lift_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection jdbcConnection = HikariCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, resortId);
            statement.setString(2, seasonId);
            statement.setString(3, dayId);
            statement.setInt(4, skierId);
            statement.setInt(5, timeTaken);
            statement.setInt(6, liftId);

            return statement.executeUpdate() > 0;
        } catch(SQLIntegrityConstraintViolationException e) {
            LOGGER.info("duplicate found and ignored");
            return true;
        } catch (SQLException e) {
            LOGGER.info("Exception found");
            e.printStackTrace();
        }
        return false;
    }

    public int getVertical(int resortId, String seasonId, String dayId, int skierId) throws Exception {
        String sql = "SELECT lift_id FROM Skiers WHERE resort_id = ? AND season_id = ? AND day_id = ? AND skier_id = ?";

        try (Connection jdbcConnection = HikariCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, resortId);
            statement.setString(2, seasonId);
            statement.setString(3, dayId);
            statement.setInt(4, skierId);

            ResultSet resultSet = statement.executeQuery();
            int ans = 0;
            LOGGER.info("ResultSet executed");
            resultSet.next();
            LOGGER.info("ResultSet next");
            int vertical = resultSet.getInt("lift_id");
            ans = vertical * 10;
            return ans;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }

    public SkierVerticalResponse getAllVerticals(int skierID, String resort) throws Exception {
        String sql = "SELECT season_id, lift_id FROM Skiers WHERE skier_id = ? AND resort_id = ?";

        try (Connection jdbcConnection = HikariCPDataSource.getConnection();//HikariCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, skierID);
            String[] strs = resort.split("=");
            statement.setInt(2, Integer.parseInt(strs[1]));
            LOGGER.info("Statement set");
            ResultSet resultSet = statement.executeQuery();
            LOGGER.info("ResultSet executed");
            SkierVerticalResponse skierVerticalResponse = new SkierVerticalResponse(new ArrayList<ResortSkierVertical>());
            addToResponse(resultSet, skierVerticalResponse);
            return skierVerticalResponse;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SkierVerticalResponse getAllVerticals(int skierID, String resort, String season) throws Exception {
        String sql = "SELECT season_id, lift_id FROM Skiers WHERE skier_id = ? AND resort_id = ? AND season_id = ?";

        try (Connection jdbcConnection = HikariCPDataSource.getConnection(); //HikariCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, skierID);
            SkierVerticalResponse skierVerticalResponse = new SkierVerticalResponse(new ArrayList<ResortSkierVertical>());
            String[] strs = resort.split("=");
            statement.setInt(2, Integer.parseInt(strs[1]));

            for (String seasonSplit : season.split(" ")) {
                String[] seasons = seasonSplit.split("=");
                statement.setString(3, seasons[1]);
                ResultSet resultSet = statement.executeQuery();
                addToResponse(resultSet, skierVerticalResponse);

            }
            return skierVerticalResponse;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addToResponse(ResultSet resultSet, SkierVerticalResponse skierVerticalResponse) throws SQLException {
        while (resultSet.next()) {
            String seasonString = resultSet.getString("season_id");
            System.out.println(seasonString);
            int vertical = resultSet.getInt("lift_id");
            System.out.println(vertical);
            ResortSkierVertical resortSkierVertical = new ResortSkierVertical(seasonString);
            resortSkierVertical.totalVert(vertical * 10);
            skierVerticalResponse.addResortsItem(resortSkierVertical);
        }
    }


}
