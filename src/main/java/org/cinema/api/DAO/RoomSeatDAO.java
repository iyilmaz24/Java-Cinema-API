package org.cinema.api.DAO;

import org.cinema.api.DB.DBClient;
import org.sqlite.SQLiteDataSource;


public class RoomSeatDAO {

    private static final String CREATE_DATABASE = """
            CREATE TABLE IF NOT EXISTS cinema (
            id INTEGER PRIMARY KEY,
            row INTEGER, column INTEGER,
            price INTEGER, purchased BOOLEAN,
            first_name VARCHAR(50), uuid VARCHAR(50) );""";

    private static final String SELECT_ALL = "SELECT * FROM cinema";
    private static final String SELECT_BY_ID = "SELECT * FROM cinema WHERE id = %d";
    //    private static final String DELETE = "DELETE FROM cinema WHERE id = %d";

    private static final String INSERT = "INSERT INTO cinema VALUES(%d,%d,%d,%d,FALSE,NULL,NULL)";
                                                            // id, row, column, price
    private static final String SELL = "UPDATE cinema SET purchased = TRUE, first_name = %s, uuid = %s, " +
                                        "WHERE row = %d AND column = %d";
    private static final String RESET = "UPDATE cinema SET purchased = FALSE, name = NULL, uuid = NULL, " +
                                        "WHERE uuid = %s";

    private static final String url = "jdbc::sqlite:src/main/resources/database";
    private final DBClient dbClient;

    public RoomSeatDAO() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        dbClient = new DBClient(dataSource);
        dbClient.runUpdate(CREATE_DATABASE);
    }

    public void insertSeat(int id, int row, int column, int price) {
        String sqlStatement = String.format(INSERT, id, row, column, price);
        dbClient.runUpdate(sqlStatement);
    }

    public void getAllSeats() {
        String sqlStatement = SELECT_ALL;
        dbClient.selectMultiple(sqlStatement);
    }

    public void getSeatById(int id) {
        String sqlStatement = String.format(SELECT_BY_ID, id);
        dbClient.selectOne(sqlStatement);
    }

    public void sellSeatByRowColumn (String firstName, String uuid, int row, int column) {
        String sqlStatement = String.format(SELL, firstName, uuid, row, column );
        dbClient.runUpdate(sqlStatement);
    }

    public void resetSeatByToken(String token) {
        String sqlStatement = String.format(RESET, token);
        dbClient.runUpdate(sqlStatement);
    }

}
