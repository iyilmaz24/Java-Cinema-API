package org.cinema.api.Util;

import org.cinema.api.DB.DBClient;

public class DB_Creator {

    private int ROWS = 10;
    private int COLUMNS = 10;
    private String PASSWORD = "secret_password";

    private static final String CLEAR_STATS_DATABASE = "DROP TABLE IF EXISTS statistics";
    private static final String CREATE_STATS_DATABASE = """
            CREATE TABLE IF NOT EXISTS statistics (
            total_seats INTEGER, available_seats INTEGER,
            sold_seats INTEGER, total_revenue INTEGER);""";
    private static final String SET_TOTAL_SEATS = """
            INSERT INTO statistics (total_seats, available_seats,
            sold_seats, total_revenue) VALUES (%d, %d, %d, %d);""";

    private static final String CLEAR_CINEMA_DATABASE = "DROP TABLE IF EXISTS cinema";
    private static final String CREATE_CINEMA_DATABASE = """
            CREATE TABLE IF NOT EXISTS cinema (
            id VARCHAR(10) PRIMARY KEY,
            row_number INTEGER, column_number INTEGER,
            price INTEGER, purchased BOOLEAN,
            first_name VARCHAR(50), uuid VARCHAR(50) );""";

    private static final String INSERT = """
            INSERT INTO cinema (id, row_number, column_number, price, purchased, first_name, uuid)
            VALUES (%s, %d, %d, %d, FALSE, NULL, NULL)""";
                                                                // id, row, column, price
    public DB_Creator() {;}

    public DB_Creator(int rows, int columns) {
        this.ROWS = rows; this.COLUMNS = columns;
    }

    public void setPassword(String password) {
        this.PASSWORD = password;
    }
    public Boolean isValidPassword(String password) {
        return this.PASSWORD.equals(password);
    }

    public void initializeDB(DBClient dbClient) {
        dbClient.runUpdate(CLEAR_CINEMA_DATABASE);
        dbClient.runUpdate(CREATE_CINEMA_DATABASE);
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                String newId = "'" + row + "R" + col + "C" + "'"; // ex: 2nd row and 3rd col = 2R3C
                dbClient.runUpdate(String.format(INSERT, newId, row, col, seatPriceGenerator(row)));
            }
        }
        dbClient.runUpdate(CLEAR_STATS_DATABASE);
        dbClient.runUpdate(CREATE_STATS_DATABASE);
        dbClient.runUpdate(String.format(SET_TOTAL_SEATS, ROWS*COLUMNS, 0, 0, 0));
    }

    private int seatPriceGenerator(int row) {
        return Math.max(20 - row, 10);
    }

}
