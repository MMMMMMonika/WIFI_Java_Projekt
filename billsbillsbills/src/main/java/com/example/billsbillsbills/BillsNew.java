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
import logic.ClientViewService;
import logic.DBReaderService;
import logic.ProductViewService;
import logic.ResultToList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BillsNew {
    private Stage stage;
    @FXML
    public Button cancelButton;
    @FXML
    public Button createBillButton;
    @FXML
    public Button addButton;
    @FXML
    public Button removeButton;
    @FXML
    private DatePicker enterPeriodFrom;
    @FXML
    private DatePicker enterPeriodUntil;
    @FXML
    private Label labelClientNumber;
    @FXML
    private Label labelCompanyName;
    @FXML
    private Label labelPeriodFrom;
    @FXML
    private Label labelPeriodUntil;
    @FXML
    private Label labelQuantity;
    @FXML
    private Label labelProducts;
    @FXML
    private Label labelProductsAddedToBill;
    @FXML
    private Label labelQuantityWarning;
    @FXML
    private TextField enterClientNumber;
    @FXML
    private TextField enterCompanyName;
    @FXML
    private TextField enterQuantity;
    @FXML
    private TableView productTable;
    @FXML
    private TableView addedProductsTable;
    @FXML
    private TableColumn productNumber;
    @FXML
    private TableColumn productName;
    @FXML
    private TableColumn pricePerUnit;
    @FXML
    private TableColumn productNumberBill;
    @FXML
    private TableColumn productQuantityBill;
    @FXML
    private TableColumn totalPriceBill;

    ObservableList<Product> productObservableList = FXCollections.observableArrayList();
    ObservableList<Product> selectedProductsObservableList = FXCollections.observableArrayList();
    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");
    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    private void initialize() {
        productTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productNumber.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        pricePerUnit.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        ResultToList<Product> productResultToList = new ProductViewService();
        DBReaderService<Product> productDBReaderService = new DBReaderService<>(productResultToList);
        List<Product> productList = productDBReaderService.readProductsFromDB(dbConnection);

        productObservableList.addAll(productList);
        productTable.setItems(productObservableList);
    }

    @FXML
    private void onAddButtonClick() throws SQLException {
        //add selected product to bill + quantity check
        if (Double.parseDouble(enterQuantity.getText()) <= 0.4) {
            labelQuantityWarning.setText("Quantity can't be less than 0.5");
        } else {
            labelQuantityWarning.setText("");

            int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
            dataSingleton.setSelection(productObservableList.get(selectedIndex).getProductID());

            ResultToList<Product> selectedProductResultToList = new ProductViewService();
            DBReaderService<Product> selectedProductDBReaderService = new DBReaderService<>(selectedProductResultToList);
            List<Product> selectedProductList = selectedProductDBReaderService.readProductSelectionFromDB(dbConnection);
            selectedProductsObservableList.addAll(selectedProductList);
            addedProductsTable.setItems(selectedProductsObservableList);

            addedProductsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            productNumberBill.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
        }
    }

    @FXML
    private void onRemoveButtonClick() {
        int selectedIndex = addedProductsTable.getSelectionModel().getSelectedIndex();
        selectedProductsObservableList.remove(selectedIndex);
    }
    @FXML
    private void onCreateButtonClick() {}
    @FXML
    private void onCancelButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("03bills-view.fxml"));
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
