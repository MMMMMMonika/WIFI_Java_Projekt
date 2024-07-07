package logic;

import data.DBConnection;

import java.sql.*;
import java.time.LocalDate;

public class DBInvoiceDateReaderService<T> {
    private final ResultToList<T> resultToList;

    public DBInvoiceDateReaderService(ResultToList<T> resultToList) {
        this.resultToList = resultToList;
    }

    public int readAmountOfInvoiceDatesFromDB(DBConnection dbConnection) throws SQLException {
        String viewInvoiceDateQuery = "SELECT * FROM codecafe.bill WHERE billdate BETWEEN ? AND ?";
        Date dateStart = Date.valueOf(LocalDate.now());
        Date dateEnd = Date.valueOf(LocalDate.now());

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(viewInvoiceDateQuery)) {
            preparedStatement.setDate(1, dateStart);
            preparedStatement.setDate(2, dateEnd);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultToList.resultSetToList(resultSet).size();
        }
    }
}
