package logic;

import data.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientViewService implements ResultToList{

    public List<Client> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Client> clientList = new ArrayList<>();

        while (resultSet.next()) {
            int clientID = resultSet.getInt("clientID");
            int clientNumber = resultSet.getInt("clientNumber");
            String companyName = resultSet.getString("companyName");
            String contactName = resultSet.getString("contactName");
            String address = resultSet.getString("address");
            int postalCode = resultSet.getInt("postalCode");
            String city = resultSet.getString("city");
            String region = resultSet.getString("region");
            String country = resultSet.getString("country");
            String phoneNumber = resultSet.getString("phoneNumber");
            String eMail = resultSet.getString("eMail");
            String vatNumber = resultSet.getString("vatNumber");

            clientList.add(new Client(clientID, clientNumber, companyName, contactName, address, postalCode, city, region, country, phoneNumber, eMail, vatNumber));
        }
        return clientList;
    }
}
