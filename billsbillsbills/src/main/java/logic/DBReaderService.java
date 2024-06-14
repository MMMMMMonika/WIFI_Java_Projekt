package logic;

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
        int selectClientID = dataSingleton.getSelection();
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

    public List<T> readProductsFromDB(DBConnection dbConnection) {
        String viewProductQuery = "SELECT * FROM codecafe.product";

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(viewProductQuery)) {

            return resultToList.resultSetToList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public List<T> readBillsFromDB(DBConnection dbConnection) {
        String viewBillQuery = "SELECT * FROM codecafe.bill";

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(viewBillQuery)) {

            return resultToList.resultSetToList(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
