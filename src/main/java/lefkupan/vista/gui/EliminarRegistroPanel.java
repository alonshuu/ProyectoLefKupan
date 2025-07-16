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
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(listPanel);

        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> app.mostrar("menu"));

        add(scroll, BorderLayout.CENTER);
        add(volver, BorderLayout.SOUTH);
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
                JPanel p = new JPanel();
                p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                p.add(new JLabel("Ramo: " + a.getNombreRamo()));
                p.add(new JLabel("Fecha: " + r.getFecha()));
                p.add(new JLabel("Horas: " + r.getCantidad()));
                JButton eliminar = new JButton("Eliminar");
                eliminar.addActionListener(e -> eliminarRegistro(a, r));
                p.add(eliminar);
                listPanel.add(p);
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
