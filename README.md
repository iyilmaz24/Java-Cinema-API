# Cinema Rest API
This project implements a RESTful API for managing cinema seat purchases and refunds.  The API allows users to purchase tickets, get refunds, view available seats, and retrieve statistics about sales.  The system uses a SQLite database to store seat information and sales statistics.



## Features
* **Seat Purchase:** Allows users to purchase seats by specifying row and column numbers.  Assigns a unique token to each purchased ticket.
* **Seat Refund:** Allows users to refund purchased seats using the unique token.
* **Seat Availability:** Provides an endpoint to retrieve information about the available seats in the cinema.
* **Sales Statistics:** Provides an endpoint to retrieve sales statistics (total seats, available seats, sold seats, and total revenue). This endpoint is protected by a password.
* **Error Handling:** Implements custom exception handling for various scenarios like already purchased seats, invalid inputs, and database errors.  Returns informative error messages in JSON format.

## Usage
The API exposes several endpoints:

* `/purchase`: POST request to purchase a seat.  Requires a JSON payload containing `row`, `column`, and `customerFirstName`.
  * **Request Example:**
    ```json
    {
      "row": 2,
      "column": 3,
      "customerFirstName": "John"
    }
    ```
  * **Response Example (Success):**
    ```json
    {
      "token": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
      "ticket": {
        "row": 2,
        "column": 3,
        "price": 18,
        "purchased": true
      }
    }
    ```
* `/return`: POST request to refund a seat. Requires a JSON payload containing `token`.
  * **Request Example:**
    ```json
    {
      "token": "a1b2c3d4-e5f6-7890-1234-567890abcdef"
    }
    ```
  * **Response Example (Success):**
    ```json
    {
      "ticket": {
        "row": 2,
        "column": 3,
        "price": 18
      }
    }
    ```
* `/seats`: GET request to retrieve information about all seats.
  * **Response Example (Success):**
    ```json
    {
      "totalSeats": 50,
      "availableSeats": 40,
      "seats": [
        // ... array of Seat objects ...
      ]
    }
    ```
* `/stats`: POST request to retrieve sales statistics. Requires a JSON payload containing the `password`.
  * **Request Example:**
    ```json
    {
      "password": "secret"
    }
    ```
  * **Response Example (Success):**
    ```json
    {
      "totalSeats": 50,
      "availableSeats": 40,
      "purchasedSeats": 10,
      "totalRevenue": 150
    }
    ```

## Technologies Used (Tech Stack)
* **Java:** Programming language
* **Spring Boot:** Framework for building Spring-based applications
* **SQLite:** Database
* **Maven:** Build tool
* **Jackson:** JSON processing library
* **slf4j:** Logging facade

## Configuration
The database is configured using a SQLite database file located at `src/main/resources/database/cinema_api.db`.  The default database schema is created upon application startup. The default admin password for `/stats` endpoint is "secret".  You can change this by modifying the `DB_Creator` class. The default room size is 5x5 rows and columns; you can change this as well in `DB_Creator` class.

*Made with [Etchr](https://readme-generator-peach.vercel.app/)*
