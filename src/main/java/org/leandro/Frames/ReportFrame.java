package org.leandro.Frames;

import org.leandro.DAO.ClientDAO;
import org.leandro.DAO.OrderDAO;
import org.leandro.models.Cliente;
import org.leandro.models.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ReportFrame extends JFrame {

    private ClientDAO clientDAO;

    public ReportFrame() {
        clientDAO = new ClientDAO();
        setTitle("Relatórios");
        setSize(1000, 700);
        setLayout(new BorderLayout());

        // Cabeçalho com Título
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 144, 255)); // Azul Royal
        headerPanel.setLayout(new BorderLayout());

        JLabel reportLabel = new JLabel("Relatórios", JLabel.CENTER);
        reportLabel.setFont(new Font("Arial", Font.BOLD, 24));
        reportLabel.setForeground(Color.WHITE);
        headerPanel.add(reportLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Painel com Botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton generateOrderReportButton = new JButton("Gerar Relatório de Pedidos");
        JButton generateFinancialReportButton = new JButton("Gerar Relatório Financeiro");
        JButton generateClientReportButton = new JButton("Gerar Relatório de Clientes");

        buttonPanel.add(generateOrderReportButton);
        buttonPanel.add(generateFinancialReportButton);
        buttonPanel.add(generateClientReportButton);

        add(buttonPanel, BorderLayout.WEST);

        // Action Listeners para os botões
        generateOrderReportButton.addActionListener(e -> generateOrderReport());
        generateFinancialReportButton.addActionListener(e -> generateFinancialReport());
        generateClientReportButton.addActionListener(e -> generateClientReport());
    }

    private void generateOrderReport() {
        try {
            OrderDAO orderDAO = new OrderDAO();
            List<Pedido> orders = orderDAO.getAllOrders();

            String[] columnNames = {"ID", "Nome do Cliente", "Data de Entrega", "Observação", "Status", "Total"};
            String[][] data = new String[orders.size()][columnNames.length];

            for (int i = 0; i < orders.size(); i++) {
                Pedido order = orders.get(i);
                Cliente cliente = clientDAO.getClientById(order.getClienteId());
                String clienteNome = cliente != null ? cliente.getNome() : "Desconhecido";

                data[i][0] = String.valueOf(order.getId());
                data[i][1] = clienteNome;
                data[i][2] = order.getDataEntrega();
                data[i][3] = order.getObservacao();
                data[i][4] = order.getStatus();
                data[i][5] = String.valueOf(order.getTotal());
            }

            JTable table = new JTable(new DefaultTableModel(data, columnNames));
            table.setFont(new Font("Arial", Font.PLAIN, 14));
            table.setRowHeight(30);
            table.setGridColor(Color.LIGHT_GRAY);
            table.setShowGrid(true);
            table.setSelectionBackground(new Color(173, 216, 230)); // Light Blue

            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("Relatório de Pedidos");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório de pedidos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateFinancialReport() {
        try {
            OrderDAO orderDAO = new OrderDAO();
            List<Pedido> orders = orderDAO.getAllOrders();

            double totalRevenue = 0.0;
            double totalExpenses = 0.0;

            for (Pedido order : orders) {
                totalRevenue += order.getTotal();
                // Adicione lógica para calcular despesas se houver
            }

            String report = String.format("Relatório Financeiro\n\nReceita Total: %.2f\nDespesas Totais: %.2f\nSaldo: %.2f",
                    totalRevenue, totalExpenses, totalRevenue - totalExpenses);

            JTextArea textArea = new JTextArea(report);
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));

            JFrame frame = new JFrame("Relatório Financeiro");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new JScrollPane(textArea));
            frame.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório financeiro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateClientReport() {
        try {
            List<Cliente> clients = clientDAO.getAllClients();

            String[] columnNames = {"ID", "Nome", "CPF", "Endereço", "Telefone", "Email", "Ações"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(model);

            table.getColumn("Ações").setCellRenderer(new ButtonRenderer());
            table.getColumn("Ações").setCellEditor(new ButtonEditor(new JCheckBox()));

            for (Cliente client : clients) {
                model.addRow(new Object[]{
                        client.getId(),
                        client.getNome(),
                        client.getCpf(),
                        client.getEndereco(),
                        client.getTelefone(),
                        client.getEmail(),
                        "Deletar"
                });
            }

            JScrollPane scrollPane = new JScrollPane(table);
            JFrame frame = new JFrame("Relatório de Clientes");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(scrollPane);
            frame.setVisible(true);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório de clientes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Renderizador para o botão
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Deletar");
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor para o botão
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int row;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setText("Deletar");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirmation = JOptionPane.showConfirmDialog(button, "Deseja realmente deletar este cliente?", "Confirmar Deleção", JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        try {
                            int clientId = (int) table.getValueAt(row, 0);
                            clientDAO.deleteClient(clientId);
                            ((DefaultTableModel) table.getModel()).removeRow(row);
                            JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!");
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao deletar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            button.setText((value == null) ? "" : value.toString());
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }
    }
}
