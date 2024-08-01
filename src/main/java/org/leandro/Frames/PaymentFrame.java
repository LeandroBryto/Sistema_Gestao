package org.leandro.Frames;

import org.leandro.DAO.ClientDAO;
import org.leandro.DAO.OrderDAO;
import org.leandro.DAO.PaymentDAO;
import org.leandro.models.Cliente;
import org.leandro.models.Pagamento;
import org.leandro.models.Pedido;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class PaymentFrame extends JFrame {
    private JTextField clientNameField;
    private JTextField paymentMethodField;
    private JTextField amountField;
    private JTextField paymentDateField;

    public PaymentFrame() {
        setTitle("Registrar Pagamento");
        setSize(400, 300);
        setLayout(null);

        JLabel clientNameLabel = new JLabel("Nome do Cliente:");
        clientNameLabel.setBounds(50, 50, 100, 30);
        add(clientNameLabel);

        clientNameField = new JTextField();
        clientNameField.setBounds(150, 50, 200, 30);
        add(clientNameField);

        JLabel paymentMethodLabel = new JLabel("Método de Pagamento:");
        paymentMethodLabel.setBounds(50, 100, 150, 30);
        add(paymentMethodLabel);

        paymentMethodField = new JTextField();
        paymentMethodField.setBounds(200, 100, 150, 30);
        add(paymentMethodField);

        JLabel amountLabel = new JLabel("Valor:");
        amountLabel.setBounds(50, 150, 100, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(150, 150, 200, 30);
        add(amountField);

        JLabel paymentDateLabel = new JLabel("Data de Pagamento:");
        paymentDateLabel.setBounds(50, 200, 150, 30);
        add(paymentDateLabel);

        paymentDateField = new JTextField();
        paymentDateField.setBounds(200, 200, 150, 30);
        add(paymentDateField);

        JButton registerPaymentButton = new JButton("Registrar Pagamento");
        registerPaymentButton.setBounds(150, 250, 200, 30);
        add(registerPaymentButton);

        registerPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDAO clientDAO = new ClientDAO();
                OrderDAO orderDAO = new OrderDAO();
                PaymentDAO paymentDAO = new PaymentDAO();

                try {
                    // Obter os valores dos campos
                    String clientName = clientNameField.getText().trim();
                    String paymentMethod = paymentMethodField.getText().trim();
                    String amountText = amountField.getText().trim();
                    String paymentDate = paymentDateField.getText().trim();

                    // Validar os campos
                    if (clientName.isEmpty() || paymentMethod.isEmpty() || amountText.isEmpty() || paymentDate.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
                        return;
                    }

                    // Validar o valor
                    double amount;
                    try {
                        amount = Double.parseDouble(amountText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valor deve ser um número válido.");
                        return;
                    }

                    // Obter o cliente pelo nome
                    Cliente cliente = clientDAO.getClientByName(clientName);
                    if (cliente == null) {
                        JOptionPane.showMessageDialog(null, "Cliente com o nome fornecido não existe.");
                        return;
                    }

                    // Obter o pedido do cliente
                    Pedido pedido = orderDAO.getOrderByClientId(cliente.getId());
                    if (pedido == null) {
                        JOptionPane.showMessageDialog(null, "Pedido do cliente não encontrado.");
                        return;
                    }

                    // Registrar o pagamento
                    Pagamento pagamento = new Pagamento(pedido.getId(), paymentMethod, amount, paymentDate);
                    paymentDAO.savePayment(pagamento);

                    // Atualizar o status do pedido para 'Pago'
                    orderDAO.updateOrderStatus(pedido.getId(), "Pago");

                    // Gerar e exibir o comprovante de pagamento
                    generatePaymentReceipt(pagamento, cliente, pedido);

                    JOptionPane.showMessageDialog(null, "Pagamento registrado com sucesso!");

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao registrar pagamento. Verifique a conexão com o banco de dados.");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado.");
                    ex.printStackTrace();
                }
            }
        });
    }

    private void generatePaymentReceipt(Pagamento pagamento, Cliente cliente, Pedido pedido) {
        String receipt = String.format(
                "---------------------------------------\n" +
                        "            RS LAVANDERIA                        \n"+
                        "         COMPROVANTE DE PAGAMENTO\n" +
                        "---------------------------------------\n" +
                        "Cliente: %s\n" +
                        "CPF: %s\n" +
                        "Endereço: %s\n" +
                        "Pedido ID: %d\n" +
                        "Data de Entrega: %s\n" +
                        "Observação: %s\n" +
                        "Status do Pedido: %s\n" +
                        "Método de Pagamento: %s\n" +
                        "Valor: %.2f\n" +
                        "Data de Pagamento: %s\n" +
                        "---------------------------------------\n",
                cliente.getNome(),
                cliente.getCpf(),  // Supondo que você tenha um método getCpf()
                cliente.getEndereco(),  // Supondo que você tenha um método getEndereco()
                pedido.getId(),
                pedido.getDataEntrega(),
                pedido.getObservacao(),
                pedido.getStatus(),
                pagamento.getMetodoPagamento(),
                pagamento.getValor(),
                pagamento.getDataPagamento()
        );

        JTextArea textArea = new JTextArea(receipt);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame receiptFrame = new JFrame("Comprovante de Pagamento");
        receiptFrame.setSize(400, 400);
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        receiptFrame.add(scrollPane);
        receiptFrame.setVisible(true);
    }
}
