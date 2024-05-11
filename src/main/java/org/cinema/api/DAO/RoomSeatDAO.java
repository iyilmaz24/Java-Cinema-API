package org.cinema.api.DAO;

import org.cinema.api.DB.DBClient;
import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.Exception.IncorrectPasswordException;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;
import org.cinema.api.Util.DB_Creator;
import org.sqlite.SQLiteDataSource;

import java.util.List;


public class RoomSeatDAO {

    private static final String url = "jdbc::sqlite:src/main/resources/database";

    private static final String GET_STATISTICS = "SELECT * FROM statistics";
//    private static final String GET_TOTAL_SEATS = "SELECT COUNT(*) AS total FROM cinema";
    private static final String INCREMENT_SALES = """
            UPDATE statistics SET sold_seats = sold_seats + 1, available_seats = available_seats - 1;
             total_revenue = total_revenue + %d, total_seats = (SELECT COUNT(*) AS total FROM cinema);""";
    private static final String DECREMENT_SALES = """
            UPDATE statistics SET sold_seats = sold_seats - 1, available_seats = available_seats + 1;
             total_revenue = total_revenue - %d, total_seats = (SELECT COUNT(*) AS total FROM cinema);""";

    private static final String SELECT_ALL = "SELECT * FROM cinema";
    private static final String SELECT_BY_TOKEN = "SELECT * FROM cinema WHERE uuid = %s";
    private static final String SELECT_BY_ROW_COLUMN = "SELECT * FROM cinema WHERE row = %d AND column = %d";
    private static final String SELL = "UPDATE cinema SET purchased = TRUE, first_name = %s, uuid = %s, " +
                                        "WHERE row = %d AND column = %d";
    private static final String RESET = "UPDATE cinema SET purchased = FALSE, name = NULL, uuid = NULL, " +
                                        "WHERE uuid = %s";

    private final DBClient dbClient;
    private final DB_Creator dbCreator;

    public RoomSeatDAO() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        dbClient = new DBClient(dataSource);
        dbCreator = new DB_Creator(5, 5); // rows and columns, default is 10 x 10
        dbCreator.initializeDB(dbClient);
        dbCreator.setPassword("secret"); // password, default is 'secret_password'
    }

    public List<Seat> getAllSeats() {
        return dbClient.selectMultiple(SELECT_ALL);
    }

    public Seat getSeatByToken(String token) {
        return dbClient.selectOne(String.format(SELECT_BY_TOKEN, token));
    }

    public Seat getSeatByRowColumn (int row, int column) {
        return dbClient.selectOne(String.format(SELECT_BY_ROW_COLUMN, row, column));
    }

    public void sellSeatByRowColumn (String firstName, String uuid, int row, int column) {
        dbClient.runUpdate(String.format(SELL, firstName, uuid, row, column));
    }

    public void refundSeatByToken(String token) {
        dbClient.runUpdate(String.format(RESET, token));
    }

    public Room getRoomInfo() {
        return dbClient.getRoomInfo(GET_STATISTICS, SELECT_ALL);
    }

    public StatsDTO getStatistics(String param_password) {
        if(!dbCreator.isValidPassword(param_password)) {
            throw new IncorrectPasswordException("Provided admin credentials are not valid.");
        }
        return dbClient.getStatistics(GET_STATISTICS);
    }

}
