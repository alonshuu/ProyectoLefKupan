package lefkupan.vista.gui;

import javax.swing.*;
import java.awt.*;

class RegistrarHorasPanel extends JPanel {
    private final AppGUI app;
    private JTextField ramoField;
    private JTextField horasField;

    RegistrarHorasPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        ramoField = new JTextField(15);
        horasField = new JTextField(15);
        GuiUtils.addPlaceholder(ramoField, "Nombre del ramo");
        GuiUtils.addPlaceholder(horasField, "Cantidad de horas");

        JButton registrar = new JButton("Registrar");
        registrar.addActionListener(e -> registrarHoras());
        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> app.mostrar("menu"));

        c.gridy = 0;
        add(ramoField, c);
        c.gridy = 1;
        add(horasField, c);
        c.gridy = 2;
        add(registrar, c);
        c.gridy = 3;
        add(volver, c);
    }

    private void registrarHoras() {
        try {
            String ramo = ramoField.getText();
            double horas = Double.parseDouble(horasField.getText());
            app.getControladorHoras().registrarHoras(ramo, horas);
            JOptionPane.showMessageDialog(this, "Horas registradas");
            ramoField.setText("Nombre del ramo");
            horasField.setText("Cantidad de horas");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Horas inválidas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}