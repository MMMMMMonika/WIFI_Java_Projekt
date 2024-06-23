package com.example.billsbillsbills;

import data.Bill;
import data.DBConnection;
import data.DataSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.DBInvoiceViewService;
import logic.RSInvoiceService;
import logic.ResultToList;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BillsTable {
    private Stage stage;
    @FXML
    public Button backButton;
    @FXML
    public Button newButton;
    @FXML
    private TableView billTable;
    @FXML
    private TableColumn billNumber;
    @FXML
    private TableColumn billDate;
    @FXML
    private TableColumn clientNumber;
    @FXML
    private TableColumn companyName;
    @FXML
    private TableColumn address;
    @FXML
    private TableColumn totalPrice;

    ObservableList<Bill> billObservableList = FXCollections.observableArrayList();
    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");
    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    public void initialize() throws SQLException {
        billTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        billNumber.setCellValueFactory(new PropertyValueFactory<>("billNumber"));
        billDate.setCellValueFactory(new PropertyValueFactory<>("billDate"));
        clientNumber.setCellValueFactory(new PropertyValueFactory<>("clientNumber"));
        companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        ResultToList<Bill> billResultToList = new RSInvoiceService();
        DBInvoiceViewService<Bill> billDBReaderService = new DBInvoiceViewService<>(billResultToList);
        List<Bill> billList = billDBReaderService.readBillsFromDB(dbConnection);

        billObservableList.addAll(billList);
        billTable.setItems(billObservableList);
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("08bills-new.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        BillsNew billsNew = fxmlLoader.getController();
        billsNew.setStage(stage);
        stage.setScene(scene);
        stage.show();
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
