package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<String> getCustomerIds(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Customer");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT CustID FROM Customer ORDER BY CustID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString(1);
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }
    }

    @Override
    public boolean add(Connection connection,Customer customer) throws SQLException, ClassNotFoundException {
        System.out.println("Add cust DAO");
        return CrudUtil.executeUpdate(connection,"INSERT INTO Customer (CustID, CustName, CustAddress, Salary) VALUES (?,?,?,?)",
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerAddress(),
                customer.getSalary());
    }

    @Override
    public boolean delete(Connection connection,String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"DELETE FROM Customer WHERE CustID=?", s);
    }

    @Override
    public boolean update(Connection connection,Customer customer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Customer search(Connection connection,String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Customer WHERE CustID=?", s);
        rst.next();
        return new Customer(
                s,
                rst.getString(2),
                rst.getString(3),
                rst.getDouble(4)

        );
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }
        return allCustomers;
    }
}
