package org.cinema.api.DB;

import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.Exception.NoResultsFoundException;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBClient {

    private final Logger logger;
    private final DataSource dataSource;

    public DBClient(DataSource dataSource) {
        this.dataSource = dataSource;
        this.logger = LoggerFactory.getLogger(DBClient.class);
    }

    public void runUpdate (String sqlString) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sqlString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Seat selectOne (String sqlString) {
        List<Seat> result = selectMultiple(sqlString);

        if(result.size() == 1) {
            return result.get(0);
        }
        else if (result.isEmpty()) {
            throw new NoResultsFoundException("No results found in search of database.");
        }
        else {
            throw new IllegalStateException("Query returned more than one object");
        }
    }

    public List<Seat> selectMultiple (String sqlString) {
        List<Seat> result = new ArrayList<>();

        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            ResultSet queryResults = statement.executeQuery(sqlString);

            while (queryResults.next()) {
                int row, column, price; Boolean purchased; String token, firstName;

                row = queryResults.getInt("row"); column = queryResults.getInt("column");
                price = queryResults.getInt("price"); purchased = queryResults.getBoolean("purchased");
                token = queryResults.getString("uuid"); firstName = queryResults.getString("first_name");

                Seat newSeat;
                if(!token.isBlank()) {
                    if(firstName.isBlank()) {
                        firstName = "";
                    }
                    newSeat = new Seat(row, column, price, purchased, firstName, token);
                }
                else if(!firstName.isBlank()) {
                    logger.warn("Seat was assigned a first name, but was not assigned a token!");
                    newSeat = new Seat(row, column, price, purchased, firstName, "");
                }
                else {
                    newSeat = new Seat(row, column, price, purchased);
                }
                result.add(newSeat);
            }
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public StatsDTO getStatistics(String sqlString) {
        StatsDTO result = null;
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            ResultSet queryResults = statement.executeQuery(sqlString);
            queryResults.next();

            int totalSeats, availableSeats, purchasedSeats, revenue;
            totalSeats = queryResults.getInt("total_seats"); availableSeats = queryResults.getInt("available_seats");
            purchasedSeats = queryResults.getInt("sold_seats"); revenue = queryResults.getInt("total_revenue");

            result = new StatsDTO(totalSeats, availableSeats, purchasedSeats, revenue);
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Room getRoomInfo(String sqlStringStats, String sqlStringSeats) {
        Room result = null;
        try(Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
        ) {
            StatsDTO statistics = getStatistics(sqlStringStats);
            List<Seat> seats = selectMultiple(sqlStringSeats);

            result = new Room(statistics, seats);
            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
