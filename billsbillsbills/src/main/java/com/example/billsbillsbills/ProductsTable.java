package com.example.billsbillsbills;

import data.Client;
import data.DBConnection;
import data.DataSingleton;
import data.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.DBProductViewService;
import logic.RSProductService;
import logic.ResultToList;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductsTable {
    private Stage stage;
    @FXML
    private TableColumn<Client, Integer> productNumber;
    @FXML
    private TableColumn<Client, String> productName;
    @FXML
    private TableColumn<Client, String> productDescription;
    @FXML
    private TableColumn<Client, String> productPrice;
    @FXML
    private TableView<Product> productTable;
    @FXML
    public Button backButton;
    @FXML
    public Button newButton;
    @FXML
    public Button editButton;
    @FXML
    public Button deleteButton;

    ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");
    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    public void initialize() throws SQLException {
        productTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productNumber.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productDescription.setCellValueFactory(new PropertyValueFactory<>("productDescription"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        ResultToList<Product> productResultToList = new RSProductService();
        DBProductViewService<Product> productDBReaderService = new DBProductViewService<>(productResultToList);
        List<Product> productList = productDBReaderService.readProductsFromDB(dbConnection);

        productObservableList.addAll(productList);
        productTable.setItems(productObservableList);
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("05products-new.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ProductsNew productsNew = fxmlLoader.getController();
        productsNew.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onEditButtonClick() throws IOException {
        if(productTable.getSelectionModel().getSelectedIndex() < 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to continue!");
            Optional<ButtonType> action = alert.showAndWait();
        } else {
            int indexSelection = productTable.getSelectionModel().getSelectedIndex();
            dataSingleton.setSelection(productObservableList.get(indexSelection).getProductID());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("07products-edit.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ProductsEdit productsEdit = fxmlLoader.getController();
            productsEdit.setStage(stage);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void onDeleteButtonClick() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Product Deletion");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this Product?");

        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent() && action.get() == ButtonType.OK) {
            String deleteClientQuery = "DELETE FROM product WHERE productid = ?";
            int selectedID = productTable.getSelectionModel().getSelectedIndex();

            try (Connection connection = DriverManager.getConnection(
                    dbConnection.dbUrl(),
                    dbConnection.dbUser(),
                    dbConnection.dbPw());

                 PreparedStatement preparedStatement = connection.prepareStatement(deleteClientQuery)) {
                preparedStatement.setInt(1, productObservableList.get(selectedID).getProductID());
                preparedStatement.executeUpdate();
                productObservableList.remove(selectedID);
                productTable.refresh();
            }
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
