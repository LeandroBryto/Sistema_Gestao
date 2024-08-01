package org.leandro.Serviços;

// PaymentService.java
import org.leandro.DAO.PaymentDAO;
import org.leandro.models.Pagamento;

import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private PaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
    }

    public void registerPayment(int pedidoId, String metodo, double valor) throws SQLException {
        Pagamento pagamento = new Pagamento(0, pedidoId, metodo, valor, new java.util.Date());
        paymentDAO.savePayment(pagamento);
    }

    public List<Pagamento> getAllPayments() throws SQLException {
        return paymentDAO.getAllPayments();
    }

    public Pagamento getPaymentById(int id) throws SQLException {
        return paymentDAO.getPaymentById(id);
    }

    public void updatePayment(Pagamento pagamento) throws SQLException {
        paymentDAO.updatePayment(pagamento);
    }

    public void deletePayment(int id) throws SQLException {
        paymentDAO.deletePayment(id);
    }
}
