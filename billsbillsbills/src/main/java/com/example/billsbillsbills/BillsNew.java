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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    private Label clientNumber;
    @FXML
    private ComboBox selectCompanyName;
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
    ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    DBConnection dbConnection = new DBConnection("jdbc:mysql://localhost:3306/codecafe", "root", "MySQL123!");
    DataSingleton dataSingleton = DataSingleton.getInstance();

    @FXML
    private void initialize() throws SQLException {
        productTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productNumber.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
        productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        pricePerUnit.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        ResultToList<Product> productResultToList = new ProductViewService();
        DBReaderService<Product> productDBReaderService = new DBReaderService<>(productResultToList);
        List<Product> productList = productDBReaderService.readProductsFromDB(dbConnection);

        productObservableList.addAll(productList);
        productTable.setItems(productObservableList);

        ResultToList<Client> clientResultToList = new ClientViewService();
        DBReaderService<Client> clientDBReaderService = new DBReaderService<>(clientResultToList);
        List<Client> clientList = clientDBReaderService.readClientsFromDB(dbConnection);

        clientObservableList.addAll(clientList);
        selectCompanyName.setItems(clientObservableList);
    }

    @FXML
    private void onAddButtonClick() throws SQLException {
        if (enterQuantity.getText().isEmpty()) {
            labelQuantityWarning.setText("Please enter a quantity!");
        } else if (Double.parseDouble(enterQuantity.getText()) <= 0.24) {
            labelQuantityWarning.setText("Quantity can't be less than 0.25!");
        } else {
            labelQuantityWarning.setText("");

            int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
            dataSingleton.setSelection(productObservableList.get(selectedIndex).getProductID());
            dataSingleton.setPriceSelection(productObservableList.get(selectedIndex).getProductPrice());

            String addQuantityQuery = "UPDATE product SET quantity = ?, totalPrice = ? WHERE productID = ?";
            try (Connection connection = DriverManager.getConnection(
                    dbConnection.dbUrl(),
                    dbConnection.dbUser(),
                    dbConnection.dbPw());

                 PreparedStatement preparedStatement = connection.prepareStatement(addQuantityQuery)) {
                preparedStatement.setDouble(1, Double.parseDouble(enterQuantity.getText()));
                preparedStatement.setDouble(2, (Double.parseDouble(enterQuantity.getText())) *
                        dataSingleton.getPriceSelection());
                preparedStatement.setInt(3, dataSingleton.getSelection());
                preparedStatement.executeUpdate();
            }

            ResultToList<Product> selectedProductResultToList = new ProductViewService();
            DBReaderService<Product> selectedProductDBReaderService = new DBReaderService<>(selectedProductResultToList);
            List<Product> selectedProductList = selectedProductDBReaderService.readProductSelectionFromDB(dbConnection);
            selectedProductsObservableList.addAll(selectedProductList);
            addedProductsTable.setItems(selectedProductsObservableList);

            addedProductsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            productNumberBill.setCellValueFactory(new PropertyValueFactory<>("productNumber"));
            productQuantityBill.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            totalPriceBill.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        }
    }
    @FXML
    private void onRemoveButtonClick() {
        int selectedIndex = addedProductsTable.getSelectionModel().getSelectedIndex();
        selectedProductsObservableList.remove(selectedIndex);
    }
    @FXML
    private void companySelected() {
        int selectedIndex = selectCompanyName.getSelectionModel().getSelectedIndex();
        dataSingleton.setSelection(clientObservableList.get(selectedIndex).getClientNumber());

        if (selectCompanyName.getSelectionModel().isEmpty()) {
            clientNumber.setText("");
        } else {
            clientNumber.setText(String.valueOf(dataSingleton.getSelection()));
        }
    }


    @FXML
    private void onCreateButtonClick() throws IOException {
        //retrieve client information
        int clientSelection = selectCompanyName.getSelectionModel().getSelectedIndex();
        dataSingleton.setSelectionClientNumber(clientObservableList.get(clientSelection).getClientNumber());
        dataSingleton.setSelectionCompanyName(clientObservableList.get(clientSelection).getCompanyName());
        dataSingleton.setSelectionClientAddress(clientObservableList.get(clientSelection).getAddress());
        dataSingleton.setSelectionClientPostalCode(clientObservableList.get(clientSelection).getPostalCode());
        dataSingleton.setSelectionClientCity(clientObservableList.get(clientSelection).getCity());
        dataSingleton.setSelectionClientCountry(clientObservableList.get(clientSelection).getCountry());
        dataSingleton.setSelectionClientVatNumber(clientObservableList.get(clientSelection).getVatNumber());
        //retrieve product information
        int productSelection = addedProductsTable.getSelectionModel().getSelectedIndex();
        dataSingleton.setSelectionProductNumber(selectedProductsObservableList.get(productSelection).getProductNumber());
        dataSingleton.setSelectionProductName(selectedProductsObservableList.get(productSelection).getProductName());
        dataSingleton.setSelectionProductUnitPrice(selectedProductsObservableList.get(productSelection).getProductPrice());
        dataSingleton.setSelectionProductQuantity(selectedProductsObservableList.get(productSelection).getQuantity());
        dataSingleton.setSelectionProductTotalPrice(selectedProductsObservableList.get(productSelection).getTotalPrice());
        //invoice number
        String invoiceNumber = "20240000";

        //create new document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //my details
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 9);
        contentStream.newLineAtOffset(440, 800);
        contentStream.showText("Company Name");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Company Address");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Company Postal Code + City");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Company Country");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("VAT Number: ");
        contentStream.newLineAtOffset(0, -30);
        contentStream.showText("Invoice Date: " + LocalDate.now());
        contentStream.endText();
        //invoice title
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(250, 700);
        contentStream.showText("INVOICE " + invoiceNumber);
        contentStream.endText();
        //client details
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 9);
        contentStream.newLineAtOffset(40, 675);
        contentStream.showText("Account Nr.: " + dataSingleton.getSelectionClientNumber());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(dataSingleton.getSelectionCompanyName());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(dataSingleton.getSelectionClientAddress());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(dataSingleton.getSelectionClientPostalCode() + " " + dataSingleton.getSelectionClientCity());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(dataSingleton.getSelectionClientCountry());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("VAT Number: " + dataSingleton.getSelectionClientVatNumber());
        contentStream.endText();
        //item headers
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 9);
        contentStream.newLineAtOffset(40, 575);
        contentStream.showText("Item");
        contentStream.newLineAtOffset(175, 0);
        contentStream.showText("Unit price");
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("Quantity");
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("VAT");
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("Total");
        contentStream.endText();

        contentStream.close();

        document.save(invoiceNumber +".pdf");

        document.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File creation successful!");
        alert.setHeaderText(null);
        alert.setContentText("Your file has been saved to the following path:" +"\n"
                + "C:/Users/Monika/Desktop/wah/0 WIFI Java Project/billsbillsbills");
        Optional<ButtonType> action = alert.showAndWait();
    }


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