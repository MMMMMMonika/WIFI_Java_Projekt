module com.example.billsbillsbills {
    opens data to javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires pdfbox;


    opens com.example.billsbillsbills to javafx.fxml;
    exports com.example.billsbillsbills;
}