
package lefkupan.gui;

import lefkupan.controlador.ControladorLogin;
import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.usuario.Ayudante;
import lefkupan.modelo.usuario.Administrador;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    public LoginGUI() {
        setTitle("Lef Kupan - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel labelUsuario = new JLabel("Ingrese su matricula");
        JTextField campoUsuario = new JTextField();
        campoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel labelContrasena = new JLabel("Ingrese su contrasena");
        JPasswordField campoContrasena = new JPasswordField();
        campoContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton botonIngresar = new JButton("Ingresar");
        botonIngresar.setFocusPainted(false);
        botonIngresar.setBackground(new Color(30, 144, 255));
        botonIngresar.setForeground(Color.WHITE);
        botonIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        botonIngresar.addActionListener(e -> {
            String usuario = campoUsuario.getText().trim();
            String contrasena = new String(campoContrasena.getPassword()).trim();

            var resultado = new ControladorLogin().autenticar(usuario, contrasena);

            if (resultado instanceof Ayudante ayudante) {
                ControladorHoras controlador = new ControladorHoras(ayudante);
                dispose();
                new MenuAyudanteGUI(controlador);
            } else if (resultado instanceof Administrador administrador) {
                dispose();
                new MenuAdministradorGUI(administrador);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contrasena incorrectos");
            }
        });

        panel.add(labelUsuario);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campoUsuario);
        panel.add(Box.createVerticalStrut(15));
        panel.add(labelContrasena);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campoContrasena);
        panel.add(Box.createVerticalStrut(25));
        panel.add(botonIngresar);

        getContentPane().add(panel);
    }
}
