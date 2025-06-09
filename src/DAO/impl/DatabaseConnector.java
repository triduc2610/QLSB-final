package DAO.impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnector {
    private static String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLySanBong;integratedSecurity=true;encrypt=false;trustServerCertificate=true;";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
    public static void createDatabase(String dbName) {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            String createDatabaseQuery = "CREATE DATABASE " + dbName;
            statement.executeUpdate(createDatabaseQuery);
            System.out.println(dbName + " created successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error creating the database", e);
        }
    }
    public static Connection connect(String name)
    {
        try {
            String url = String.format("jdbc:sqlserver://localhost:1433;databaseName=%s;integratedSecurity=true;encrypt=false;trustServerCertificate=true;", name);
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
    public static void dropDatabase(String dbName) {
        try (Connection connection = DriverManager.getConnection(URL);
             Statement statement = connection.createStatement()) {
            String dropDatabaseQuery = "DROP DATABASE " + dbName;
            statement.executeUpdate(dropDatabaseQuery);
            System.out.println(dbName + " dropped successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping the database", e);
        }
    }

    public static void dropColumn(String table,String column){
        String Columnarr[] = column.split(",");
        StringBuilder dropColumnBuilder = new StringBuilder();
        for (int i = 0; i < Columnarr.length; i++) {
            dropColumnBuilder.append("DROP COLUMN ").append(Columnarr[i]);
            if (i == Columnarr.length - 1) {
                dropColumnBuilder.append(" ");
            } else {
                dropColumnBuilder.append(" ,");
            }
        }
        String dropColumn = dropColumnBuilder.toString();
        
        try(Connection connection = DriverManager.getConnection(URL);
        Statement statement = connection.createStatement()){
            String dropColumnQuery = String.format("ALTER TABLE %s %s",table,dropColumn);
            statement.executeUpdate(dropColumnQuery);
            System.out.println(column + " dropped successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping the column", e);
        }
    }
}
