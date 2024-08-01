package org.leandro.Serviços;

// ClientService.java
import org.leandro.DAO.ClientDAO;
import org.leandro.models.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private ClientDAO clientDAO;

    public ClientService() {
        this.clientDAO = new ClientDAO();
    }

    public void registerClient(String nome, String cpf, String endereco, String telefone, String email) throws SQLException {
        Cliente cliente = new Cliente(0, nome, cpf, endereco, telefone, email);
        clientDAO.saveClient(cliente);
    }

    public List<Cliente> getAllClients() throws SQLException {
        return clientDAO.getAllClients();
    }

    public Cliente getClientById(int id) throws SQLException {
        return clientDAO.getClientById(id);
    }

    public void updateClient(Cliente cliente) throws SQLException {
        clientDAO.updateClient(cliente);
    }

    public void deleteClient(int id) throws SQLException {
        clientDAO.deleteClient(id);
    }
}

