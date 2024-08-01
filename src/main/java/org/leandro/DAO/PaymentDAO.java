package org.leandro.DAO;

import org.leandro.models.Pagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection connection;

    public PaymentDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Método para salvar um pagamento
    public void savePayment(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO Pagamentos (pedido_id, metodo_pagamento, valor, data_pagamento) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pagamento.getPedidoId());
            statement.setString(2, pagamento.getMetodoPagamento());
            statement.setDouble(3, pagamento.getValor());
            statement.setString(4, pagamento.getDataPagamento());
            statement.executeUpdate();
        }
    }

    // Método para obter um pagamento por ID
    public Pagamento getPaymentById(int id) throws SQLException {
        String sql = "SELECT * FROM Pagamentos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Pagamento(
                            resultSet.getInt("id"),
                            resultSet.getInt("pedido_id"),
                            resultSet.getString("metodo_pagamento"),
                            resultSet.getDouble("valor"),
                            resultSet.getString("data_pagamento")
                    );
                } else {
                    return null; // Pagamento não encontrado
                }
            }
        }
    }

    // Método para atualizar um pagamento
    public void updatePayment(Pagamento pagamento) throws SQLException {
        String sql = "UPDATE Pagamentos SET pedido_id = ?, metodo_pagamento = ?, valor = ?, data_pagamento = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pagamento.getPedidoId());
            statement.setString(2, pagamento.getMetodoPagamento());
            statement.setDouble(3, pagamento.getValor());
            statement.setString(4, pagamento.getDataPagamento());
            statement.setInt(5, pagamento.getId());
            statement.executeUpdate();
        }
    }

    // Método para deletar um pagamento
    public void deletePayment(int id) throws SQLException {
        String sql = "DELETE FROM Pagamentos WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Método para deletar pagamentos associados a um pedido
    public void deletePaymentsByOrderId(int orderId) throws SQLException {
        String sql = "DELETE FROM Pagamentos WHERE pedido_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }

    // Método para obter todos os pagamentos
    public List<Pagamento> getAllPayments() throws SQLException {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM Pagamentos";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Pagamento pagamento = new Pagamento(
                        resultSet.getInt("id"),
                        resultSet.getInt("pedido_id"),
                        resultSet.getString("metodo_pagamento"),
                        resultSet.getDouble("valor"),
                        resultSet.getString("data_pagamento")
                );
                pagamentos.add(pagamento);
            }
        }
        return pagamentos;
    }
}
