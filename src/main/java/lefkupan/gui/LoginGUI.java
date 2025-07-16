package lefkupan.gui;

import lefkupan.controlador.ControladorHoras;
import lefkupan.controlador.ControladorLogin;
import lefkupan.modelo.usuario.Ayudante;
import lefkupan.modelo.usuario.Administrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI { //ventana de inicio de sesion para el sistema
    private JTextField campoMatricula;
    private JPasswordField campoContrasena;

    public LoginGUI() {
        setTitle("Lef Küpan - Login");
        setDefaultCloseOperacion(EXIT_ON_CLOSE);
        setSize(350,220);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(4,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        campoMatricula = new JTextField();
        campoContrasena = new JPasswordField();

        JButton botonLogin = new JButton("Inicio Sesion");
        botonLogin.addActionListener(new LoginListener());

        panel.add(new JLabel("Matrícula:"));
        panel.add(campoMatricula);
        panel.add(new JLabel("Contraseña:"));
        panel.add(campoContrasena);
        panel.add(botonLogin);

        getContentPane().add(panel);
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String matricula = campoMatricula.getText().trim();
            String clave = new String(campoContrasena.getPassword()).trim();

            Object usuario = ControladorLogin.autenticar(matricula, contrasena);

            if (usuario instanceof Ayudante ayudante) {// Abrir menú ayudante
                dispose();
                new MenuAyudanteGUI(new ControladorHoras(ayudante));
            } else if (usuario instanceof Administrador administrador) {
                // Abrir menú administrador
                dispose();
                new MenuAdministradorGUI(administrador);
            } else {
                JOptionPane.showMessageDialog(LoginGUI.this,
                        "Credenciales incorrectas",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }


}
