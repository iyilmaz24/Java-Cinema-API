package org.cinema.api.DAO;

import org.cinema.api.DB.DBClient;
import org.cinema.api.DTO.StatsDTO;
import org.cinema.api.Exception.IncorrectPasswordException;
import org.cinema.api.Model.Room;
import org.cinema.api.Model.Seat;
import org.cinema.api.DB.DB_Creator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.sqlite.SQLiteDataSource;

import java.util.List;

@Repository
public class RoomSeatDAO {

    private static final String url = "jdbc:sqlite:./src/main/resources/database/cinema_api.db";

    private static final String GET_STATISTICS = "SELECT * FROM statistics";
    private static final String INCREMENT_SALES = """
            UPDATE statistics SET sold_seats = sold_seats + 1, available_seats = available_seats - 1;
             total_revenue = total_revenue + %d, total_seats = (SELECT COUNT(*) AS total FROM cinema);""";
    private static final String DECREMENT_SALES = """
            UPDATE statistics SET sold_seats = sold_seats - 1, available_seats = available_seats + 1;
             total_revenue = total_revenue - %d, total_seats = (SELECT COUNT(*) AS total FROM cinema);""";

    private static final String SELECT_ALL = "SELECT * FROM cinema";
    private static final String SELECT_BY_TOKEN = "SELECT * FROM cinema WHERE uuid = '%s'";
    private static final String SELECT_BY_ROW_COLUMN = "SELECT * FROM cinema WHERE row_number = %d AND column_number = %d";
    private static final String SELL = """
                                        UPDATE cinema SET purchased = TRUE, first_name = '%s', uuid = '%s'
                                        WHERE row_number = %d AND column_number = %d""";
    private static final String RESET = """
                                        UPDATE cinema SET purchased = FALSE, first_name = NULL, uuid = NULL
                                        WHERE uuid = '%s'""";

    private final DBClient dbClient;
    private final DB_Creator dbCreator;

    @Autowired
    public RoomSeatDAO(PlatformTransactionManager transactionManager) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        dbClient = new DBClient(dataSource, transactionManager);
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
        dbClient.runUpdate(String.format(SELL, firstName, uuid, row, column), INCREMENT_SALES);
    }

    public void refundSeatByToken(String token) {
        dbClient.runUpdate(String.format(RESET, token), DECREMENT_SALES);
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
