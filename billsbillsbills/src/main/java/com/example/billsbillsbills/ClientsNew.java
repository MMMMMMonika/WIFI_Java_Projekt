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

public class ClientsNew {
    @FXML
    public Button cancelButton;
    @FXML
    public Button saveButton;
    @FXML
    private Label labelClientNumber;
    @FXML
    private Label labelCompanyName;
    @FXML
    private Label labelContactName;
    @FXML
    private Label labelAddress;
    @FXML
    private Label labelPostalCode;
    @FXML
    private Label labelCity;
    @FXML
    private Label labelRegion;
    @FXML
    private Label labelCountry;
    @FXML
    private Label labelPhoneNumber;
    @FXML
    private Label labelEMail;
    @FXML
    private Label labelVatNumber;
    @FXML
    private TextField enterClientNumber;
    @FXML
    private TextField enterCompanyName;
    @FXML
    private TextField enterContactName;
    @FXML
    private TextField enterAddress;
    @FXML
    private TextField enterPostalCode;
    @FXML
    private TextField enterCity;
    @FXML
    private TextField enterRegion;
    @FXML
    private TextField enterCountry;
    @FXML
    private TextField enterPhoneNumber;
    @FXML
    private TextField enterEMail;
    @FXML
    private TextField enterVatNumber;
    private Stage stage;

    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");

    @FXML
    public void onCancelButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("01clients-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientsTable clientsTable = fxmlLoader.getController();
        clientsTable.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onSaveButtonClick() throws SQLException, IOException {
        String newClientQuery = "INSERT INTO client" +
                " (clientnumber, companyname, contactname, address, postalcode, city," +
                " region, country, phonenumber, email, vatnumber)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(newClientQuery)) {
            preparedStatement.setInt(1, Integer.parseInt(enterClientNumber.getText()));
            preparedStatement.setString(2, enterCompanyName.getText());
            preparedStatement.setString(3, enterContactName.getText());
            preparedStatement.setString(4, enterAddress.getText());
            preparedStatement.setInt(5, Integer.parseInt(enterPostalCode.getText()));
            preparedStatement.setString(6, enterCity.getText());
            preparedStatement.setString(7, enterRegion.getText());
            preparedStatement.setString(8, enterCountry.getText());
            preparedStatement.setString(9, enterPhoneNumber.getText());
            preparedStatement.setString(10, enterEMail.getText());
            preparedStatement.setString(11, enterVatNumber.getText());
            preparedStatement.executeUpdate();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("01clients-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientsTable clientsTable = fxmlLoader.getController();
        clientsTable.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
