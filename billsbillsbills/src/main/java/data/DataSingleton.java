package data;

public class DataSingleton {

    private static final DataSingleton instance = new DataSingleton();
    private int selection;
    private double priceSelection;
    private int selectionClientNumber;
    private String selectionCompanyName;
    private String selectionClientAddress;
    private int selectionClientPostalCode;
    private String selectionClientCity;
    private String selectionClientCountry;
    private String selectionClientVatNumber;

    private DataSingleton() {}

    public static DataSingleton getInstance() {
        return instance;
    }
    public int getSelection() {
        return selection;
    }
    public void setSelection(int selection) {
        this.selection = selection;
    }
    public double getPriceSelection() {
        return priceSelection;
    }
    public void setPriceSelection(double priceSelection) {
        this.priceSelection = priceSelection;
    }
    public String getSelectionCompanyName() {
        return selectionCompanyName;
    }
    public void setSelectionCompanyName(String selectionCompanyName) {
        this.selectionCompanyName = selectionCompanyName;
    }
    public int getSelectionClientNumber() {
        return selectionClientNumber;
    }
    public void setSelectionClientNumber(int selectionClientNumber) {
        this.selectionClientNumber = selectionClientNumber;
    }
    public String getSelectionClientAddress() {
        return selectionClientAddress;
    }
    public void setSelectionClientAddress(String selectionClientAddress) {
        this.selectionClientAddress = selectionClientAddress;
    }
    public int getSelectionClientPostalCode() {
        return selectionClientPostalCode;
    }
    public void setSelectionClientPostalCode(int selectionClientPostalCode) {
        this.selectionClientPostalCode = selectionClientPostalCode;
    }
    public String getSelectionClientCity() {
        return selectionClientCity;
    }
    public void setSelectionClientCity(String selectionClientCity) {
        this.selectionClientCity = selectionClientCity;
    }
    public String getSelectionClientCountry() {
        return selectionClientCountry;
    }
    public void setSelectionClientCountry(String selectionClientCountry) {
        this.selectionClientCountry = selectionClientCountry;
    }
    public String getSelectionClientVatNumber() {
        return selectionClientVatNumber;
    }
    public void setSelectionClientVatNumber(String selectionClientVatNumber) {
        this.selectionClientVatNumber = selectionClientVatNumber;
    }
}
