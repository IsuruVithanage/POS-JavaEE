package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item, String> {
   // boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    //String generateNewID() throws SQLException, ClassNotFoundException;

    //List<String> getAllItemIds() throws SQLException, ClassNotFoundException;

    //boolean updateQTY(String itemCode, int qty) throws SQLException, ClassNotFoundException;

    //ArrayList<ItemSellsDTO> selectAllItemSell() throws SQLException, ClassNotFoundException;

    //int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException;

}
