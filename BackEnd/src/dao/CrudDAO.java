package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T, ID> {
    boolean add(Connection connection, T t) throws SQLException, ClassNotFoundException;

    boolean delete(Connection connection,ID id) throws SQLException, ClassNotFoundException;

    boolean update(Connection connection,T t) throws SQLException, ClassNotFoundException;

    T search(Connection connection,ID id) throws SQLException, ClassNotFoundException;

    ArrayList<T> getAll(Connection connection) throws SQLException, ClassNotFoundException;
}
