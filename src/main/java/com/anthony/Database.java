package com.anthony;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Database {

    private final HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    public Database() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:bank.db");
        dataSource = new HikariDataSource(config);
    }

    public void createTable() {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS accounts(accountID INTEGER PRIMARY KEY, accountHolder TEXT NOT NULL)");
        } catch (SQLException e){
            logger.error("Error creating table: ", e);
        }
    }

    public void addEntry(String columnName, String value) {
        String sql = "INSERT INTO accounts (accountID, accountHolder) VALUES(?, ?)";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, columnName);
            preparedStatement.setString(2, value);
            System.out.println("Attempting to add account.");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error adding account: ", e);
        }
    }



    public void deleteEntry(String columnName, String value) {
        String sql = "DELETE FROM accounts WHERE accountID = ? AND accountHolder = ?";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, columnName);
            preparedStatement.setString(2, value);
            System.out.println("Attempting to delete account: ");
            int rowsAffected = preparedStatement.executeUpdate();
            if(rowsAffected == 0){
                System.out.println("Successfully deleted account.");
            }
        } catch (SQLException e){
            logger.error("Error deleting account: ", e);
        }
    }

    public int getTableLength() {
        String sql = "SELECT COUNT(*) FROM accounts";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error("Error getting table length: ", e);
            return -1;
        }
    }

    public String getColumnName() {
        String sql = "SELECT accountID FROM accounts";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("accountID");
            }else {
                logger.warn("No account ID found in the accounts table.");
                return "No Data";
            }
        } catch (SQLException e) {
            logger.error("Error getting account: ", e);
            return "Error Thrown.";
        }
    }

    public String getValue() {
        String sql = "SELECT accountHolder FROM accounts";
        try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("accountHolder");
            } else {
                logger.warn("No account holder found in the accounts table.");
                return "No Data.";
            }
        } catch (SQLException e) {
            logger.error("Error getting account: ", e);
            return "Error Thrown.";
        }
    }

    public int getLatestID(){
        String sql = "SELECT * FROM accounts ORDER BY accountID DESC LIMIT 1";
        try(Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("accountID");
            } else {
                logger.warn("No records found in the accounts table.");
                return -1;
            }
        } catch (SQLException e) {
            logger.error("Error getting latest entry: ", e);
            return -1;
        }
    }

    public void deleteTable(){
        String sql = "DROP TABLE accounts";
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()){
            System.out.println("Deleting table....");
            statement.execute(sql);
        } catch (SQLException e){
            logger.error("Error deleting table: ", e);
        }
    }


}

