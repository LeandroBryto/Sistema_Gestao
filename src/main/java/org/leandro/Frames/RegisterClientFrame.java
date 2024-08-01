package org.leandro.Frames;



import org.leandro.DAO.ClientDAO;
import org.leandro.models.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterClientFrame extends JFrame {
    private JTextField nameField;
    private JTextField cpfField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField dobField;
    private JCheckBox whatsappCheckBox;

    public RegisterClientFrame() {
        setTitle("Registrar Cliente");
        setSize(400, 400);
        setLayout(null);

        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setBounds(50, 50, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        add(nameField);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(50, 100, 100, 30);
        add(cpfLabel);

        cpfField = new JTextField();
        cpfField.setBounds(150, 100, 200, 30);
        add(cpfField);

        JLabel addressLabel = new JLabel("Endereço:");
        addressLabel.setBounds(50, 150, 100, 30);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(150, 150, 200, 30);
        add(addressField);

        JLabel phoneLabel = new JLabel("Telefone:");
        phoneLabel.setBounds(50, 200, 100, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(150, 200, 200, 30);
        add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 250, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 250, 200, 30);
        add(emailField);

        JLabel dobLabel = new JLabel("Data de Nascimento:");
        dobLabel.setBounds(50, 300, 150, 30);
        add(dobLabel);

        dobField = new JTextField();
        dobField.setBounds(200, 300, 150, 30);
        add(dobField);

        JLabel whatsappLabel = new JLabel("WhatsApp:");
        whatsappLabel.setBounds(50, 350, 100, 30);
        add(whatsappLabel);

        whatsappCheckBox = new JCheckBox();
        whatsappCheckBox.setBounds(150, 350, 30, 30);
        add(whatsappCheckBox);

        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(150, 400, 100, 30);
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientDAO clientDAO = new ClientDAO();
                try {
                    Cliente client = new Cliente(
                            nameField.getText(),
                            cpfField.getText(),
                            addressField.getText(),
                            phoneField.getText(),
                            whatsappCheckBox.isSelected(),
                            emailField.getText(),
                            dobField.getText()
                    );
                    clientDAO.saveClient(client);
                    JOptionPane.showMessageDialog(null, "Cliente registrado com sucesso!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao registrar cliente!");
                }
            }
        });
    }
}
