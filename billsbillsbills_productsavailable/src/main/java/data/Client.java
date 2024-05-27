package data;

public class Client {
    private int clientID;
    private int clientNumber;
    private String companyName;
    private String contactName;
    private String address;
    private int postalCode;
    private String city;
    private String region;
    private String country;
    private String phoneNumber;
    private String eMail;
    private String vatNumber;

    public Client(int clientID, int clientNumber, String companyName, String contactName, String address, int postalCode, String city, String region, String country, String phoneNumber, String eMail, String vatNumber) {
        this.clientID = clientID;
        this.clientNumber = clientNumber;
        this.companyName = companyName;
        this.contactName = contactName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.region = region;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.vatNumber = vatNumber;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }


    @Override
    public String toString() {
        return "Client{" +
                "clientID=" + clientID +
                ", clientNumber=" + clientNumber +
                ", companyName='" + companyName + '\'' +
                ", contactName='" + contactName + '\'' +
                ", address='" + address + '\'' +
                ", postalCode=" + postalCode +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", eMail='" + eMail + '\'' +
                ", vatNumber='" + vatNumber + '\'' +
                '}';
    }
}
