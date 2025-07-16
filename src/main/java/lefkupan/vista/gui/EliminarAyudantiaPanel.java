package lefkupan.vista.gui;

import lefkupan.modelo.HistorialTxt;

import javax.swing.*;
import java.awt.*;

class EliminarAyudantiaPanel extends JPanel {
    private final AppGUI app;
    private JPanel listPanel;

    EliminarAyudantiaPanel(AppGUI app) {
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
        app.getAyudante().getAyudantias().forEach(a -> {
            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
            tarjeta.setBackground(new Color(245, 245, 245));
            tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JLabel ramo = new JLabel("📘 Nombre del ramo: " + a.getNombreRamo());
            JLabel horas = new JLabel("⏱ Horas trabajadas: " + a.getTotalHoras());

            JButton eliminar = new JButton("Eliminar ayudantía");
            eliminar.setBackground(new Color(244, 67, 54)); // Rojo suave
            eliminar.setForeground(Color.WHITE);
            eliminar.setFocusPainted(false);
            eliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
            eliminar.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
            eliminar.addActionListener(e -> eliminarAyudantia(a.getNombreRamo()));

            tarjeta.add(ramo);
            tarjeta.add(Box.createVerticalStrut(5));
            tarjeta.add(horas);
            tarjeta.add(Box.createVerticalStrut(10));
            tarjeta.add(eliminar);

            listPanel.add(tarjeta);
            listPanel.add(Box.createVerticalStrut(12));
        });

        revalidate();
        repaint();
    }

    private void eliminarAyudantia(String ramo) {
        boolean ok = app.getAyudante().eliminarAyudantia(ramo);
        if (ok) {
            HistorialTxt.eliminarAyudantiaDelArchivo(app.getAyudante(), ramo);
            JOptionPane.showMessageDialog(this, "Ayudantía eliminada");
            refrescar();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ayudantía", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
