package lefkupan.gui;

import com.formdev.flatlaf.FlatLightLaf;

public class MainGUI {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        new LoginGUI();
    }
}

