package lefkupan.vista.gui;

import javax.swing.*;
import java.awt.*;

class LoginPanel extends JPanel {
    private final AppGUI app;
    private JTextField matriculaField;
    private JPasswordField passField;

    LoginPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1.0;

        // etiquetas de matricual
        JLabel matriculaLabel = new JLabel("Ingrese su matrícula");
        matriculaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        matriculaLabel.setForeground(Color.DARK_GRAY);
        c.gridy = 0;
        add(matriculaLabel, c);

        // matricula field
        matriculaField = new JTextField(15);
        matriculaField.setPreferredSize(new Dimension(200, 30));
        GuiUtils.addPlaceholder(matriculaField, "Ej: 202012345");
        c.gridy = 1;
        add(matriculaField, c);

        // etiquetas de contraseña
        JLabel passLabel = new JLabel("Ingrese su contraseña");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setForeground(Color.DARK_GRAY);
        c.gridy = 2;
        add(passLabel, c);

        // contrasñea field
        passField = new JPasswordField(15);
        passField.setPreferredSize(new Dimension(200, 30));
        GuiUtils.addPlaceholder(passField, "Contraseña");
        c.gridy = 3;
        add(passField, c);

        // login
        JButton login = new JButton("Ingresar");
        login.setBackground(new Color(33, 150, 243));
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        login.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        login.setFont(new Font("Segoe UI", Font.BOLD, 14));
        login.addActionListener(e -> app.login(matriculaField.getText(), new String(passField.getPassword())));
        c.gridy = 4;
        add(login, c);
    }
}
