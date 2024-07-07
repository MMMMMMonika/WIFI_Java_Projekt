package logic;

import data.Bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RSInvoiceService implements ResultToList{
    public List<Bill> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Bill> billList = new ArrayList<>();

        while (resultSet.next()) {
            int billID = resultSet.getInt("billID");
            String billNumber = resultSet.getString("billNumber");
            LocalDate billDate = resultSet.getDate("billDate").toLocalDate();
            int clientNumber = resultSet.getInt("clientNumber");
            String companyName = resultSet.getString("companyName");
            String address = resultSet.getString("address");
            Double totalPrice = resultSet.getDouble("totalPrice");

            billList.add(new Bill(billID, billNumber, billDate, clientNumber, companyName, address, totalPrice));
        }
        return billList;
    }
}
