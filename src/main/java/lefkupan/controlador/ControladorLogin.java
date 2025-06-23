package lefkupan.controlador;

import lefkupan.modelo.Ayudante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ControladorLogin {
    public static Ayudante autenticar(String matricula, String contrasena) {
        BufferedReader lector = obtenerLectorUsuarios();
        if (lector == null) return null;

        try {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (credencialesValidas(linea, matricula, contrasena)) {
                    return new Ayudante(matricula, contrasena);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo usuarios: " + e.getMessage());
        }
        return null;
    }

    private static BufferedReader obtenerLectorUsuarios() {
        InputStream entrada = ControladorLogin.class.getClassLoader().getResourceAsStream("usuarios.txt");

        if (entrada == null) {
            System.out.println("Archivo 'usuarios.txt' no encontrado.");
            return null;
        }

        return new BufferedReader(new InputStreamReader(entrada));
    }

    private static boolean credencialesValidas(String linea, String matricula, String contrasena) {
        String[] partes = linea.split(";");
        return partes.length == 2 &&
                partes[0].trim().equals(matricula) &&
                partes[1].trim().equals(contrasena);
    }
}
