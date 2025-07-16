package lefkupan.vista.gui;

import javax.swing.*;
import java.awt.*;

class PagoEstimadoPanel extends JPanel {
    private final AppGUI app;
    private JTextField valorField;
    private JLabel resultado;

    PagoEstimadoPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 0, 12, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.weightx = 1.0;


        JLabel valorLabel = new JLabel("Valor por hora:");
        valorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valorLabel.setForeground(Color.DARK_GRAY);
        c.gridy = 0;
        add(valorLabel, c);


        valorField = new JTextField(15);
        valorField.setPreferredSize(new Dimension(200, 30));
        GuiUtils.addPlaceholder(valorField, "Ej: 5000");
        valorField.getDocument().addDocumentListener((SimpleDocumentListener) e -> actualizar());
        c.gridy = 1;
        add(valorField, c);


        resultado = new JLabel("Pago estimado: $");
        resultado.setFont(new Font("Segoe UI", Font.BOLD, 16));
        resultado.setForeground(new Color(0, 128, 0));
        c.gridy = 2;
        add(resultado, c);


        JButton volver = new JButton("Volver");
        volver.setBackground(new Color(200, 200, 200));
        volver.setForeground(Color.BLACK);
        volver.setFocusPainted(false);
        volver.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        volver.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        volver.addActionListener(e -> app.mostrar("menu"));
        c.gridy = 3;
        add(volver, c);
    }

    private void actualizar() {
        try {
            double valor = Double.parseDouble(valorField.getText());
            double pago = app.getAyudante().calcularPago(valor);
            resultado.setText("Pago estimado: $" + pago);
        } catch (NumberFormatException ex) {
            resultado.setText("Pago estimado: $");
        }
    }
}
