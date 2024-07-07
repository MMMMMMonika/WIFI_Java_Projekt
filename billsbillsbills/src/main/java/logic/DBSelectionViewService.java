package logic;

import data.DBConnection;
import data.DataSingleton;

import java.sql.*;
import java.util.List;

public class DBSelectionViewService<T> {
    private final ResultToList<T> resultToList;
    DataSingleton dataSingleton = DataSingleton.getInstance();
    public DBSelectionViewService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }

    public List<T> readSelectionFromDB(DBConnection dbConnection, String query) throws SQLException {
        int selectedID = dataSingleton.getSelection();

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, selectedID);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultToList.resultSetToList(resultSet);
        }
    }
}
