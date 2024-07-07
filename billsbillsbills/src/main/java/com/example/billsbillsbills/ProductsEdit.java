package com.example.billsbillsbills;

import data.DBConnection;
import data.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.DBSelectionViewService;
import logic.RSProductService;
import logic.ResultToList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductsEdit {
    private Stage stage;
    @FXML
    public Button cancelButton;
    @FXML
    public Button updateButton;
    @FXML
    private Label labelProductNumber;
    @FXML
    private Label labelProductName;
    @FXML
    private Label labelProductDesc;
    @FXML
    private Label labelPrice;
    @FXML
    private TextField editProductNumber;
    @FXML
    private TextField editProductName;
    @FXML
    private TextField editProductDesc;
    @FXML
    private TextField editPrice;

    ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");

    @FXML
    public void initialize() throws SQLException {
        String query = "SELECT * FROM codecafe.product WHERE productid = ?";

        ResultToList<Product> productResultToList = new RSProductService();
        DBSelectionViewService<Product> productDBReaderService = new DBSelectionViewService<>(productResultToList);
        List<Product> productList = productDBReaderService.readSelectionFromDB(dbConnection, query);
        productObservableList.addAll(productList);

        editProductNumber.setText(String.valueOf(productObservableList.get(0).getProductNumber()));
        editProductName.setText(productObservableList.get(0).getProductName());
        editProductDesc.setText(productObservableList.get(0).getProductDescription());
        editPrice.setText(String.valueOf(productObservableList.get(0).getProductPrice()));
    }
    @FXML
    public void onUpdateButtonClick() throws SQLException, IOException {
        Pattern productNumberPattern = Pattern.compile("^\\d+$");
        Matcher productNumberMatcher = productNumberPattern.matcher(editProductNumber.getText());
        boolean correctProductNumber = productNumberMatcher.find();

        Pattern productPricePattern = Pattern.compile("^[1-9]\\d*(\\.\\d+)?$");
        Matcher productPriceMatcher = productPricePattern.matcher(editPrice.getText());
        boolean correctProductPrice = productPriceMatcher.find();

        if(!correctProductNumber) {
            editProductNumber.setText("Please enter a valid number!");
        } else if (!correctProductPrice) {
            editPrice.setText("Please enter a valid price!");
        } else {
            String updateProductQuery = "UPDATE product SET productName = ?, productDescription = ?, productPrice = ? WHERE productNumber = ?";

            try (Connection connection = DriverManager.getConnection(
                    dbConnection.dbUrl(),
                    dbConnection.dbUser(),
                    dbConnection.dbPw());

                 PreparedStatement preparedStatement = connection.prepareStatement(updateProductQuery)) {
                preparedStatement.setInt(4, Integer.parseInt(editProductNumber.getText()));
                preparedStatement.setString(1, editProductName.getText());
                preparedStatement.setString(2, editProductDesc.getText());
                preparedStatement.setDouble(3, Double.parseDouble(editPrice.getText()));
                preparedStatement.executeUpdate();
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("products-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ProductsTable productsTable = fxmlLoader.getController();
            productsTable.setStage(stage);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void onCancelButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("products-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ProductsTable productsTable = fxmlLoader.getController();
        productsTable.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
