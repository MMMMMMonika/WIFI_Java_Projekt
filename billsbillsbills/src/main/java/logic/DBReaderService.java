package logic;

import com.example.billsbillsbills.ClientsTable;
import data.ClientSelection;
import data.DBConnection;
import data.DataSingleton;

import java.sql.*;
import java.util.List;

public class DBReaderService<T> {
    private final ResultToList<T> resultToList;

    public DBReaderService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }
    DataSingleton dataSingleton = DataSingleton.getInstance();

    public List<T> readFromDB(DBConnection dbConnection) {
        String viewClientQuery = "SELECT * FROM codecafe.client";

        try (Connection connection = DriverManager.getConnection(
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

    public List<T> readSelectionFromDB(DBConnection dbConnection) throws SQLException {
        String viewClientQuery = "SELECT * FROM codecafe.client WHERE clientid = ?";
        int selectClientID = dataSingleton.getClientSelection();
        System.out.println("Client ID for Statement = " + selectClientID);

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(viewClientQuery)) {
             preparedStatement.setInt(1, selectClientID);

             ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("MySQL Query = " + preparedStatement);

             return resultToList.resultSetToList(resultSet);
        }
    }
}
