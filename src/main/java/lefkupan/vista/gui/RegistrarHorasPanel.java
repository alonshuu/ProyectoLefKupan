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
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 0, 10, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1.0;


        JLabel ramoLabel = new JLabel("Nombre del ramo");
        ramoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ramoLabel.setForeground(Color.DARK_GRAY);
        c.gridy = 0;
        add(ramoLabel, c);

        ramoField = new JTextField(15);
        ramoField.setPreferredSize(new Dimension(200, 30));
        GuiUtils.addPlaceholder(ramoField, "Ej: Cálculo 2");
        c.gridy = 1;
        add(ramoField, c);


        JLabel horasLabel = new JLabel("Cantidad de horas");
        horasLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        horasLabel.setForeground(Color.DARK_GRAY);
        c.gridy = 2;
        add(horasLabel, c);

        horasField = new JTextField(15);
        horasField.setPreferredSize(new Dimension(200, 30));
        GuiUtils.addPlaceholder(horasField, "Ej: 3.5");
        c.gridy = 3;
        add(horasField, c);


        JButton registrar = new JButton("Registrar");
        registrar.setBackground(new Color(33, 150, 243));
        registrar.setForeground(Color.WHITE);
        registrar.setFocusPainted(false);
        registrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registrar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registrar.addActionListener(e -> registrarHoras());
        c.gridy = 4;
        add(registrar, c);


        JButton volver = new JButton("Volver");
        volver.setBackground(new Color(200, 200, 200));
        volver.setForeground(Color.BLACK);
        volver.setFocusPainted(false);
        volver.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        volver.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        volver.addActionListener(e -> app.mostrar("menu"));
        c.gridy = 5;
        add(volver, c);
    }

    private void registrarHoras() {
        try {
            String ramo = ramoField.getText();
            double horas = Double.parseDouble(horasField.getText());
            app.getControladorHoras().registrarHoras(ramo, horas);
            JOptionPane.showMessageDialog(this, "Horas registradas");
            ramoField.setText("");
            horasField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Horas inválidas", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
