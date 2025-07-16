package lefkupan.gui;

import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.dominio.TipoActividad;
import lefkupan.modelo.usuario.Ayudante;
import lefkupan.modelo.persistencia.HistorialJson;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MenuAyudanteGUI extends JFrame {

    private final ControladorHoras controlador;

    public MenuAyudanteGUI(ControladorHoras controlador) {
        this.controlador = controlador;
        Ayudante ayudante = controlador.getAyudante();

        setTitle("Lef Küpan - Menu Ayudante");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel saludo = new JLabel("Bienvenido, " + ayudante.getMatricula(), SwingConstants.CENTER);
        saludo.setFont(new Font("Arial", Font.BOLD, 16));

        JButton botonRegistrar = new JButton("Registrar Horas");
        JButton botonResumen = new JButton("Ver Resumen de Ayudantias");
        JButton botonEliminarAyudantia = new JButton("Eliminar Ayudantia");
        JButton botonEliminarRegistro = new JButton("Eliminar Registro Especifico");
        JButton botonHistorial = new JButton("Ver Historial y Pago");
        JButton botonSalir = new JButton("Cerrar Sesion");

        botonRegistrar.addActionListener(e -> mostrarFormularioRegistro());
        botonResumen.addActionListener(e -> controlador.mostrarDetalleAyudantias());
        botonEliminarAyudantia.addActionListener(e -> eliminarAyudantia());
        botonEliminarRegistro.addActionListener(e -> eliminarRegistroEspecifico());
        botonHistorial.addActionListener(e -> mostrarPagoEstimado());
        botonSalir.addActionListener(e -> {
            dispose();
            new LoginGUI().setVisible(true);
        });

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panel.add(saludo);
        panel.add(botonRegistrar);
        panel.add(botonResumen);
        panel.add(botonEliminarAyudantia);
        panel.add(botonEliminarRegistro);
        panel.add(botonHistorial);
        panel.add(botonSalir);

        getContentPane().add(panel);
        setVisible(true);
    }

    private void mostrarFormularioRegistro() {
        JTextField campoRamo = new JTextField();
        JTextField campoFecha = new JTextField("YYYY-MM-DD");
        JTextField campoHoras = new JTextField();

        TipoActividad[] opciones = TipoActividad.values();
        JComboBox<TipoActividad> comboTipo = new JComboBox<>(opciones);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Ramo:"));
        panel.add(campoRamo);
        panel.add(new JLabel("Fecha:"));
        panel.add(campoFecha);
        panel.add(new JLabel("Horas:"));
        panel.add(campoHoras);
        panel.add(new JLabel("Tipo Actividad:"));
        panel.add(comboTipo);

        int result = JOptionPane.showConfirmDialog(this, panel, "Registrar Horas", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String ramo = campoRamo.getText().trim();
                LocalDate fecha = LocalDate.parse(campoFecha.getText().trim());
                double horas = Double.parseDouble(campoHoras.getText().trim());
                TipoActividad tipo = (TipoActividad) comboTipo.getSelectedItem();

                controlador.registrarHoras(ramo, fecha, horas, tipo);
                JOptionPane.showMessageDialog(this, "Registro guardado");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados");
            }
        }
    }

    private void eliminarAyudantia() {
        String ramo = JOptionPane.showInputDialog(this, "Ingrese nombre del ramo a eliminar: ");
        if (ramo != null && !ramo.isBlank()) {
            boolean ok = controlador.eliminarAyudantia(ramo);
            JOptionPane.showMessageDialog(this,
                    ok ? "Ayudantia eliminada" : "No se encontro el ramo");
        }
    }

    private void eliminarRegistroEspecifico() {
        JOptionPane.showMessageDialog(this, "Esta funcion requiere implementacion más detallada en GUI.\nUsa la versión por consola o espera siguiente iteracion.");
    }

    private void mostrarPagoEstimado() {
        String input = JOptionPane.showInputDialog(this, "Ingrese valor por hora: ");
        if (input == null || input.isBlank()) return;

        try {
            double valor = Double.parseDouble(input);
            HistorialJson.mostrarHistorialPagos(controlador.getAyudante(), valor);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor no valido");
        }
    }
}
