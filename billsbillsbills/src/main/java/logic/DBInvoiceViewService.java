package logic;

import data.DBConnection;

import java.sql.*;
import java.util.List;

public class DBInvoiceViewService <T> {
    private final ResultToList<T> resultToList;
    public DBInvoiceViewService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }
    public List<T> readBillsFromDB(DBConnection dbConnection) throws SQLException {
        String viewBillQuery = "SELECT * FROM codecafe.bill";

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(viewBillQuery)) {

            return resultToList.resultSetToList(resultSet);
        }
    }
}
