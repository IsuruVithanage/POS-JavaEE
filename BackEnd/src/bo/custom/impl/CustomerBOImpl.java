package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(id);
    }

    @Override
    public String generateNewID(Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID(connection);
    }

    @Override
    public List<String> getCustomerIds(Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.getCustomerIds(connection);
    }

    @Override
    public boolean addCustomer(Connection connection,CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        System.out.println("BO add customer");
        return customerDAO.add(connection,new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getCustomerName(),
                customerDTO.getCustomerAddress(),
                customerDTO.getSalary()
        ));
    }

    @Override
    public boolean deleteCustomer(Connection connection,String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(connection,id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public CustomerDTO searchCustomer(Connection connection,String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(connection,id);
        return new CustomerDTO(
                c.getCustomerId(),
                c.getCustomerName(),
                c.getCustomerAddress(),
                c.getSalary()
        );
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer c : customers) {
            customerDTOS.add(new CustomerDTO(
                    c.getCustomerId(),
                    c.getCustomerName(),
                    c.getCustomerAddress(),
                    c.getSalary()
            ));
        }
        return customerDTOS;
    }
}
