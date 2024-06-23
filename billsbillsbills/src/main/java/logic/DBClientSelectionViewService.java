package logic;

import data.DBConnection;
import data.DataSingleton;

import java.sql.*;
import java.util.List;

public class DBClientSelectionViewService<T> {
    private final ResultToList<T> resultToList;
    DataSingleton dataSingleton = DataSingleton.getInstance();

    public DBClientSelectionViewService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }

    public List<T> readClientSelectionFromDB(DBConnection dbConnection) throws SQLException {
        String viewClientQuery = "SELECT * FROM codecafe.client WHERE clientid = ?";
        int selectClientID = dataSingleton.getSelection();

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(viewClientQuery)) {
            preparedStatement.setInt(1, selectClientID);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultToList.resultSetToList(resultSet);
        }
    }
}
