package logic;

import data.DBConnection;
import data.DataSingleton;

import java.sql.*;
import java.util.List;

public class DBProductSelectionViewService<T> {
    private final ResultToList<T> resultToList;
    DataSingleton dataSingleton = DataSingleton.getInstance();
    public DBProductSelectionViewService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }
    public List<T> readProductSelectionFromDB(DBConnection dbConnection) throws SQLException {
        String viewProductQuery = "SELECT * FROM codecafe.product WHERE productid = ?";
        int selectProductID = dataSingleton.getSelection();

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(viewProductQuery)) {
            preparedStatement.setInt(1, selectProductID);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultToList.resultSetToList(resultSet);
        }
    }
}
