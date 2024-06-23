package com.example.billsbillsbills;

import data.Client;
import data.DBConnection;
import data.DataSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.RSClientService;
import logic.DBClientViewService;
import logic.ResultToList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientsTable {
    @FXML
    public Button backButton;
    @FXML
    public Button newButton;
    @FXML
    public Button editButton;
    @FXML
    public Button deleteButton;
    @FXML
    private TableColumn<Client, Integer> clientNumber;
    @FXML
    private TableColumn<Client, String> companyName;
    @FXML
    private TableColumn<Client, String> contactName;
    @FXML
    private TableColumn<Client, String> address;
    @FXML
    private TableColumn<Client, String> postalCode;
    @FXML
    private TableColumn<Client, String> city;
    @FXML
    private TableColumn<Client, String> region;
    @FXML
    private TableColumn<Client, String> country;
    @FXML
    private TableColumn<Client, String> phoneNumber;
    @FXML
    private TableColumn<Client, String> eMail;
    @FXML
    private TableColumn<Client, String> vatNumber;
    @FXML
    private TableView<Client> clientTable;
    private Stage stage;
    ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");
    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    public void initialize() throws SQLException {
        clientTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        clientNumber.setCellValueFactory(new PropertyValueFactory<>("clientNumber"));
        companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        contactName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        region.setCellValueFactory(new PropertyValueFactory<>("region"));
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        eMail.setCellValueFactory(new PropertyValueFactory<>("eMail"));
        vatNumber.setCellValueFactory(new PropertyValueFactory<>("vatNumber"));

        ResultToList<Client> clientResultToList = new RSClientService();
        DBClientViewService<Client> clientDBClientViewService = new DBClientViewService<>(clientResultToList);
        List<Client> clientList = clientDBClientViewService.readClientsFromDB(dbConnection);

        clientObservableList.addAll(clientList);
        clientTable.setItems(clientObservableList);
    }
    @FXML
    public void onBackButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("00hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloController helloController = fxmlLoader.getController();
        helloController.setStage(stage);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    public void onNewButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("04clients-new.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientsNew clientsNew = fxmlLoader.getController();
        clientsNew.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onEditButtonClick() throws IOException {
        if(clientTable.getSelectionModel().getSelectedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a client to continue!");
            Optional<ButtonType> action = alert.showAndWait();
        } else {
            int indexSelection = clientTable.getSelectionModel().getSelectedIndex();
            dataSingleton.setSelection(clientObservableList.get(indexSelection).getClientID());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("06clients-edit.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ClientsEdit clientsEdit = fxmlLoader.getController();
            clientsEdit.setStage(stage);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void onDeleteButtonClick() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Client Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this Client?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent() && action.get() == ButtonType.OK) {
            String deleteClientQuery = "DELETE FROM codecafe.client WHERE clientid = ?";
            int selectedDeletionID = clientTable.getSelectionModel().getSelectedIndex();

            try (Connection connection = DriverManager.getConnection(
                    dbConnection.dbUrl(),
                    dbConnection.dbUser(),
                    dbConnection.dbPw());

                 PreparedStatement preparedStatement = connection.prepareStatement(deleteClientQuery)) {
                 preparedStatement.setInt(1, clientObservableList.get(selectedDeletionID).getClientID());
                 preparedStatement.executeUpdate();
                 clientObservableList.remove(selectedDeletionID);
                 clientTable.refresh();
            }
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
