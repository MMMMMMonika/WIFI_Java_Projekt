package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultToList<T> {
    List<T> resultSetToList(ResultSet resultSet) throws SQLException;
}
