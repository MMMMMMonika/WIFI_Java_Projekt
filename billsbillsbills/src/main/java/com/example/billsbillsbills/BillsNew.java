package com.example.billsbillsbills;

import data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String productQuery = "SELECT * FROM codecafe.product";
        ResultToList<Product> productResultToList = new RSProductService();
        DBViewService<Product> productDBReaderService = new DBViewService<>(productResultToList);
        List<Product> productList = productDBReaderService.readAllFromDB(dbConnection, productQuery);
        productObservableList.addAll(productList);
        productTable.setItems(productObservableList);

        String clientQuery = "SELECT * FROM codecafe.client";
        ResultToList<Client> clientResultToList = new RSClientService();
        DBViewService<Client> dbViewService = new DBViewService<>(clientResultToList);
        List<Client> clientList = dbViewService.readAllFromDB(dbConnection, clientQuery);
        clientObservableList.addAll(clientList);
        selectCompanyName.setItems(clientObservableList);
    }
    @FXML
    private void onAddButtonClick() throws SQLException {
        Pattern quantityPatternDouble = Pattern.compile("^[0-9]\\d*(\\.\\d+)?$");
        Matcher quantityMatcherDouble = quantityPatternDouble.matcher(enterQuantity.getText());
        boolean correctQuantityDouble = quantityMatcherDouble.find();

        if (enterQuantity.getText().isEmpty() || !correctQuantityDouble) {
            labelQuantityWarning.setText("Please enter a valid quantity!");
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

            String productSelectionQuery = "SELECT * FROM codecafe.product WHERE productid = ?";
            ResultToList<Product> selectedProductResultToList = new RSProductService();
            DBSelectionViewService<Product> selectedProductDBReaderService =
                    new DBSelectionViewService<>(selectedProductResultToList);
            List<Product> selectedProductList = selectedProductDBReaderService.readSelectionFromDB(dbConnection, productSelectionQuery);
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
    private void onCreateButtonClick() throws IOException, SQLException, InterruptedException {
        int clientSelection = selectCompanyName.getSelectionModel().getSelectedIndex();
        dataSingleton.setSelectionClientNumber(clientObservableList.get(clientSelection).getClientNumber());
        dataSingleton.setSelectionCompanyName(clientObservableList.get(clientSelection).getCompanyName());
        dataSingleton.setSelectionClientAddress(clientObservableList.get(clientSelection).getAddress());
        dataSingleton.setSelectionClientPostalCode(clientObservableList.get(clientSelection).getPostalCode());
        dataSingleton.setSelectionClientCity(clientObservableList.get(clientSelection).getCity());
        dataSingleton.setSelectionClientCountry(clientObservableList.get(clientSelection).getCountry());
        dataSingleton.setSelectionClientVatNumber(clientObservableList.get(clientSelection).getVatNumber());

        ResultToList<Bill> billResultToList = new RSInvoiceService();
        DBInvoiceDateReaderService<Bill> DBInvoiceDateReaderService = new DBInvoiceDateReaderService<>(billResultToList);
        int amountOfInvoicesWithTodaysDate = DBInvoiceDateReaderService.readAmountOfInvoiceDatesFromDB(dbConnection) + 1;

        String todaysDate = String.valueOf(LocalDate.now());
        String invoiceDateNumber = todaysDate.replace("-", "");

        String invoiceNumber = invoiceDateNumber + "-" + amountOfInvoicesWithTodaysDate;

        String invoiceDate = String.valueOf(LocalDate.now());

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        PDType1Font helvetica = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        PDType1Font helveticaBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

        contentStream.beginText();
        contentStream.setFont(helvetica, 9);
        contentStream.newLineAtOffset(440, 800);
        contentStream.showText("Macrosoft");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Währinger Gürtel 97");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("1180 Vienna");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Austria");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("VAT Number: ATU09061998");
        contentStream.newLineAtOffset(0, -30);
        contentStream.showText("Invoice Date: " + invoiceDate);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helveticaBold, 12);
        contentStream.newLineAtOffset(250, 700);
        contentStream.showText("INVOICE " + invoiceNumber);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helvetica, 9);
        contentStream.newLineAtOffset(40, 675);
        contentStream.showText(dataSingleton.getSelectionCompanyName());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(dataSingleton.getSelectionClientAddress());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(dataSingleton.getSelectionClientPostalCode() + " " + dataSingleton.getSelectionClientCity());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText(dataSingleton.getSelectionClientCountry());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("VAT Number: " + dataSingleton.getSelectionClientVatNumber());
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Account Nr.: " + dataSingleton.getSelectionClientNumber());
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helveticaBold, 9);
        contentStream.newLineAtOffset(40, 575);
        contentStream.showText("Item");
        contentStream.newLineAtOffset(220, 0);
        contentStream.showText("Unit price");
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("Quantity");
        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("Total");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helvetica, 9);
        contentStream.newLineAtOffset(40, 550);
        contentStream.showText(selectedProductsObservableList.get(0).getProductName());
        for (int i = 1; i < selectedProductsObservableList.size(); i++) {
            if (selectedProductsObservableList.get(i).getProductName().isEmpty()) {
                return;
            } else {
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(selectedProductsObservableList.get(i).getProductName());}
        }
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helvetica, 9);
        contentStream.newLineAtOffset(260, 550);
        contentStream.showText(String.valueOf(selectedProductsObservableList.get(0).getProductPrice()));
        for (int i = 1; i < selectedProductsObservableList.size(); i++) {
            if (selectedProductsObservableList.get(i).getProductPrice() == 0) {
                return;
            } else {
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(String.valueOf(selectedProductsObservableList.get(i).getProductPrice()));}
        }
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helvetica, 9);
        contentStream.newLineAtOffset(360, 550);
        contentStream.showText(String.valueOf(selectedProductsObservableList.get(0).getQuantity()));
        for (int i = 1; i < selectedProductsObservableList.size(); i++) {
            if (selectedProductsObservableList.get(i).getQuantity() == 0) {
                return;
            } else {
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(String.valueOf(selectedProductsObservableList.get(i).getQuantity()));}
        }
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helvetica, 9);
        contentStream.newLineAtOffset(460, 550);
        double sum = selectedProductsObservableList.get(0).getTotalPrice();
        contentStream.showText(String.valueOf(selectedProductsObservableList.get(0).getTotalPrice()));
        for (int i = 1; i < selectedProductsObservableList.size(); i++) {
            if (selectedProductsObservableList.get(i).getTotalPrice() == 0) {
                return;
            } else {
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(String.valueOf(selectedProductsObservableList.get(i).getTotalPrice()));
            } sum += selectedProductsObservableList.get(i).getTotalPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        contentStream.newLineAtOffset(-100, -30);
        contentStream.showText("Net Total: ");
        contentStream.newLineAtOffset(+70, 0);
        contentStream.showText("EUR ");
        contentStream.newLineAtOffset(+30, 0);
        contentStream.showText(String.valueOf(decimalFormat.format(sum)));

        double vat = (sum * 1.2)-sum;
        contentStream.newLineAtOffset(-100, -20);
        contentStream.showText("20% VAT: ");
        contentStream.newLineAtOffset(+70, 0);
        contentStream.showText("EUR ");
        contentStream.newLineAtOffset(+30, 0);
        contentStream.showText(String.valueOf(decimalFormat.format(vat)));

        double grandTotal = sum + vat;
        contentStream.setFont(helveticaBold, 9);
        contentStream.newLineAtOffset(-100, -20);
        contentStream.showText("Grand Total: ");
        contentStream.newLineAtOffset(+70, 0);
        contentStream.showText("EUR ");
        contentStream.newLineAtOffset(+30, 0);
        contentStream.showText(String.valueOf(decimalFormat.format(grandTotal)));

        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helveticaBold, 9);
        contentStream.newLineAtOffset(40, 150);
        contentStream.showText("Payment Terms:");
        contentStream.setFont(helvetica, 8);
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Payment is due within thirty (30) days from the date of invoice.");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Failure to pay within this term will result in a late fee of EUR 10.00.");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Please mention the invoice number when processing payment.");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("Any discrepancies should be reported within fourteen (14) days of receipt.");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(helveticaBold, 9);
        contentStream.newLineAtOffset(40, 75);
        contentStream.showText("Bank Account Details:");
        contentStream.newLineAtOffset(0, -12);
        contentStream.setFont(helvetica, 8);
        contentStream.showText("Account Holder: Macrosoft GmbH");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("IBAN: AT24 0906 2012 2090");
        contentStream.newLineAtOffset(0, -12);
        contentStream.showText("SWIFT: BKAUATWW");
        contentStream.endText();

        contentStream.close();

        document.save(invoiceNumber + ".pdf");

        document.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("File creation successful!");
        alert.setHeaderText(null);
        alert.setContentText("Your file has been successfully created!");
        Optional<ButtonType> action = alert.showAndWait();


        String newInvoiceQuery = "INSERT INTO bill (billnumber, billdate, clientnumber, companyname, address, totalprice)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(
                dbConnection.dbUrl(),
                dbConnection.dbUser(),
                dbConnection.dbPw());

             PreparedStatement preparedStatement = connection.prepareStatement(newInvoiceQuery)) {
            preparedStatement.setString(1, invoiceNumber);
            preparedStatement.setDate(2, Date.valueOf(invoiceDate));
            preparedStatement.setInt(3, dataSingleton.getSelectionClientNumber());
            preparedStatement.setString(4, dataSingleton.getSelectionCompanyName());
            preparedStatement.setString(5, dataSingleton.getSelectionClientAddress());
            preparedStatement.setDouble(6, grandTotal);
            preparedStatement.executeUpdate();
        }
    }
    @FXML
    private void onCancelButtonClick() throws IOException {
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