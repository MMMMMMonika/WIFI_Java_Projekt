package com.example.billsbillsbills;

import data.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductsNew {
    @FXML
    public Button cancelButton;
    @FXML
    public Button saveButton;
    @FXML
    private Label labelProductNumber;
    @FXML
    private Label labelProductName;
    @FXML
    private Label labelProductDescription;
    @FXML
    private Label labelProductPrice;
    @FXML
    private TextField enterProductNumber;
    @FXML
    private TextField enterProductName;
    @FXML
    private TextField enterProductDescription;
    @FXML
    private TextField enterProductPrice;
    private Stage stage;

    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");

    @FXML
    public void onCancelButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("02products-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ProductsTable productsTable = fxmlLoader.getController();
        productsTable.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onSaveButtonClick() throws SQLException, IOException {
        String newProductQuery = "INSERT INTO product (productnumber, productname, productdescription, productprice) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(newProductQuery)) {
            preparedStatement.setInt(1, Integer.parseInt(enterProductNumber.getText()));
            preparedStatement.setString(2, enterProductName.getText());
            preparedStatement.setString(3, enterProductDescription.getText());
            preparedStatement.setDouble(4, Double.parseDouble(enterProductPrice.getText()));
            preparedStatement.executeUpdate();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("02products-view.fxml"));
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
