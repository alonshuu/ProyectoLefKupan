package lefkupan.modelo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//CLASE HistorialTxt: clase utilitaria, sirve para guargar y leer archivos desde archivos .txt .
public class HistorialTxt {

    private static final String BASE_PATH = "historial/";

    public static void guardarRegistro(Ayudante ayudante, String ramo, double horas) {
        //CAMBIO: try-with-resources para manejo seguro de archivos.
        String archivo = obtenerRuta(ayudante);
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write(LocalDate.now() + "," + ramo + "," + horas + "\n");
        } catch (IOException e) {
            System.err.println("Error al guardar el registro: " + e.getMessage());
        }
    }

    public static void cargarHistorial(Ayudante ayudante) {
        File archivo = new File(obtenerRuta(ayudante));
        if (!archivo.exists()) return;

        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                String[] partes = scanner.nextLine().split(",");
                if (partes.length != 3) continue;

                LocalDate fecha = LocalDate.parse(partes[0]);
                String ramo = partes[1];
                double horas = Double.parseDouble(partes[2]);

                ayudante.buscarAyudantia(ramo)
                        .orElseGet(() -> {
                            Ayudantia nueva = new Ayudantia(ramo);
                            ayudante.getAyudantias().add(nueva);
                            return nueva;
                        })
                        .agregarHoras(fecha, horas);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar historial: " + e.getMessage());
        }
    }

    private static String obtenerRuta(Ayudante ayudante) {
        return BASE_PATH + ayudante.getMatricula() + ".txt";
    }


}
