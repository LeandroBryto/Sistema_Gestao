package org.leandro.DAO;

import org.leandro.models.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private Connection connection;

    public ItemDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Método para salvar um item
    public void saveItem(Item item) throws SQLException {
        String sql = "INSERT INTO Itens (pedido_id, descricao, quantidade, preco) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, item.getPedidoId());
            statement.setString(2, item.getDescricao());
            statement.setInt(3, item.getQuantidade());
            statement.setDouble(4, item.getPreco());
            statement.executeUpdate();
        }
    }

    // Método para obter todos os itens
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM Itens";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getInt("pedido_id"),
                        resultSet.getString("descricao"),
                        resultSet.getInt("quantidade"),
                        resultSet.getDouble("preco")
                );
                item.setId(resultSet.getInt("id"));
                items.add(item);
            }
        }
        return items;
    }

    // Método para obter um item por ID
    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT * FROM Itens WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Item(
                            resultSet.getInt("pedido_id"),
                            resultSet.getString("descricao"),
                            resultSet.getInt("quantidade"),
                            resultSet.getDouble("preco")
                    ) {{
                        setId(resultSet.getInt("id"));
                    }};
                } else {
                    return null; // Item não encontrado
                }
            }
        }
    }

    // Método para atualizar um item
    public void updateItem(Item item) throws SQLException {
        String sql = "UPDATE Itens SET pedido_id = ?, descricao = ?, quantidade = ?, preco = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, item.getPedidoId());
            statement.setString(2, item.getDescricao());
            statement.setInt(3, item.getQuantidade());
            statement.setDouble(4, item.getPreco());
            statement.setInt(5, item.getId());
            statement.executeUpdate();
        }
    }

    // Método para deletar um item
    public void deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM Itens WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
