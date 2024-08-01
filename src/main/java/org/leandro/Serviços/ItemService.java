package org.leandro.Serviços;

// ItemService.java
import org.leandro.DAO.ItemDAO;
import org.leandro.models.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemService {
    private ItemDAO itemDAO;

    public ItemService() {
        this.itemDAO = new ItemDAO();
    }

    public void addItem(String tipo, double preco) throws SQLException {
        Item item = new Item(0, tipo, preco);
        itemDAO.saveItem(item);
    }

    public List<Item> getAllItems() throws SQLException {
        return itemDAO.getAllItems();
    }

    public Item getItemById(int id) throws SQLException {
        return itemDAO.getItemById(id);
    }

    public void updateItem(Item item) throws SQLException {
        itemDAO.updateItem(item);
    }

    public void deleteItem(int id) throws SQLException {
        itemDAO.deleteItem(id);
    }
}
