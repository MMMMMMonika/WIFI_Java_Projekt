package com.example.billsbillsbills;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreen {
    @FXML
    public Button clientsButton;
    @FXML
    public Button productsButton;
    @FXML
    public Button billsButton;
    private Stage stage;

    @FXML
    protected void onClientsButtonClick() throws IOException {
        Stage stage = (Stage) clientsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("clients-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ClientsTable clientsTable = fxmlLoader.getController();
        clientsTable.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onProductsButtonClick() throws IOException {
        Stage stage = (Stage) clientsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("products-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        ProductsTable productsTable = fxmlLoader.getController();
        productsTable.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    protected void onBillsButtonClick() throws IOException {
        Stage stage = (Stage) clientsButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bills-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        BillsTable billsTable = fxmlLoader.getController();
        billsTable.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}