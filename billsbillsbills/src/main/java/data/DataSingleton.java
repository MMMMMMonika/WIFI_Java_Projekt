package data;

public class DataSingleton {

    private static final DataSingleton instance = new DataSingleton();
    private int selection;

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
}
