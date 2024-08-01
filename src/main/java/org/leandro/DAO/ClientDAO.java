package org.leandro.DAO;

import org.leandro.models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    private Connection connection;

    public ClientDAO() {
        connection = DatabaseConnection.getConnection();
    }

    // Método para salvar um cliente
    public void saveClient(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (nome, cpf, endereco, telefone, email, data_nascimento, whatsapp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getCpf());
            statement.setString(3, cliente.getEndereco());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getEmail());
            statement.setString(6, cliente.getDataNascimento());
            statement.setBoolean(7, cliente.isWhatsapp());
            statement.executeUpdate();
        }
    }

    // Método para obter todos os clientes
    public List<Cliente> getAllClients() throws SQLException {
        List<Cliente> clients = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Cliente client = new Cliente(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("cpf"),
                        resultSet.getString("endereco"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascimento"),
                        resultSet.getBoolean("whatsapp")
                );
                clients.add(client);
            }
        }
        return clients;
    }

    // Método para obter um cliente por ID
    public Cliente getClientById(int id) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Cliente(
                            resultSet.getInt("id"),
                            resultSet.getString("nome"),
                            resultSet.getString("cpf"),
                            resultSet.getString("endereco"),
                            resultSet.getString("telefone"),
                            resultSet.getString("email"),
                            resultSet.getString("data_nascimento"),
                            resultSet.getBoolean("whatsapp")
                    );
                } else {
                    return null; // Cliente não encontrado
                }
            }
        }
    }

    // Método para atualizar um cliente
    public void updateClient(Cliente cliente) throws SQLException {
        String sql = "UPDATE Clientes SET nome = ?, cpf = ?, endereco = ?, telefone = ?, email = ?, data_nascimento = ?, whatsapp = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getCpf());
            statement.setString(3, cliente.getEndereco());
            statement.setString(4, cliente.getTelefone());
            statement.setString(5, cliente.getEmail());
            statement.setString(6, cliente.getDataNascimento());
            statement.setBoolean(7, cliente.isWhatsapp());
            statement.setInt(8, cliente.getId());
            statement.executeUpdate();
        }
    }
    public Cliente getClientByName(String nome) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Cliente(
                            resultSet.getInt("id"),
                            resultSet.getString("nome"),
                            resultSet.getString("cpf"),
                            resultSet.getString("endereco"),
                            resultSet.getString("telefone"),
                            resultSet.getString("email"),
                            resultSet.getString("data_nascimento"),
                            resultSet.getBoolean("whatsapp")
                    );
                } else {
                    return null; // Cliente não encontrado
                }
            }
        }
    }

    // Método para deletar um cliente
    public void deleteClient(int id) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}

