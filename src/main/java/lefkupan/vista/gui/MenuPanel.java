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
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton registrar = new JButton("Registrar horas trabajadas");
        JButton pago = new JButton("Ver pago estimado");
        JButton ver = new JButton("Ver ayudantias registradas");
        JButton eliminar = new JButton("Eliminar ayudantia");
        JButton volver = new JButton("Volver");

        registrar.addActionListener(e -> app.mostrar("registrar"));
        pago.addActionListener(e -> app.mostrar("pago"));
        ver.addActionListener(e -> app.mostrar("ver"));
        eliminar.addActionListener(e -> app.mostrar("eliminar"));
        volver.addActionListener(e -> app.mostrar("login"));

        add(registrar);
        add(pago);
        add(ver);
        add(eliminar);
        add(volver);
    }
}
