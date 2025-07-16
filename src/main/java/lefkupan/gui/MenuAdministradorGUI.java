package lefkupan.gui;

import lefkupan.modelo.usuario.Administrador;
import lefkupan.vista.MenuAdministrador;

import javax.swing.*;
import java.awt.*;

public class MenuAdministradorGUI extends JFrame {

    public MenuAdministradorGUI(Administrador administrador) {
        setTitle("Lef Kupan - Menu Administrador");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel saludo = new JLabel("Bienvenido administrador " + administrador.getMatricula(), SwingConstants.CENTER);
        saludo.setFont(new Font("Arial", Font.BOLD, 14));

        JButton botonCerrar = new JButton("Cerrar sesion");
        botonCerrar.addActionListener(e -> {
            dispose();
            new LoginGUI().setVisible(true);
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        panel.add(saludo, BorderLayout.CENTER);
        panel.add(botonCerrar, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);
    }
}

