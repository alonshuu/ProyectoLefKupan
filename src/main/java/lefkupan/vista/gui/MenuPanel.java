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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        String[] labels = {
                "Registrar horas trabajadas",
                "Ver pago estimado",
                "Ver resumen de horas",
                "Ver historial de pagos",
                "Ver ayudantías registradas",
                "Eliminar registro",
                "Eliminar ayudantía",
                "Cerrar sesión"
        };

        String[] targets = {
                "registrar", "pago", "resumen", "historial",
                "ver", "eliminarRegistro", "eliminar", "login"
        };

        for (int i = 0; i < labels.length; i++) {
            JButton button = crearBoton(labels[i]);
            String destino = targets[i];
            button.addActionListener(e -> app.mostrar(destino));
            add(button);
            add(Box.createVerticalStrut(12));
        }
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(280, 40));
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setBackground(new Color(33, 150, 243));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }
}

