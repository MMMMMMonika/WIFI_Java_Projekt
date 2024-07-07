package data;

public class Product {
    private int productID;
    private int productNumber;
    private String productName;
    private String productDescription;
    private double productPrice;
    private double quantity;
    private double totalPrice;

    public Product(int productID, int productNumber, String productName, String productDescription, double productPrice, double quantity, double totalPrice) {
        this.productID = productID;
        this.productNumber = productNumber;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    public int getProductID() {
        return productID;
    }
    public void setProductID(int productID) {
        this.productID = productID;
    }
    public int getProductNumber() {
        return productNumber;
    }
    public void setProductNumber(int productNumber) {
        this.productNumber = productNumber;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productNumber=" + productNumber +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
