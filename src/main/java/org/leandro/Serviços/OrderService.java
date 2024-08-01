package org.leandro.Serviços;

// OrderService.java
import org.leandro.DAO.OrderDAO;
import org.leandro.models.Pedido;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public void createOrder(int clienteId, String dataEntrega, String observacao, String status, double total) throws SQLException {
        Pedido pedido = new Pedido(0, clienteId, dataEntrega, observacao, status, total);
        orderDAO.saveOrder(pedido);
    }

    public List<Pedido> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }

    public Pedido getOrderById(int id) throws SQLException {
        return orderDAO.getOrderById(id);
    }

    public void updateOrder(Pedido pedido) throws SQLException {
        orderDAO.updateOrder(pedido);
    }

    public void deleteOrder(int id) throws SQLException {
        orderDAO.deleteOrder(id);
    }
}

