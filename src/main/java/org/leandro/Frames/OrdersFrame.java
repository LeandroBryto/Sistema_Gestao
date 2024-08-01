package org.leandro.Frames;

import org.leandro.DAO.ClientDAO;
import org.leandro.DAO.OrderDAO;
import org.leandro.DAO.PaymentDAO;
import org.leandro.models.Cliente;
import org.leandro.models.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class OrdersFrame extends JFrame {
    private JTable ordersTable;
    private JButton deleteButton;
    private String[] columnNames = {"ID", "Nome do Cliente", "Data de Entrega", "Observação", "Status", "Total"};

    public OrdersFrame() {
        setTitle("Visualizar Pedidos");
        setSize(800, 600);
        setLayout(new BorderLayout());

        ordersTable = new JTable();
        add(new JScrollPane(ordersTable), BorderLayout.CENTER);

        // Botão para deletar pedido
        deleteButton = new JButton("Deletar Pedido");
        add(deleteButton, BorderLayout.SOUTH);

        // Adicionar listener para o botão
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ordersTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int orderId = Integer.parseInt(ordersTable.getValueAt(selectedRow, 0).toString());
                    deleteOrder(orderId);
                } else {
                    JOptionPane.showMessageDialog(OrdersFrame.this, "Selecione um pedido para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        loadOrders();
    }

    private void loadOrders() {
        try {
            OrderDAO orderDAO = new OrderDAO();
            ClientDAO clientDAO = new ClientDAO();
            List<Pedido> orders = orderDAO.getAllOrders();
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

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            ordersTable.setModel(model);

            // Aplicar o renderer personalizado para a coluna "Status"
            ordersTable.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar pedidos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder(int orderId) {
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza de que deseja deletar este pedido?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                OrderDAO orderDAO = new OrderDAO();
                PaymentDAO paymentDAO = new PaymentDAO();

                // Deletar pagamentos associados
                paymentDAO.deletePaymentsByOrderId(orderId);

                // Deletar o pedido
                orderDAO.deleteOrder(orderId);
                JOptionPane.showMessageDialog(this, "Pedido deletado com sucesso!");
                loadOrders(); // Recarregar a lista de pedidos
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao deletar pedido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Renderer personalizado para a coluna Status
    class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) value;

            switch (status) {
                case "Em Processamento":
                    cell.setBackground(Color.YELLOW);
                    break;
                case "Pago":
                    cell.setBackground(Color.GREEN);
                    break;
                case "Pronto":
                    cell.setBackground(Color.BLUE);
                    break;
                case "Recebido":
                    cell.setBackground(Color.ORANGE);
                    break;
                case "Entregue":
                    cell.setBackground(Color.GREEN);
                    break;
                default:
                    cell.setBackground(Color.WHITE);
                    break;
            }

            // Mantém a cor de fundo padrão para linhas selecionadas
            if (isSelected) {
                cell.setBackground(cell.getBackground().darker());
            }

            return cell;
        }
    }
}
