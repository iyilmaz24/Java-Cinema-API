package org.cinema.api.DAO;

import org.cinema.api.DB.DBClient;
import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.Exception.IncorrectPasswordException;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;
import org.sqlite.SQLiteDataSource;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


public class RoomSeatDAO {

    private static final String url = "jdbc::sqlite:src/main/resources/database";

    private static final String CREATE_STATS_DATABASE = """
            CREATE TABLE IF NOT EXISTS statistics (
            total_seats INTEGER, available_seats INTEGER,
            sold_seats INTEGER, total_revenue INTEGER, );""";

    private static final String PASSWORD = "super_secret"; // for use when trying to retrieve statistics
    private static final String GET_STATISTICS = "SELECT * FROM statistics";

    private static final String CREATE_CINEMA_DATABASE = """
            CREATE TABLE IF NOT EXISTS cinema (
            id INTEGER PRIMARY KEY,
            row INTEGER, column INTEGER,
            price INTEGER, purchased BOOLEAN,
            first_name VARCHAR(50), uuid VARCHAR(50) );""";

    private static final String SELECT_ALL = "SELECT * FROM cinema";
    private static final String SELECT_BY_ID = "SELECT * FROM cinema WHERE id = %d";
    private static final String SELECT_BY_ROW_COLUMN = "SELECT * FROM cinema WHERE row = %d AND column = %d";
    //    private static final String DELETE = "DELETE FROM cinema WHERE id = %d";
    private static final String INSERT = "INSERT INTO cinema VALUES(%d,%d,%d,%d,FALSE,NULL,NULL)";
                                                            // id, row, column, price
    private static final String SELL = "UPDATE cinema SET purchased = TRUE, first_name = %s, uuid = %s, " +
                                        "WHERE row = %d AND column = %d";
    private static final String RESET = "UPDATE cinema SET purchased = FALSE, name = NULL, uuid = NULL, " +
                                        "WHERE uuid = %s";

    private final DBClient dbClient;

    public RoomSeatDAO() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        dbClient = new DBClient(dataSource);
        dbClient.runUpdate(CREATE_CINEMA_DATABASE);
        dbClient.runUpdate(CREATE_STATS_DATABASE);
    }

    public void insertSeat(int id, int row, int column, int price) {
        dbClient.runUpdate(String.format(INSERT, id, row, column, price));
    }

    public List<Seat> getAllSeats() {
        return dbClient.selectMultiple(SELECT_ALL);
    }

    public Seat getSeatById(int id) {
        return dbClient.selectOne(String.format(SELECT_BY_ID, id));
    }

    public Seat getSeatByRowColumn (int row, int column) {
        return dbClient.selectOne(String.format(SELECT_BY_ROW_COLUMN, row, column));
    }

    public void sellSeatByRowColumn (String firstName, String uuid, int row, int column) {
        dbClient.runUpdate(String.format(SELL, firstName, uuid, row, column));
    }

    public void resetSeatByToken(String token) {
        dbClient.runUpdate(String.format(RESET, token));
    }

    public Room getRoomInfo() {
        String sqlStatement1 = GET_STATISTICS;
        String sqlStatement2 = SELECT_ALL;
        return dbClient.getRoomInfo(GET_STATISTICS, SELECT_ALL);
    }

    public StatsDTO getStatistics(String param_password) {
        if(!PASSWORD.equals(param_password)) {
            throw new IncorrectPasswordException("Provided admin credentials are not valid.");
        }
        return dbClient.getStatistics(GET_STATISTICS);
    }

}
