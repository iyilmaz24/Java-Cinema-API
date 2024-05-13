package org.cinema.api.DB;

import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.Exception.DatabaseException;
import org.cinema.api.Exception.NoResultsFoundException;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


public class DBClient {

    private final Logger logger;
    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;

    public DBClient(DataSource dataSource, PlatformTransactionManager transactionManager) {
        this.dataSource = dataSource;
        this.logger = LoggerFactory.getLogger(DBClient.class);
        this.transactionManager = transactionManager;
    }

    public void runUpdate (String sqlString) { // without transaction manager method signature
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sqlString);
        }
        catch (Exception e) {
            logger.error("Error in DBClient runUpdate(< {} >): {}", sqlString, e.getMessage());
            throw new RuntimeException("Request unsuccessful, error encountered: " + e.getMessage());
        }
    }

    public void runUpdate (String sqlString1, String sqlString2) { // with transaction manager method signature
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sqlString1);
            statement.executeUpdate(sqlString2);
            transactionManager.commit(transactionStatus); // close transaction, save changes
        }
        catch (Exception e) {
            logger.error("Error in DB transaction: < {} > < {} > {}", sqlString1, sqlString2, e.getMessage());
            transactionManager.rollback(transactionStatus); // rollback transaction, discard changes
            throw new RuntimeException("Request unsuccessful, error encountered: " + e.getMessage());
        }
    }

    public Seat selectOne (String sqlString) {
        List<Seat> result = selectMultiple(sqlString); // call other DBClient method to gather data
        if(result.size() == 1) return result.get(0); // desired outcome, handle exceptions below

        else if (result.isEmpty()) throw new NoResultsFoundException("No results found in search of database.");
        else throw new IllegalStateException("Query returned more than one object");
    }

    public List<Seat> selectMultiple (String sqlString) {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            ResultSet queryResults = statement.executeQuery(sqlString);

            List<Seat> result = new ArrayList<>(); Seat newSeat; // declare required placeholders
            int row, column, price; boolean purchased; String token, firstName;

            while (queryResults.next()) { // iterate full ResultSet and create Seat Models from data
                row = queryResults.getInt("row_number"); column = queryResults.getInt("column_number");
                price = queryResults.getInt("price"); purchased = queryResults.getBoolean("purchased");
                token = queryResults.getString("uuid"); firstName = queryResults.getString("first_name");

                if(token != null && !token.isBlank()) {
                    if(firstName == null || firstName.isBlank()) firstName = ""; // first name = not required field
                    newSeat = new Seat(row, column, price, purchased, firstName, token);
                }
                else if(firstName != null && !firstName.isBlank()) {
                    logger.warn("Seat was assigned a first name, but was not assigned a token!"); // token = required field
                    newSeat = new Seat(row, column, price, purchased, firstName, "");
                }
                else newSeat = new Seat(row, column, price, purchased); // if Seat Model has not been sold yet
                result.add(newSeat);
            }
            return result; // return list of created Seat Models after iterating ResultSet
        }
        catch (Exception e) {
            logger.error("Error in DBClient selectMultiple: {}", e.getMessage());
            throw new RuntimeException("Request unsuccessful, error encountered: " + e.getMessage());
        }
    }

    public StatsDTO getStatistics(String sqlString) {
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            ResultSet queryResults = statement.executeQuery(sqlString); // statistics table consists of a single row
            if(queryResults.next()) return new StatsDTO(queryResults.getInt("total_seats"), queryResults.getInt("available_seats"),
                        queryResults.getInt("sold_seats"), queryResults.getInt("total_revenue"));

            logger.warn("Empty result set returned when getting statistics: {}", sqlString);
            throw new NoResultsFoundException("No results found in search of database.");
        }
        catch (Exception e) {
            logger.error("Error encountered getting statistics: {}", e.getMessage());
            throw new RuntimeException("Error encountered getting statistics: " + e.getMessage());
        }
    }

    public Room getRoomInfo(String sqlStringStats, String sqlStringSeats) {
        try {
            StatsDTO statistics = getStatistics(sqlStringStats); // call other DBClient methods to gather data
            List<Seat> seats = selectMultiple(sqlStringSeats);

            if(statistics == null || seats == null) throw new DatabaseException("Error getting room information");
            return new Room(statistics, seats); // desired outcome, construct and return new Room Model
        }
        catch (Exception e) {
            logger.error("Error encountered getting room information: {}", e.getMessage());
            throw new RuntimeException("Error encountered getting room information: " + e.getMessage());
        }
    }

}
