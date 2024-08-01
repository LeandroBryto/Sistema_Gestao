package org.leandro.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("RS Lavanderia");
        setSize(800, 600); // Tamanho aumentado para melhor visualização
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de Cabeçalho com Nome
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(33, 150, 243)); // Azul claro

        // Adiciona nome do aplicativo
        JLabel titleLabel = new JLabel("RS Lavanderia", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Painel Central para os Botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // Usar GridBagLayout para uma organização mais flexível
        buttonPanel.setBackground(new Color(245, 245, 245)); // Cor de fundo suave
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento entre os botões
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Adicionar botões ao painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(createStyledButton("Registrar Cliente", "icons/register.png"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(createStyledButton("Gerar Pedido", "icons/order.png"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(createStyledButton("Ver Pedidos", "icons/view.png"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        buttonPanel.add(createStyledButton("Registrar Pagamento", "icons/payment.png"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(createStyledButton("Relatórios", "icons/report.png"), gbc);

        // Adicionar painel ao frame
        add(buttonPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                if (source.getText().equals("Registrar Cliente")) {
                    new RegisterClientFrame().setVisible(true);
                } else if (source.getText().equals("Gerar Pedido")) {
                    new GenerateOrderFrame().setVisible(true);
                } else if (source.getText().equals("Ver Pedidos")) {
                    new OrdersFrame().setVisible(true);
                } else if (source.getText().equals("Registrar Pagamento")) {
                    new PaymentFrame().setVisible(true);
                } else if (source.getText().equals("Relatórios")) {
                    new ReportFrame().setVisible(true);
                }
            }
        };

        // Attach ActionListener to buttons
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                ((JButton) component).addActionListener(buttonListener);
            }
        }
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(iconPath)); // Adiciona um ícone, se disponível
        button.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Fonte aumentada para melhor visibilidade
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(33, 150, 243)); // Azul claro
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito de borda arredondada e sombra sutil
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(button.getBackground());
                g2d.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), 12, 12);
                g2d.setColor(new Color(0, 0, 0, 20)); // Sombra sutil
                g2d.fillRoundRect(4, 4, button.getWidth() - 8, button.getHeight() - 8, 12, 12);
                super.paint(g, c);
            }
        });

        // Efeito ao passar o mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 136, 229)); // Azul mais escuro no hover
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 150, 243)); // Reset para a cor original
            }
        });

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
