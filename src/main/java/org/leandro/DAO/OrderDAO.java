package org.leandro.DAO;

import org.leandro.models.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;

    public OrderDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Método para salvar um pedido
    public void saveOrder(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedidos (cliente_id, data_entrega, observacao, status, total) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pedido.getClienteId());
            statement.setString(2, pedido.getDataEntrega());
            statement.setString(3, pedido.getObservacao());
            statement.setString(4, pedido.getStatus());
            statement.setDouble(5, pedido.getTotal());
            statement.executeUpdate();
        }
    }

    // Método para obter todos os pedidos
    public List<Pedido> getAllOrders() throws SQLException {
        List<Pedido> orders = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Pedido order = new Pedido(
                        resultSet.getInt("cliente_id"),
                        resultSet.getString("data_entrega"),
                        resultSet.getString("observacao"),
                        resultSet.getString("status"),
                        resultSet.getDouble("total")
                );
                order.setId(resultSet.getInt("id"));
                orders.add(order);
            }
        }
        return orders;
    }

    // Método para obter um pedido por ID
    public Pedido getOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM Pedidos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Pedido(
                            resultSet.getInt("cliente_id"),
                            resultSet.getString("data_entrega"),
                            resultSet.getString("observacao"),
                            resultSet.getString("status"),
                            resultSet.getDouble("total")
                    );
                } else {
                    return null; // Pedido não encontrado
                }
            }
        }
    }

    // Método para atualizar um pedido
    public void updateOrder(Pedido pedido) throws SQLException {
        String sql = "UPDATE Pedidos SET cliente_id = ?, data_entrega = ?, observacao = ?, status = ?, total = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pedido.getClienteId());
            statement.setString(2, pedido.getDataEntrega());
            statement.setString(3, pedido.getObservacao());
            statement.setString(4, pedido.getStatus());
            statement.setDouble(5, pedido.getTotal());
            statement.setInt(6, pedido.getId());
            statement.executeUpdate();
        }
    }

    // Método para obter um pedido por ID de cliente
    public Pedido getOrderByClientId(int clientId) throws SQLException {
        String sql = "SELECT * FROM Pedidos WHERE cliente_id = ? LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pedido pedido = new Pedido(
                            resultSet.getInt("cliente_id"),
                            resultSet.getString("data_entrega"),
                            resultSet.getString("observacao"),
                            resultSet.getString("status"),
                            resultSet.getDouble("total")
                    );
                    pedido.setId(resultSet.getInt("id"));
                    return pedido;
                } else {
                    return null; // Pedido não encontrado
                }
            }
        }
    }

    // Método para atualizar o status de um pedido
    public void updateOrderStatus(int id, String status) throws SQLException {
        String sql = "UPDATE Pedidos SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    // Método para deletar um pedido
    public void deleteOrder(int id) throws SQLException {
        String sql = "DELETE FROM Pedidos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
