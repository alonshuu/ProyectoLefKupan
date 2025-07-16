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
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;

        valorField = new JTextField(15);
        GuiUtils.addPlaceholder(valorField, "Valor por hora");
        valorField.getDocument().addDocumentListener((SimpleDocumentListener) e -> actualizar());

        resultado = new JLabel("$");

        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> app.mostrar("menu"));

        c.gridy = 0;
        add(valorField, c);
        c.gridy = 1;
        add(resultado, c);
        c.gridy = 2;
        add(volver, c);
    }

    private void actualizar() {
        try {
            double valor = Double.parseDouble(valorField.getText());
            double pago = app.getAyudante().calcularPago(valor);
            resultado.setText("$" + pago);
        } catch (NumberFormatException ex) {
            resultado.setText("$");
        }
    }
}
