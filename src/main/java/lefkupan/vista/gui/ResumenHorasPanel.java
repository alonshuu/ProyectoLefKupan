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
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Campo de valor por hora
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(Color.WHITE);

        JLabel valorLabel = new JLabel("Valor por hora:");
        valorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        valorField = new JTextField(10);
        GuiUtils.addPlaceholder(valorField, "Ej: 5000");
        valorField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        valorField.getDocument().addDocumentListener((SimpleDocumentListener) e -> actualizar());

        top.add(valorLabel);
        top.add(Box.createVerticalStrut(5));
        top.add(valorField);
        top.add(Box.createVerticalStrut(10));

        add(top, BorderLayout.NORTH);

        // Lista de ayudantías
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        // Pie de página con total y botón volver
        totalHorasLabel = new JLabel();
        totalPagoLabel = new JLabel("Pago estimado: $");
        totalPagoLabel.setForeground(new Color(0, 128, 0));
        totalPagoLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton volver = new JButton("Volver");
        volver.setBackground(new Color(200, 200, 200));
        volver.setForeground(Color.BLACK);
        volver.setFocusPainted(false);
        volver.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        volver.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        volver.setAlignmentX(Component.LEFT_ALIGNMENT);
        volver.addActionListener(e -> app.mostrar("menu"));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(Color.WHITE);
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottom.add(totalHorasLabel);
        bottom.add(Box.createVerticalStrut(5));
        bottom.add(totalPagoLabel);
        bottom.add(Box.createVerticalStrut(10));
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
            p.setBackground(new Color(245, 245, 245));
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            p.add(new JLabel("📘 Ramo: " + a.getNombreRamo()));
            p.add(Box.createVerticalStrut(3));
            p.add(new JLabel("⏱ Horas: " + a.getTotalHoras()));
            p.add(Box.createVerticalStrut(5));
            a.getRegistrosHoras().forEach(r -> {
                JLabel registroLabel = new JLabel("• " + r.toString());
                registroLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                p.add(registroLabel);
            });

            listPanel.add(p);
            listPanel.add(Box.createVerticalStrut(10));
        }

        totalHorasLabel.setText("Total de horas trabajadas: " + app.getAyudante().getHorasTrabajadas());
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
