package data;

import java.time.LocalDate;

public class Bill {
    private int billID;
    private String billNumber;
    private LocalDate billDate;
    private int clientNumber;
    private String companyName;
    private String address;
    private double totalPrice;

    public Bill(int billID, String billNumber, LocalDate billDate, int clientNumber, String companyName, String address, double totalPrice) {
        this.billID = billID;
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.clientNumber = clientNumber;
        this.companyName = companyName;
        this.address = address;
        this.totalPrice = totalPrice;
    }
    public int getBillID() {
        return billID;
    }
    public void setBillID(int billID) {
        this.billID = billID;
    }
    public String getBillNumber() {
        return billNumber;
    }
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }
    public LocalDate getBillDate() {
        return billDate;
    }
    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }
    public int getClientNumber() {
        return clientNumber;
    }
    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
