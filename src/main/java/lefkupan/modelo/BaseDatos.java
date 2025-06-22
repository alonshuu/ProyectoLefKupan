package lefkupan.modelo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
    private static final String ARCHIVO = "src/main/resources/usuarios.txt";

    public static List<Ayudante> cargarAyudantes() {
        List<Ayudante> ayudantes = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String matricula = partes[0];

            }
        }
    }
}
