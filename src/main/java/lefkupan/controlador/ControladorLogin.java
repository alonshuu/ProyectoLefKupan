package lefkupan.controlador;

import lefkupan.modelo.Ayudante;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//CLASE ControladorLogin: clase encargada de autenticar usuarios desde un archivo plano.
public class ControladorLogin {

    public static final String RUTA_USUARIOS = "src/main/resources/usuarios.txt";

    public static Ayudante autenticar(String matricula, String contrasena) {
        //CAMBIO: autentica un ayudante a partir del archivo usuario
        try (Scanner scanner = obtenerLectorUsuarios()) {
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (datos.length != 2) continue;

                String mat = datos[0];
                String pass = datos[1];

                if (credencialesValidas(matricula, contrasena,mat, pass)) {
                    //CAMBIO: reutilizacion de comparacion en metodo separado.
                    return new Ayudante(mat, pass);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado");
        }
        return null; //CAMBIO: autenticacion fallida controlada.
    }

    private static Scanner obtenerLectorUsuarios() throws FileNotFoundException {
        //CAMBIO: metodo privado para centralizar lectura del archivo de usuarios.
        return new Scanner(new File(RUTA_USUARIOS));
    }

    private static boolean credencialesValidas(String entradaMatricula, String entradaContrasena, String mat, String pass) {

        return entradaMatricula.equals(mat) && entradaContrasena.equals(pass);
    }
}
