
package lefkupan.gui;

import lefkupan.controlador.ControladorHoras;
import lefkupan.modelo.dominio.TipoActividad;
import lefkupan.modelo.dominio.Ayudantia;
import lefkupan.modelo.dominio.RegistroHoras;
import lefkupan.modelo.usuario.Ayudante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class MenuAyudanteGUI extends JFrame {

    private final ControladorHoras controlador;

    public MenuAyudanteGUI(ControladorHoras controlador) {
        this.controlador = controlador;
        Ayudante ayudante = controlador.getAyudante();

        setTitle("Menu Ayudante");
        setSize(450, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel saludo = new JLabel("Bienvenido " + ayudante.getMatricula(), SwingConstants.CENTER);
        saludo.setFont(new Font("Arial", Font.BOLD, 16));
        saludo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(saludo);
        panel.add(Box.createVerticalStrut(20));

        String[] acciones = {
                "Registrar horas trabajadas",
                "Ver pago estimado",
                "Ver resumen de horas",
                "Ver historial de pagos",
                "Ver ayudantias registradas",
                "Eliminar registro",
                "Eliminar ayudantia",
                "Cerrar sesion"
        };

        for (String texto : acciones) {
            JButton boton = new JButton(texto);
            boton.setFocusPainted(false);
            boton.setBackground(new Color(30, 144, 255));
            boton.setForeground(Color.WHITE);
            boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(boton);
            panel.add(Box.createVerticalStrut(10));

            switch (texto) {
                case "Registrar horas trabajadas" -> boton.addActionListener(e -> mostrarFormularioRegistro());
                case "Ver resumen de horas" -> boton.addActionListener(e -> mostrarResumenAyudantias());
                case "Eliminar ayudantia" -> boton.addActionListener(e -> eliminarAyudantia());
                case "Eliminar registro" -> boton.addActionListener(e -> eliminarRegistroEspecifico());
                case "Ver ayudantias registradas" -> boton.addActionListener(e -> new HistorialTablaGUI(controlador.getAyudante()));
                case "Cerrar sesion" -> boton.addActionListener(e -> {
                    dispose();
                    new LoginGUI().setVisible(true);
                });
            }
        }

        getContentPane().add(panel);
        setVisible(true);
    }

    private void mostrarFormularioRegistro() {
        JTextField campoRamo = new JTextField();
        JTextField campoFecha = new JTextField("YYYY-MM-DD");
        JTextField campoHoras = new JTextField();

        TipoActividad[] opciones = TipoActividad.values();
        JComboBox<TipoActividad> comboTipo = new JComboBox<>(opciones);

        campoRamo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoFecha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campoHoras.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        comboTipo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new JLabel("Nombre del ramo"));
        panel.add(campoRamo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Fecha"));
        panel.add(campoFecha);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Cantidad de horas"));
        panel.add(campoHoras);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Tipo de actividad"));
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

    private void mostrarResumenAyudantias() {
        StringBuilder sb = new StringBuilder();

        if (controlador.getAyudante().getAyudantias().isEmpty()) {
            sb.append("No hay ayudantias registradas");
        } else {
            for (Ayudantia a : controlador.getAyudante().getAyudantias()) {
                sb.append("Ramo: ").append(a.getNombreRamo()).append("\n");
                sb.append("Total horas: ").append(a.getTotalHoras()).append("\n");
                sb.append("Registros: ").append(a.getRegistrosHoras().size()).append("\n\n");
            }
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(this, scroll, "Resumen de Ayudantias", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarAyudantia() {
        String ramo = JOptionPane.showInputDialog(this, "Ingrese nombre del ramo a eliminar");
        if (ramo != null && !ramo.isBlank()) {
            boolean ok = controlador.eliminarAyudantia(ramo);
            JOptionPane.showMessageDialog(this,
                    ok ? "Ayudantia eliminada" : "No se encontro el ramo");
        }
    }

    private void eliminarRegistroEspecifico() {
        var ayudantias = controlador.getAyudante().getAyudantias();
        if (ayudantias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ayudantias registradas");
            return;
        }

        String[] nombres = ayudantias.stream().map(Ayudantia::getNombreRamo).toArray(String[]::new);
        String seleccion = (String) JOptionPane.showInputDialog(
                this, "Seleccione ayudantia", "Eliminar Registro",
                JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]
        );

        if (seleccion == null) return;

        var ayudantia = ayudantias.stream()
                .filter(a -> a.getNombreRamo().equals(seleccion))
                .findFirst().orElse(null);

        if (ayudantia == null || ayudantia.getRegistrosHoras().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Esta ayudantia no tiene registros");
            return;
        }

        DefaultTableModel modelo = new DefaultTableModel(new String[]{"Fecha", "Horas", "Tipo"}, 0);
        List<RegistroHoras> registros = ayudantia.getRegistrosHoras();

        registros.forEach(r -> modelo.addRow(new Object[]{r.getFecha(), r.getCantidad(), r.getTipoActividad()}));

        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(500, 200));

        int opcion = JOptionPane.showConfirmDialog(
                this, scroll, "Seleccione registro a eliminar", JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion == JOptionPane.OK_OPTION) {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                RegistroHoras registro = registros.get(fila);
                boolean ok = controlador.eliminarRegistroEspecifico(ayudantia.getNombreRamo(), registro);
                JOptionPane.showMessageDialog(this, ok ? "Registro eliminado" : "No se pudo eliminar");
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una fila");
            }
        }
    }
}
