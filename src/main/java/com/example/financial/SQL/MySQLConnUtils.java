package com.example.financial.SQL;

import java.sql.*;

public class MySQLConnUtils {
    public static Connection getMySQlConnection() throws SQLException {
        final String hostName = "localhost";
        final String dbName = "financial";
        final String userName = "root";
        final String password = "haydenshimada1804"; //enter your password
        return getMySQlConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQlConnection(String hostName, String dbName, String userName, String password) throws SQLException {
        final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&verifyServerCertificate=false&useSSL=true";
        return DriverManager.getConnection(connectionURL, userName, password);
    }

}
