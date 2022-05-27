package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    /*@Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return false;
    }*/

    @Override
    public String generateItemNewID(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT Itemcode FROM Item ORDER BY Itemcode DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString(1);
            int newItemId = Integer.parseInt(id.replace("I", "")) + 1;
            return String.format("I%03d", newItemId);
        } else {
            return "I001";
        }
    }

    @Override
    public List<String> getAllItemIds(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Item");
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

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
        return CrudUtil.executeUpdate(connection,"DELETE FROM Item WHERE Itemcode=?", s);
    }

    @Override
    public Item search(Connection connection, String s) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Item WHERE ItemCode=?", s);
        rst.next();
        return new Item(
                s,
                rst.getString(2),
                rst.getDouble(3),
                rst.getDouble(4)
        );
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4)
            ));
        }
        return allItems;
    }
}
