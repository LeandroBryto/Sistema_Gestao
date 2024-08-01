package org.leandro.Frames;

import org.leandro.DAO.ClientDAO;
import org.leandro.DAO.OrderDAO;
import org.leandro.models.Cliente;
import org.leandro.models.Pedido;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class GenerateOrderFrame extends JFrame {
    private JTextField clientNameField;
    private JTextField deliveryDateField;
    private JTextArea observationArea;
    private JComboBox<String> statusComboBox;
    private JTextField totalField;

    public GenerateOrderFrame() {
        setTitle("Gerar Pedido");
        setSize(400, 400);
        setLayout(null);

        JLabel clientNameLabel = new JLabel("Nome do Cliente:");
        clientNameLabel.setBounds(50, 50, 100, 30);
        add(clientNameLabel);

        clientNameField = new JTextField();
        clientNameField.setBounds(150, 50, 200, 30);
        add(clientNameField);

        JLabel deliveryDateLabel = new JLabel("Data de Entrega:");
        deliveryDateLabel.setBounds(50, 100, 100, 30);
        add(deliveryDateLabel);

        deliveryDateField = new JTextField();
        deliveryDateField.setBounds(150, 100, 200, 30);
        add(deliveryDateField);

        JLabel observationLabel = new JLabel("Observação:");
        observationLabel.setBounds(50, 150, 100, 30);
        add(observationLabel);

        observationArea = new JTextArea();
        observationArea.setBounds(150, 150, 200, 60);
        add(observationArea);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(50, 220, 100, 30);
        add(statusLabel);

        statusComboBox = new JComboBox<>(new String[]{"Recebido", "Em Processamento", "Pronto", "Entregue"});
        statusComboBox.setBounds(150, 220, 200, 30);
        add(statusComboBox);

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setBounds(50, 270, 100, 30);
        add(totalLabel);

        totalField = new JTextField();
        totalField.setBounds(150, 270, 200, 30);
        add(totalField);

        JButton generateOrderButton = new JButton("Gerar Pedido");
        generateOrderButton.setBounds(150, 320, 200, 30);
        add(generateOrderButton);

        generateOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderDAO orderDAO = new OrderDAO();
                ClientDAO clientDAO = new ClientDAO();

                try {
                    // Obtenção e depuração dos campos
                    String clientNameText = clientNameField.getText().trim();
                    String deliveryDateText = deliveryDateField.getText().trim();
                    String observationText = observationArea.getText().trim();
                    String status = (String) statusComboBox.getSelectedItem();
                    String totalText = totalField.getText().trim();

                    // Validação dos campos
                    if (clientNameText.isEmpty() || deliveryDateText.isEmpty() || observationText.isEmpty() || status == null || totalText.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.");
                        return;
                    }

                    // Validação do total
                    double total;
                    try {
                        total = Double.parseDouble(totalText);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Total deve ser um número válido.");
                        return;
                    }

                    // Verificação se o cliente existe pelo nome
                    Cliente cliente = clientDAO.getClientByName(clientNameText);
                    if (cliente == null) {
                        JOptionPane.showMessageDialog(null, "Cliente com o nome fornecido não existe.");
                        return;
                    }

                    // Criação e salvamento do pedido
                    Pedido pedido = new Pedido(cliente.getId(), deliveryDateText, observationText, status, total);
                    orderDAO.saveOrder(pedido);

                    // Gerar e exibir o comprovante
                    generateOrderReceipt(pedido, cliente);

                    JOptionPane.showMessageDialog(null, "Pedido registrado com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao registrar pedido. Verifique a conexão com o banco de dados.");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro inesperado.");
                    ex.printStackTrace();
                }
            }
        });
    }

    // Método para gerar e exibir o comprovante de pedido
    private void generateOrderReceipt(Pedido pedido, Cliente cliente) {
        // Criar o conteúdo do comprovante
        String receipt = String.format(
                "---------------------------------------\n" +
                        "                COMPROVANTE DE PEDIDO\n" +
                        "---------------------------------------\n" +
                        "Cliente: %s\n" +
                        "Pedido ID: %d\n" +
                        "Data de Entrega: %s\n" +
                        "Observação: %s\n" +
                        "Status: %s\n" +
                        "Total: %.2f\n" +
                        "---------------------------------------\n",
                cliente.getNome(),
                pedido.getId(),
                pedido.getDataEntrega(),
                pedido.getObservacao(),
                pedido.getStatus(),
                pedido.getTotal()
        );

        // Exibir o comprovante em uma nova janela
        JTextArea textArea = new JTextArea(receipt);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JFrame receiptFrame = new JFrame("Comprovante de Pedido");
        receiptFrame.setSize(400, 300);
        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        receiptFrame.add(scrollPane);
        receiptFrame.setVisible(true);
    }
}
