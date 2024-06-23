package logic;

import data.DBConnection;
import data.DataSingleton;

import java.sql.*;
import java.util.List;

public class DBProductViewService<T> {
    private final ResultToList<T> resultToList;
    public DBProductViewService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }

    public List<T> readProductsFromDB(DBConnection dbConnection) throws SQLException {
        String viewProductQuery = "SELECT * FROM codecafe.product";

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(viewProductQuery)) {

            return resultToList.resultSetToList(resultSet);
        }
    }
}
