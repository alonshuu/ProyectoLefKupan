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
        setLayout(new BorderLayout(10,10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(listPanel);

        JButton volver = new JButton("Volver");
        volver.addActionListener(e -> app.mostrar("menu"));

        add(scroll, BorderLayout.CENTER);
        add(volver, BorderLayout.SOUTH);
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
            p.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.add(new JLabel("Nombre del ramo: " + a.getNombreRamo()));
            p.add(new JLabel("Horas trabajadas: " + a.getTotalHoras()));
            listPanel.add(p);
        });
        revalidate();
        repaint();
    }
}
