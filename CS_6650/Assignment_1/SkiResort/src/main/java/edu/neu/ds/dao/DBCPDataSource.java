package edu.neu.ds.dao;
import java.sql.*;

import edu.neu.ds.controller.StatisticsServlet;
import org.apache.commons.dbcp2.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBCPDataSource {
    private static final Logger LOGGER = LogManager.getLogger(DBCPDataSource.class.getName());
    private static BasicDataSource dataSource = new BasicDataSource();
    private static final String HOST_NAME = System.getenv("db_ip_address");
    private static final String PORT = System.getenv("db_port");
    private static final String DATABASE = System.getenv("db_name");
    private static final String USERNAME = System.getenv("db_username");
    private static final String PASSWORD = System.getenv("db_password");

//    private static final String HOST_NAME = "skiresort.chkolzjgncr8.us-west-2.rds.amazonaws.com";
//    private static final String PORT = "3306";
//    private static final String DATABASE = "skiresort";
//    private static final String USERNAME = "ds2019";
//    private static final String PASSWORD = "skier2019";

    static {
        String url = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC", HOST_NAME, PORT, DATABASE);
        dataSource.setUrl(url);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMinIdle(3);
        dataSource.setMaxIdle(5);
        dataSource.setMaxTotal(20);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}

