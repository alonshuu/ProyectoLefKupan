package lefkupan.modelo.persistencia;

import lefkupan.modelo.dominio.Ayudantia;
import lefkupan.modelo.dominio.RegistroHoras;
import lefkupan.modelo.dominio.TipoActividad;
import lefkupan.modelo.usuario.Ayudante;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

public class HistorialTxt { //cargar y guardar registros de horas en archivos .txt por ayudante
    private static final String CARPETA_BASE = "data";

    static {
        //crear carpeta si no existe
        Path carpeta = Paths.get(CARPETA_BASE);
        try {
            if (!Files.exists(carpeta)) {
                Files.createDirectories(carpeta);
            }
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de carpeta");
        }
    }

    public static void cargarHistorial(Ayudante ayudante) { //carga el historial desde el archivo .txt para un ayudante
        Path archivo = getRutaArchivo(ayudante.getMatricula());
        if (!Files.exists(archivo)) return;

        try (BufferedReader reader = Files.newBufferedReader(archivo)) {
            String linea;
            while ((linea = reader.readLine()) !=null) {
                //formato: fecha, ramo, horas, tipoActividad
                String[] partes = linea.split(";");
                if(partes.length != 4) continue;

                LocalDate fecha = LocalDate.parse(partes[0]);
                String ramo = partes[1];
                double horas = Double.parseDouble(partes[2]);
                TipoActividad tipo = TipoActividad.valueOf(partes[3]);

                Ayudantia ayudantia = buscarOcrearAyudantia(ayudante, ramo);
                ayudantia.agregarHoras(fecha, horas, tipo);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al leer el historial: " + e.getMessage());
        }
    }

    public static void guardarRegistro(Ayudante ayudante, String ramo, RegistroHoras registro) { //guarda un registro nuevo para el ayudante
        Path archivo = getRutaArchivo(ayudante.getMatricula());

        try (BufferedWriter writer = Files.newBufferedWriter(archivo, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String linea = String.format("%s,%s,%.2f,%s",
                    registro.getFecha(),
                    ramo,
                    registro.getCantidad(),
                    registro.getTipoActividad().name());

            writer.write(linea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar registro: " + e.getMessage());
        }
    }

    public static void eliminarRegistroDelArchivo(Ayudante ayudante, String ramo, RegistroHoras registro) { //elimina un registro especifico del archivo
        Path archivo = getRutaArchivo(ayudante.getMatricula());
        if (!Files.exists(archivo)) return;

        try {
            List<String> lineas = Files.readAllLines(archivo);
            String target = String.format("%s,%s,%.2f,%s",
                    registro.getFecha(),
                    ramo,
                    registro.getCantidad(),
                    registro.getTipoActividad().name());

            // CAMBIO: elimina solo esa línea exacta
            boolean eliminado = lineas.removeIf(linea -> linea.trim().equals(target));
            if (eliminado) {
                Files.write(archivo, lineas);
            }

        } catch (IOException e) {
            System.err.println("Error al eliminar registro: " + e.getMessage());
        }
    }

    public static void eliminarAyudantiaDelArchivo(Ayudante ayudante, String ramo) { //elimina todas las entradas de una ayudantia especifica
        Path archivo = getRutaArchivo(ayudante.getMatricula());
        if (!Files.exists(archivo)) return;

        try {
            List<String> lineas = Files.readAllLines(archivo);
            lineas.removeIf(linea -> linea.split(",")[1].equalsIgnoreCase(ramo));
            Files.write(archivo, lineas);
        } catch (IOException e) {
            System.err.println("Error al eliminar ayudantía: " + e.getMessage());
        }
    }

    public static void mostrarHistorialPagos(Ayudante ayudante, double valorHora) { //muestra por consola el historial y pago estimado de un ayudante
        System.out.println("\n Historial de horas:");
        for (Ayudantia ayudantia : ayudante.getAyudantias()) {
            System.out.println("\n" + ayudantia.getNombreRamo());
            for (RegistroHoras registrohoras : ayudantia.getRegistrosHoras()) {
                System.out.println(" - " + registrohoras);
            }
        }

        System.out.printf("\n Total Horas: %.2f | Pago Estimado: $%.0f\n",
                ayudante.getHorasTotales(),
                ayudante.calcularPago(valorHora));
    }

    private static Path getRutaArchivo(String matricula) {
        return Paths.get(CARPETA_BASE, matricula + ".txt");
    }

    private static Ayudantia buscarOcrearAyudantia(Ayudante ayudante, String nombreRamo) {
        for (Ayudantia ayudantia : ayudante.getAyudantias()) {
            if (ayudantia.getNombreRamo().equalsIgnoreCase(nombreRamo)) {
                return ayudantia;
            }
        }
        Ayudantia nueva = new Ayudantia(nombreRamo);
        ayudante.agregarAyudantia(nueva);
        return nueva;
    }




}
