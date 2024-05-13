This "feature_branch" was used to refactor the API to use SQLite database for persistence instead of the previous in-memory data structures.

The cinema database stores all of the available seats, while the statistics database stores business data
The RoomSeatDAO was added to interact with the DBClient for more loose coupled SQLite integration
Logging and error handling was also added in this branch for the SQLite operations performed in the DBClient
Various bugs/errors were found and fixed, and existing controllers + services were slightly altered to implement the new DAO
