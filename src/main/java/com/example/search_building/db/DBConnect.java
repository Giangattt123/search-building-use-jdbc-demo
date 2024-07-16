package com.example.search_building.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
    static final String user = "root";
    static final String pass = "";
    private static Connection conn;
    public static Connection getCon() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL , user , pass);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
