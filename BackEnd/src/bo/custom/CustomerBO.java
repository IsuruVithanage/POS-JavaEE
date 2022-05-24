package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID(Connection connection) throws SQLException, ClassNotFoundException;

    List<String> getCustomerIds(Connection connection) throws SQLException, ClassNotFoundException;

    boolean addCustomer(Connection connection,CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(Connection connection,String id) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(Connection connection,String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;
}
