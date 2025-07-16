package lefkupan.gui;

import lefkupan.modelo.usuario.RolUsuario;
import lefkupan.modelo.usuario.Administrador;
import lefkupan.modelo.persistencia.UsuarioJsonRepositorio;
import lefkupan.vista.MenuAdministrador;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MenuAdministradorGUI extends JFrame { //menu principal para administradores del sistema

    private final Administrador administrador;

    public MenuAdministradorGUI(Administrador administrador) {
        this.administrador = administrador;

        setTitle("Lef Küpan - Administrador");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JLabel saludo = new JLabel("Administrador: " + administrador.getId(), SwingConstants.CENTER);
        saludo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton botonRegistrarUsuario = new JButton("Registrar Nuevo Usuario");
        JButton botonVerHistoriales = new JButton("Ver Historiales de Ayudantes");
        JButton botonCerrar = new JButton("Cerrar Sesion");

        botonRegistrarUsuario.addActionListener(e -> registrarNuevoUsuario());
        botonVerHistoriales.addActionListener(e -> mostrarHistoriales());
        botonCerrar.addActionListener(e -> {
            dispose();
            new LoginGUI().setVisible(true);
        });

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(saludo);
        panel.add(botonRegistrarUsuario);
        panel.add(botonVerHistoriales);
        panel.add(botonCerrar);

        getContentPane().add(panel);
    }

    private void registrarNuevoUsuario() {
        JTextField campoMatricula = new JTextField();
        JTextField campoClave = new JTextField();
        JComboBox<String> comboRol = new JComboBox<>(new String[]{"AYUDANTE", "ADMINISTRADOR"});

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Matrícula:"));
        panel.add(campoMatricula);
        panel.add(new JLabel("Contraseña:"));
        panel.add(campoClave);
        panel.add(new JLabel("Rol:"));
        panel.add(comboRol);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar Usuario", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String matricula = campoMatricula.getText().trim();
            String clave = campoClave.getText().trim();
            RolUsuario rol = RolUsuario.valueOf(comboRol.getSelectedItem().toString());

            UsuarioJsonRepositorio.guardarUsuario(matricula, clave, rol);
        }
    }

    private void mostrarHistoriales() {
        File carpeta = new File("data");
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".json"));

        if (archivos == null || archivos.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay historiales disponibles.");
            return;
        }

        StringBuilder resumen = new StringBuilder();

        for (File archivo : archivos) {
            resumen.append("📂 ").append(archivo.getName().replace(".json", "")).append(":\n");
            try {
                Scanner lector = new Scanner(Files.newBufferedReader(Paths.get(archivo.getPath())));
                while (lector.hasNextLine()) {
                    resumen.append("  ").append(lector.nextLine()).append("\n");
                }
            } catch (Exception e) {
                resumen.append("  ❌ Error al leer archivo.\n");
            }
            resumen.append("\n");
        }

        JTextArea area = new JTextArea(resumen.toString());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 400));

        JOptionPane.showMessageDialog(this, scroll, "Historiales de Ayudantes", JOptionPane.INFORMATION_MESSAGE);
    }
}

