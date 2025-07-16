package lefkupan.vista.gui;

import javax.swing.*;
import java.awt.*;

class VerAyudantiasPanel extends JPanel {
    private final AppGUI app;
    private JPanel listPanel;

    VerAyudantiasPanel(AppGUI app) {
        this.app = app;
        init();
    }

    private void init() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));


        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);


        JButton volver = new JButton("Volver");
        volver.setBackground(new Color(200, 200, 200));
        volver.setForeground(Color.BLACK);
        volver.setFocusPainted(false);
        volver.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        volver.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        volver.addActionListener(e -> app.mostrar("menu"));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(Color.WHITE);
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
        app.getAyudante().getAyudantias().forEach(a -> {
            JPanel p = new JPanel();
            p.setBackground(new Color(245, 245, 245));
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            JLabel ramo = new JLabel("📘 Nombre del ramo: " + a.getNombreRamo());
            JLabel horas = new JLabel("⏱ Horas trabajadas: " + a.getTotalHoras());

            p.add(ramo);
            p.add(Box.createVerticalStrut(5));
            p.add(horas);

            listPanel.add(p);
            listPanel.add(Box.createVerticalStrut(10));
        });

        revalidate();
        repaint();
    }
}

