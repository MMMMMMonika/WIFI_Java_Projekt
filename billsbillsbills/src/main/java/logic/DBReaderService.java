package logic;

import data.DBConnection;

import java.sql.*;
import java.util.List;

public class DBReaderService<T> {
    private final ResultToList<T> resultToList;

    public DBReaderService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }

    public List<T> readFromDB(DBConnection dbConnection) {
        String viewClientQuery = "SELECT * FROM xxx.client";

        try(Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(viewClientQuery)) {

            return resultToList.resultSetToList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
