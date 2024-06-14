package logic;

import data.Bill;
import data.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillViewService implements ResultToList{
    public List<Bill> resultSetToList(ResultSet resultSet) throws SQLException, SQLException {
        List<Bill> billList = new ArrayList<>();

        while (resultSet.next()) {
            int billID = resultSet.getInt("billID");
            LocalDate billDate = resultSet.getDate("billDate").toLocalDate();
            int billNumber = resultSet.getInt("billNumber");
            int clientNumber = resultSet.getInt("clientNumber");
            String companyName = resultSet.getString("companyName");
            String address = resultSet.getString("address");
            Double totalPrice = resultSet.getDouble("totalPrice");
            String periodFrom = resultSet.getString("periodFrom");
            String periodUntil = resultSet.getString("periodUntil");

            billList.add(new Bill(billID, billNumber, billDate, clientNumber, companyName, address, totalPrice, periodFrom, periodUntil));
        }
        return billList;
    }
}
