package dao.custom;


import dao.CrudDAO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    //boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;
    String generateNewID(Connection connection) throws SQLException, ClassNotFoundException;
    //List<String> getCustomerIds(Connection connection) throws SQLException, ClassNotFoundException;

}
