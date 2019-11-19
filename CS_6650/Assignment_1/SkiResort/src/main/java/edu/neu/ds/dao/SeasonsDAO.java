package edu.neu.ds.dao;

import edu.neu.ds.controller.ResortServlet;
import edu.neu.ds.model.Season;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class SeasonsDAO {
    private static final Logger LOGGER = LogManager.getLogger(ResortServlet.class.getName());
    private static DataSource pool;

    public SeasonsDAO(DataSource pool) {
        this.pool = pool;
    }

    public boolean insertSeason(int resortId, String seasonId) throws Exception {
        String sql = "INSERT INTO Seasons (resort_id, season_id) VALUES (?, ?)";

        try (Connection jdbcConnection = HikariCPDataSource.getConnection();//getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {

            statement.setInt(1, resortId);
            statement.setString(2, seasonId);

            return statement.executeUpdate() > 0;
        } catch(SQLIntegrityConstraintViolationException e) {
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
        try (Connection jdbcConnection = HikariCPDataSource.getConnection();//getConnection();
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
