package lefkupan.vista.gui;

import lefkupan.modelo.Ayudantia;

import javax.swing.*;
import java.awt.*;

class ResumenHorasPanel extends JPanel {
    private final AppGUI app;
    private JPanel listPanel;
    private JTextField valorField;
    private JLabel totalHorasLabel;
    private JLabel totalPagoLabel;

    ResumenHorasPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        valorField = new JTextField(10);
        GuiUtils.addPlaceholder(valorField, "Valor por hora");
        valorField.getDocument().addDocumentListener((SimpleDocumentListener) e -> actualizar());
        JPanel top = new JPanel();
        top.add(valorField);
        add(top, BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);

        totalHorasLabel = new JLabel();
        totalPagoLabel = new JLabel("$");
        totalPagoLabel.setForeground(new Color(0,128,0));
        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> app.mostrar("menu"));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.add(totalHorasLabel);
        bottom.add(totalPagoLabel);
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
        for (Ayudantia a : app.getAyudante().getAyudantias()) {
            JPanel p = new JPanel();
            p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.add(new JLabel("Ramo: " + a.getNombreRamo()));
            p.add(new JLabel("Horas: " + a.getTotalHoras()));
            a.getRegistrosHoras().forEach(r -> p.add(new JLabel(r.toString())));
            listPanel.add(p);
        }
        totalHorasLabel.setText("Total horas: " + app.getAyudante().getHorasTrabajadas());
        actualizar();
        revalidate();
        repaint();
    }

    private void actualizar() {
        try {
            double valor = Double.parseDouble(valorField.getText());
            double pago = app.getAyudante().calcularPago(valor);
            totalPagoLabel.setText("Pago estimado: $" + pago);
        } catch (NumberFormatException ex) {
            totalPagoLabel.setText("Pago estimado: $");
        }
    }
}
