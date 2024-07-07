package logic;

import data.DBConnection;

import java.sql.*;
import java.util.List;

public class DBViewService<T> {
    private final ResultToList<T> resultToList;

    public DBViewService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }

    public List<T> readAllFromDB(DBConnection dbConnection, String query) throws SQLException {

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            return resultToList.resultSetToList(resultSet);
        }
    }
}
