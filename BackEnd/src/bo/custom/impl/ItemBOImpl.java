package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    /*@Override
    public boolean ifItemExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
*/
    /*@Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }*/

   /* @Override
    public List<String> getItemIds() throws SQLException, ClassNotFoundException {
        return null;
    }*/

    @Override
    public boolean addItem(Connection connection,ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        System.out.println("sdsdsd");
        return itemDAO.add(connection,new Item(
                itemDTO.getItemID(),
                itemDTO.getItemName(),
                itemDTO.getQty(),
                itemDTO.getPrice()
        ));
    }

    /*@Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return false;
    }*/

    /*@Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        return false;
    }*/

    /*@Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        return null;
    }*/

    /*@Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
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
}
