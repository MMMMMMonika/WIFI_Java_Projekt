package com.example.billsbillsbills;

import data.Client;
import data.ClientSelection;
import data.DBConnection;
import data.DataSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.ClientViewService;
import logic.DBReaderService;
import logic.ResultToList;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class ClientsEdit {
    @FXML
    public Button cancelButton;
    @FXML
    public Button updateButton;
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
    private TextField editClientNumber;
    @FXML
    private TextField editCompanyName;
    @FXML
    private TextField editContactName;
    @FXML
    private TextField editAddress;
    @FXML
    private TextField editPostalCode;
    @FXML
    private TextField editCity;
    @FXML
    private TextField editRegion;
    @FXML
    private TextField editCountry;
    @FXML
    private TextField editPhoneNumber;
    @FXML
    private TextField editEMail;
    @FXML
    private TextField editVatNumber;
    private Stage stage;
    ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");
    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    public void initialize() throws SQLException {
        ResultToList<Client> clientResultToList = new ClientViewService();
        DBReaderService<Client> clientDBReaderService = new DBReaderService<>(clientResultToList);
        List<Client> clientList = clientDBReaderService.readSelectionFromDB(dbConnection);
        clientObservableList.addAll(clientList);

        editClientNumber.setText(String.valueOf(clientObservableList.get(0).getClientNumber()));
        editCompanyName.setText(clientObservableList.get(0).getCompanyName());
        editContactName.setText(clientObservableList.get(0).getContactName());
        editAddress.setText(clientObservableList.get(0).getAddress());
        editPostalCode.setText(String.valueOf(clientObservableList.get(0).getPostalCode()));
        editCity.setText(clientObservableList.get(0).getCity());
        editRegion.setText(clientObservableList.get(0).getRegion());
        editCountry.setText(clientObservableList.get(0).getCountry());
        editPhoneNumber.setText(clientObservableList.get(0).getPhoneNumber());
        editEMail.setText(clientObservableList.get(0).getEMail());
        editVatNumber.setText(clientObservableList.get(0).getVatNumber());

        System.out.println("ClientsEdit Singleton = " + dataSingleton.getClientSelection());
        System.out.println(clientObservableList);
    }
    @FXML
    public void onUpdateButtonClick() throws IOException, SQLException {
        String updateClientQuery = "UPDATE client SET companyName = ?, contactName = ?, address = ?, postalCode = ?"
                + ", city = ?, region = ?, country = ?, phoneNumber = ?, eMail = ?, vatNumber = ?"
                + " WHERE clientNumber = ?";

        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(updateClientQuery)) {
            preparedStatement.setInt(11, Integer.parseInt(editClientNumber.getText()));
            preparedStatement.setString(1, editCompanyName.getText());
            preparedStatement.setString(2, editContactName.getText());
            preparedStatement.setString(3, editAddress.getText());
            preparedStatement.setInt(4, Integer.parseInt(editPostalCode.getText()));
            preparedStatement.setString(5, editCity.getText());
            preparedStatement.setString(6, editRegion.getText());
            preparedStatement.setString(7, editCountry.getText());
            preparedStatement.setString(8, editPhoneNumber.getText());
            preparedStatement.setString(9, editEMail.getText());
            preparedStatement.setString(10, editVatNumber.getText());
            preparedStatement.executeUpdate();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("01clients-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientsTable clientsTable = fxmlLoader.getController();
        clientsTable.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onCancelButtonClick() throws IOException {
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
