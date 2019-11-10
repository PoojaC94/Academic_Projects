package edu.neu.ds.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import edu.neu.ds.controller.ResortServlet;
import edu.neu.ds.dto.response.SkierVerticalResponse;
import edu.neu.ds.model.Resort;
import edu.neu.ds.model.ResortSkierVertical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.*;

public class SkierDAO {
    private static final Logger LOGGER = LogManager.getLogger(ResortServlet.class.getName());

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean insertSkier(int resortId, String seasonId, String dayId, int skierId, int timeTaken, int liftId) throws SQLException {
        String sql = "INSERT INTO Skiers (resort_id, season_id, day_id, skier_id, time, lift_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection jdbcConnection = DBCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, resortId);
            statement.setString(2, seasonId);
            statement.setString(3, dayId);
            statement.setInt(4, skierId);
            statement.setInt(5, timeTaken);
            statement.setInt(6, liftId);

            return statement.executeUpdate() > 0;
        } catch(MySQLIntegrityConstraintViolationException e) {
            LOGGER.info("duplicate found and ignored");
            return true;
        } catch (SQLException e) {
            LOGGER.info("Exception found");
            e.printStackTrace();
        }
        return false;
    }

    public int getVertical(int resortId, String seasonId, String dayId, int skierId) throws SQLException {
        String sql = "SELECT lift_id FROM Skiers WHERE resort_id = ? AND season_id = ? AND day_id = ? AND skier_id = ?";

        try (Connection jdbcConnection = DBCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, resortId);
            statement.setString(2, seasonId);
            statement.setString(3, dayId);
            statement.setInt(4, skierId);

            ResultSet resultSet = statement.executeQuery();
            int ans = 0;

            resultSet.next();

            int vertical = resultSet.getInt("lift_id");
            ans = vertical * 10;
            return ans;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }

    public SkierVerticalResponse getAllVerticals(int skierID, String resort) throws SQLException {
        String sql = "SELECT season_id, lift_id FROM Skiers WHERE skier_id = ? AND resort_id = ?";

        try (Connection jdbcConnection = DBCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, skierID);
            String[] strs = resort.split("=");
            statement.setInt(2, Integer.parseInt(strs[1]));

            ResultSet resultSet = statement.executeQuery();
            SkierVerticalResponse skierVerticalResponse = new SkierVerticalResponse(new ArrayList<ResortSkierVertical>());
            addToResponse(resultSet, skierVerticalResponse);
            return skierVerticalResponse;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SkierVerticalResponse getAllVerticals(int skierID, String resort, String season) throws SQLException {
        String sql = "SELECT season_id, lift_id FROM Skiers WHERE skier_id = ? AND resort_id = ? AND season_id = ?";

        try (Connection jdbcConnection = DBCPDataSource.getConnection();
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
