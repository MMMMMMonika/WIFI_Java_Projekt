package logic;

import data.DBConnection;
import data.DataSingleton;

import java.sql.*;
import java.util.List;

public class DBClientViewService<T> {
    private final ResultToList<T> resultToList;

    public DBClientViewService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }
    DataSingleton dataSingleton = DataSingleton.getInstance();

    public List<T> readClientsFromDB(DBConnection dbConnection) throws SQLException {
        String viewClientQuery = "SELECT * FROM codecafe.client";

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(viewClientQuery)) {

            return resultToList.resultSetToList(resultSet);
        }
    }
}
