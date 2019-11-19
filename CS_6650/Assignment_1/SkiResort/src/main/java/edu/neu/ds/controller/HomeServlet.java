package edu.neu.ds.controller;

import edu.neu.ds.dao.ConnectionPoolContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class HomeServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(HomeServlet.class.getName());
    private String message;

    public void init() throws ServletException {

        message = "Hello hey4";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sql = "INSERT INTO Skiers (resort_id, season_id, day_id, skier_id, time, lift_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionPoolContextListener.getConnection(); PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, 2);
            statement.setString(2, "2019");
            statement.setString(3, "2");
            statement.setInt(4, 100);
            statement.setInt(5, 12);
            statement.setInt(6, 500);

            statement.executeUpdate();

            // Initialize the database


            response.setContentType("text/html");


            PrintWriter out = response.getWriter();
            out.println("<h1>" + message + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
