package logic;

import data.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductViewService implements ResultToList{

    public List<Product> resultSetToList(ResultSet resultSet) throws SQLException {
        List<Product> productList = new ArrayList<>();

        while (resultSet.next()) {
            int productID = resultSet.getInt("productID");
            int productNumber = resultSet.getInt("productNumber");
            String productName = resultSet.getString("productName");
            String productDesc = resultSet.getString("productDescription");
            double productPrice = resultSet.getDouble("productPrice");

            productList.add(new Product(productID, productNumber, productName, productDesc, productPrice));
        }
        return productList;
    }
}
