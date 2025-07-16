package lefkupan.modelo.usuario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Administrador { //representa a un administrador del sistema, tiene permisos para registrar usuarios y revisar los historiales
    private final String id;

    public Administrador(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void registrarNuevoUsuario(String matricula, String contrasena, RolUsuario rol) { //registra un nuevo usuario en el archivo usuario.txt
        Path archivo = Path.of("src/main/resources/usuarios.txt");

        try {
            List<String> lineas = Files.readAllLines(archivo);

            for (String linea : lineas) {
                if (linea.startsWith(matricula + ",")) {
                    System.out.println("El usuario ya existe");
                    return;
                }
            }
            //CAMBIO: agrega linea con un rol al archivo
            String nuevaLinea = String.format("%s %s", matricula, contrasena, rol.name());
            Files.write(archivo, List.of(nuevaLinea), StandardOpenOption.APPEND);
            System.out.println("El usuario se ha registrado correctamente");

        } catch (IOException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
    }

    public void verHistorialTodosLosAyudantes() {
        File carpeta = new File("data");
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".txt"));

        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay registros");
            return;
        }

        for(File archivo : archivos){
            System.out.println("Historial de: "+archivo.getName().replace(".txt",""));
            try {
                List<String> lineas = Files.readAllLines(archivo.toPath());
                for (String linea : lineas) {
                    System.out.println(" - " + linea);
                }
            } catch (IOException e) {
                System.out.println("Error al leer " + archivo.getName());
            }
        }
    }
}
