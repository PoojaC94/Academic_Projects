package edu.neu.ds.dao;

import edu.neu.ds.controller.ResortServlet;
import edu.neu.ds.model.Resort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class ResortsDAO {
    private static final Logger LOGGER = LogManager.getLogger(ResortServlet.class.getName());
    private static DataSource pool;

//    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public ResortsDAO(DataSource pool) {
        this.pool = pool;
    }

    public boolean insertResort(String resortName, DataSource pool) throws Exception {
        String sql = "INSERT INTO Resorts (resortName) VALUES (?)";

        try (Connection jdbcConnection = HikariCPDataSource.getConnection();//getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setString(1, resortName);

            return statement.executeUpdate() > 0;
        } catch(SQLIntegrityConstraintViolationException e) {
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Resort> listAllResorts(DataSource pool) throws Exception {
        List<Resort> resortList = new ArrayList();

        String sql = "SELECT * FROM Resorts";

        try (Connection jdbcConnection = HikariCPDataSource.getConnection();//getConnection();
             Statement statement = jdbcConnection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Resort resort = new Resort(name, id);
                resortList.add(resort);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resortList;
    }

    public Resort getResortById(int resortId) throws Exception {
        String sql = "SELECT * FROM Resorts WHERE id = ?";

        try (Connection jdbcConnection = HikariCPDataSource.getConnection();//getConnection();
             PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {

            statement.setInt(1, resortId);

            ResultSet resultSet = statement.executeQuery();
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Resort resort = new Resort(name, id);

            return resort;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
