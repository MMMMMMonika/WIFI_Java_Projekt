package data;

public class DataSingleton {

    private static final DataSingleton instance = new DataSingleton();
    private int clientSelection;

    private DataSingleton() {}

    public static DataSingleton getInstance() {
        return instance;
    }

    public int getClientSelection() {
        return clientSelection;
    }

    public void setClientSelection(int clientSelection) {
        this.clientSelection = clientSelection;
    }
}
