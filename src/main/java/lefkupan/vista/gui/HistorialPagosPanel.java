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
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        valorField = new JTextField(10);
        GuiUtils.addPlaceholder(valorField, "Valor por hora");
        valorField.getDocument().addDocumentListener((SimpleDocumentListener) e -> refrescar());
        JPanel top = new JPanel();
        top.add(valorField);
        add(top, BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);

        totalLabel = new JLabel("Total de pagos: $0");
        totalLabel.setForeground(new Color(0,128,0));
        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> app.mostrar("menu"));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.add(totalLabel);
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

            JPanel p = new JPanel(new GridLayout(1, 0));
            p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            p.add(new JLabel(fecha));
            p.add(new JLabel(ramo));
            p.add(new JLabel(horas + " hrs"));
            JLabel monto = new JLabel("$" + pago);
            monto.setForeground(new Color(0,128,0));
            p.add(monto);
            listPanel.add(p);
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
