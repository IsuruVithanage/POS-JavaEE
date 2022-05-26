package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    /*@Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return false;
    }*/

    /*@Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }*/

    /*@Override
    public List<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return null;
    }*/

    /*@Override
    public boolean updateQTY(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return false;
    }*/

    /*@Override
    public int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException {
        return 0;
    }*/

    @Override
    public boolean add(Connection connection, Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO Item (Itemcode, ItemName, Qty, Price) VALUES (?,?,?,?)",
                item.getItemID(),
                item.getItemName(),
                item.getQty(),
                item.getPrice()
        );
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Item search(Connection connection, String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        return null;
    }
}
