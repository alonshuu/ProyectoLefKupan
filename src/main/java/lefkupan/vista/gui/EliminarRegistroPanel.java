package lefkupan.vista.gui;

import lefkupan.modelo.Ayudantia;
import lefkupan.modelo.HistorialTxt;
import lefkupan.modelo.RegistroHoras;

import javax.swing.*;
import java.awt.*;

class EliminarRegistroPanel extends JPanel {
    private final AppGUI app;
    private JPanel listPanel;

    EliminarRegistroPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        // Botón volver
        JButton volver = new JButton("Volver");
        volver.setBackground(new Color(200, 200, 200));
        volver.setForeground(Color.BLACK);
        volver.setFocusPainted(false);
        volver.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        volver.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        volver.addActionListener(e -> app.mostrar("menu"));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(Color.WHITE);
        bottom.add(volver);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (aFlag) refrescar();
        super.setVisible(aFlag);
    }

    private void refrescar() {
        listPanel.removeAll();
        for (Ayudantia a : app.getAyudante().getAyudantias()) {
            for (RegistroHoras r : a.getRegistrosHoras()) {
                JPanel tarjeta = new JPanel();
                tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
                tarjeta.setBackground(new Color(245, 245, 245));
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(210, 210, 210)),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));

                JLabel ramo = new JLabel("📘 Ramo: " + a.getNombreRamo());
                JLabel fecha = new JLabel("📅 Fecha: " + r.getFecha());
                JLabel horas = new JLabel("⏱ Horas: " + r.getCantidad());

                JButton eliminar = new JButton("Eliminar registro");
                eliminar.setBackground(new Color(244, 67, 54)); // rojo
                eliminar.setForeground(Color.WHITE);
                eliminar.setFocusPainted(false);
                eliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
                eliminar.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
                eliminar.addActionListener(e -> eliminarRegistro(a, r));

                tarjeta.add(ramo);
                tarjeta.add(Box.createVerticalStrut(4));
                tarjeta.add(fecha);
                tarjeta.add(Box.createVerticalStrut(4));
                tarjeta.add(horas);
                tarjeta.add(Box.createVerticalStrut(8));
                tarjeta.add(eliminar);

                listPanel.add(tarjeta);
                listPanel.add(Box.createVerticalStrut(12));
            }
        }

        revalidate();
        repaint();
    }

    private void eliminarRegistro(Ayudantia a, RegistroHoras r) {
        if (a.eliminarRegistro(r.getFecha(), r.getCantidad())) {
            app.getAyudante().recalcularHorasTotales();
            HistorialTxt.eliminarRegistroDelArchivo(app.getAyudante(), a.getNombreRamo(), r);
            JOptionPane.showMessageDialog(this, "Registro eliminado");
            refrescar();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró registro", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

