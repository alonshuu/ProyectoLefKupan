
package lefkupan.gui;

import lefkupan.modelo.dominio.Ayudantia;
import lefkupan.modelo.dominio.RegistroHoras;
import lefkupan.modelo.usuario.Ayudante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistorialTablaGUI extends JFrame {

    public HistorialTablaGUI(Ayudante ayudante) {
        setTitle("Historial de Ayudantias");
        setSize(650, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] columnas = {"Ramo", "Fecha", "Cantidad", "Tipo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Ayudantia a : ayudante.getAyudantias()) {
            for (RegistroHoras r : a.getRegistrosHoras()) {
                modelo.addRow(new Object[]{
                        a.getNombreRamo(),
                        r.getFecha(),
                        r.getCantidad(),
                        r.getTipoActividad()
                });
            }
        }

        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scroll, BorderLayout.CENTER);
        setVisible(true);
    }
}

