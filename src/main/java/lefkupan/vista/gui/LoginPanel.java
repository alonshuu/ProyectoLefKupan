package lefkupan.vista.gui;

import javax.swing.*;
import java.awt.*;

class LoginPanel extends JPanel {
    private final AppGUI app;
    private JTextField matriculaField;
    private JTextField passField;

    LoginPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        matriculaField = new JTextField(15);
        passField = new JTextField(15);
        GuiUtils.addPlaceholder(matriculaField, "Ingrese su matricula");
        GuiUtils.addPlaceholder(passField, "Ingrese su contraseña");

        JButton login = new JButton("Ingresar");
        login.addActionListener(e -> app.login(matriculaField.getText(), passField.getText()));

        c.gridy = 0;
        add(matriculaField, c);
        c.gridy = 1;
        add(passField, c);
        c.gridy = 2;
        add(login, c);
    }
}
