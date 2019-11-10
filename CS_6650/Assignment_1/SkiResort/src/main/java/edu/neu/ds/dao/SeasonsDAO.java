package edu.neu.ds.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import edu.neu.ds.controller.ResortServlet;
import edu.neu.ds.model.Season;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SeasonsDAO {
    private static final Logger LOGGER = LogManager.getLogger(ResortServlet.class.getName());

    public boolean insertSeason(int resortId, String seasonId) throws SQLException {
        String sql = "INSERT INTO Seasons (resort_id, season_id) VALUES (?, ?)";

        try (Connection jdbcConnection = DBCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {

            statement.setInt(1, resortId);
            statement.setString(2, seasonId);

            return statement.executeUpdate() > 0;
        } catch(MySQLIntegrityConstraintViolationException e) {
            LOGGER.info("duplicate exception found and ignored");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String> listAllSeasons(int resortId) throws SQLException {
        List<String> seasonList = new ArrayList();

        String sql = "SELECT * FROM Seasons WHERE resort_id = ?";
        try (Connection jdbcConnection = DBCPDataSource.getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, resortId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String seasonString = resultSet.getString("season_id");

                seasonList.add(seasonString);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seasonList;
    }

}
