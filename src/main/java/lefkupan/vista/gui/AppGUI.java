package lefkupan.vista.gui;

import lefkupan.controlador.ControladorHoras;
import lefkupan.controlador.ControladorLogin;
import lefkupan.modelo.Ayudante;
import lefkupan.modelo.HistorialTxt;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class AppGUI extends JFrame {
    private CardLayout cards;
    private JPanel container;

    private Ayudante ayudanteActual;
    private ControladorHoras controladorHoras;

    public AppGUI() {
        super("Lef Küpan");


        FlatLightLaf.setup();
        UIManager.put("TextComponent.arc", 15);
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Component.innerFocusWidth", 0);
        UIManager.put("TitlePane.unifiedBackground", true);
        UIManager.put("TitlePane.background", new Color(245, 245, 245));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 420);  // Tamaño más moderno
        setLocationRelativeTo(null);

        cards = new CardLayout();
        container = new JPanel(cards);
        container.setBackground(Color.WHITE); // Fondo uniforme estilo web


        container.add(new LoginPanel(this), "login");
        container.add(new MenuPanel(this), "menu");
        container.add(new RegistrarHorasPanel(this), "registrar");
        container.add(new PagoEstimadoPanel(this), "pago");
        container.add(new ResumenHorasPanel(this), "resumen");
        container.add(new HistorialPagosPanel(this), "historial");
        container.add(new VerAyudantiasPanel(this), "ver");
        container.add(new EliminarAyudantiaPanel(this), "eliminar");
        container.add(new EliminarRegistroPanel(this), "eliminarRegistro");

        add(container);
        mostrar("login");
    }

    public void mostrar(String nombre) {
        cards.show(container, nombre);
    }

    public void setAyudante(Ayudante ayudante) {
        this.ayudanteActual = ayudante;
        this.controladorHoras = new ControladorHoras(ayudante);
    }

    public Ayudante getAyudante() {
        return ayudanteActual;
    }

    public ControladorHoras getControladorHoras() {
        return controladorHoras;
    }

    public static void launch() {
        SwingUtilities.invokeLater(() -> new AppGUI().setVisible(true));
    }

    public void login(String matricula, String contrasena) {
        Ayudante a = ControladorLogin.autenticar(matricula, contrasena);
        if (a != null) {
            setAyudante(a);
            HistorialTxt.cargarHistorial(a);
            mostrar("menu");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Credenciales inválidas",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
