package lefkupan.controlador;

import lefkupan.modelo.Ayudante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ControladorLogin {
    public static Ayudante autenticar(String matricula, String contrasena) {
        try {
            InputStream is = ControladorLogin.class.getClassLoader().getResourceAsStream("usuarios.txt");
            if (is == null) {
                System.out.println("Archivo no encontrado");
                return null;
            }

            BufferedReader lector = new BufferedReader(new InputStreamReader(is));
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2 && partes[0].equals(matricula) && partes[1].equals(contrasena)) {
                    return new Ayudante(matricula, contrasena);
                }
            }
            lector.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
        }

        return null;
    }
}
