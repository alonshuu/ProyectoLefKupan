package lefkupan.modelo;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class HistorialTxt {

    public static void cargarHistorial(Ayudante ayudante) {
        String nombreArchivo = "historial_" + ayudante.getMatricula() + ".txt";
        File archivo = new File(nombreArchivo);

        if (!archivo.exists()) {
            System.out.println("No existe historial para esta matricula.");
            return;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    LocalDate fecha = LocalDate.parse(partes[0]);
                    String ramo = partes[1];
                    double horas = Double.parseDouble(partes[2]);

                    Ayudantia ayudantia = ayudante.getAyudantias().stream()
                            .filter(a -> a.getNombreRamo().equalsIgnoreCase(ramo))
                            .findFirst()
                            .orElse(null);

                    if (ayudantia == null) {
                        ayudantia = new Ayudantia(ramo);
                        ayudante.agregarAyudantia(ayudantia);
                    }

                    ayudantia.getRegistrosHoras().add(new RegistroHoras(fecha, horas));
                    ayudante.registrarHoras(ramo, 0);
                    ayudante.registrarHoras(ramo, horas);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar historial: " + e.getMessage());
        }
    }

    public static void guardarRegistro(Ayudante ayudante, String ramo, double horas) {
        String nombreArchivo = "historial_" + ayudante.getMatricula() + ".txt";
        String fechaHoy = LocalDate.now().toString();
        String linea = fechaHoy + "," + ramo + "," + horas;

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            escritor.write(linea);
            escritor.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar historial: " + e.getMessage());
        }
    }

    public static void eliminarAyudantiaDelArchivo(Ayudante ayudante, String ramo) {
        String nombreArchivo = "historial_" + ayudante.getMatricula() + ".txt";
        File archivo = new File(nombreArchivo);

        if (!archivo.exists()) {
            System.out.println("No existe historial para esta matricula");
            return;
        }

        File archivoTemporal = new File(nombreArchivo + ".tmp");

        try (
            BufferedReader lector = new BufferedReader(new FileReader(archivo));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivoTemporal))
        ) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3 && !partes[1].equalsIgnoreCase(ramo)) {
                    escritor.write(linea);
                    escritor.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al modificar historial: " + e.getMessage());
            return;
        }

        if (archivo.delete()) {
            archivoTemporal.renameTo(archivo);
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
    }

    public static void eliminarRegistroDelArchivo(Ayudante ayudante, String ramo, RegistroHoras registro) {
        String nombreArchivo = "historial_" + ayudante.getMatricula() + ".txt";
        File archivo = new File(nombreArchivo);
        File temp = new File(nombreArchivo + ".tmp");

        try (
                BufferedReader lector = new BufferedReader(new FileReader(archivo));
                BufferedWriter escritor = new BufferedWriter(new FileWriter(temp))
        ) {
            String linea;
            boolean eliminado = false;

            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3 &&
                        partes[0].equals(registro.getFecha().toString()) &&
                        partes[1].equalsIgnoreCase(ramo) &&
                        Double.parseDouble(partes[2]) == registro.getCantidad() &&
                        !eliminado) {
                    eliminado = true;
                    continue;
                }
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar registro del archivo");
            return;
        }

        if (archivo.delete()) {
            temp.renameTo(archivo);
        } else {
            System.out.println("No se pudo sobrescribir el archivo original");
        }
    }

}
