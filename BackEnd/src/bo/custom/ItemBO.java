package bo.custom;

import dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemBO {
    //boolean ifItemExist(String id) throws SQLException, ClassNotFoundException;

    //String generateNewID() throws SQLException, ClassNotFoundException;

    //List<String> getItemIds() throws SQLException, ClassNotFoundException;

    boolean addItem(Connection connection,ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    //boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    //boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException;

    //ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    //ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    //boolean updateQTY(String itemCode, int qty) throws SQLException, ClassNotFoundException;

    //ArrayList<ItemSellsDTO> selectAllItemSell() throws SQLException, ClassNotFoundException;

    //int getItemQTYOnHand(String id) throws SQLException, ClassNotFoundException;

}
