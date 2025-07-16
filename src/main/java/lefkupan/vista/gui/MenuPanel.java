package lefkupan.vista.gui;

import javax.swing.*;
import java.awt.*;

class MenuPanel extends JPanel {
    private final AppGUI app;

    MenuPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new GridLayout(8, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JButton registrar = new JButton("Registrar horas trabajadas");
        JButton pago = new JButton("Ver pago estimado");
        JButton resumen = new JButton("Ver resumen de horas");
        JButton historial = new JButton("Ver historial de pagos");
        JButton ver = new JButton("Ver ayudantias registradas");
        JButton eliminarReg = new JButton("Eliminar registro");
        JButton eliminar = new JButton("Eliminar ayudantia");
        JButton volver = new JButton("Volver");

        registrar.addActionListener(e -> app.mostrar("registrar"));
        pago.addActionListener(e -> app.mostrar("pago"));
        resumen.addActionListener(e -> app.mostrar("resumen"));
        historial.addActionListener(e -> app.mostrar("historial"));
        ver.addActionListener(e -> app.mostrar("ver"));
        eliminarReg.addActionListener(e -> app.mostrar("eliminarRegistro"));
        eliminar.addActionListener(e -> app.mostrar("eliminar"));
        volver.addActionListener(e -> app.mostrar("login"));

        add(registrar);
        add(pago);
        add(resumen);
        add(historial);
        add(ver);
        add(eliminarReg);
        add(eliminar);
        add(volver);
    }
}
