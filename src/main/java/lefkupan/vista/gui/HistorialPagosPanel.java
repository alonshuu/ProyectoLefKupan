package lefkupan.vista.gui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class HistorialPagosPanel extends JPanel {
    private final AppGUI app;
    private JTextField valorField;
    private JPanel listPanel;
    private JLabel totalLabel;

    HistorialPagosPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));


        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(Color.WHITE);

        JLabel valorLabel = new JLabel("Valor por hora:");
        valorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valorLabel.setForeground(Color.DARK_GRAY);
        valorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        valorField = new JTextField(10);
        valorField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        GuiUtils.addPlaceholder(valorField, "Ej: 5000");
        valorField.getDocument().addDocumentListener((SimpleDocumentListener) e -> refrescar());

        top.add(valorLabel);
        top.add(Box.createVerticalStrut(5));
        top.add(valorField);
        top.add(Box.createVerticalStrut(10));
        add(top, BorderLayout.NORTH);


        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);


        totalLabel = new JLabel("Total de pagos: $0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setForeground(new Color(0, 128, 0));

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
        bottom.add(totalLabel);
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
        double valor;
        try {
            valor = Double.parseDouble(valorField.getText());
        } catch (NumberFormatException e) {
            valor = 0;
        }

        double total = 0;
        for (String[] partes : leerLineasDesdeArchivo()) {
            String fecha = partes[0];
            String ramo = partes[1];
            double horas = Double.parseDouble(partes[2]);
            double pago = horas * valor;
            total += pago;

            JPanel p = new JPanel(new GridLayout(1, 4, 10, 0));
            p.setBackground(new Color(245, 245, 245));
            p.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));

            p.add(new JLabel("📅 " + fecha));
            p.add(new JLabel("📘 " + ramo));
            p.add(new JLabel("⏱ " + horas + " hrs"));

            JLabel monto = new JLabel("💲$" + pago);
            monto.setForeground(new Color(0, 128, 0));
            monto.setFont(new Font("Segoe UI", Font.BOLD, 13));
            p.add(monto);

            listPanel.add(p);
            listPanel.add(Box.createVerticalStrut(8));
        }

        totalLabel.setText("Total de pagos: $" + total);
        revalidate();
        repaint();
    }

    private List<String[]> leerLineasDesdeArchivo() {
        List<String[]> registros = new ArrayList<>();
        String archivo = "historial_" + app.getAyudante().getMatricula() + ".txt";
        File file = new File(archivo);
        if (!file.exists()) {
            return registros;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) registros.add(parts);
            }
        } catch (IOException ignored) {}
        return registros;
    }
}

