package lefkupan.modelo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistorialTxt {

    public static void cargarHistorial(Ayudante ayudante) {
        List<String[]> lineas = leerLineasDesdeArchivo(ayudante);
        for (String[] partes : lineas) {
            procesarLineaHistorial(ayudante, partes);
        }
    }

    public static void guardarRegistro(Ayudante ayudante, String ramo, double horas) {
        String linea = LocalDate.now() + "," + ramo + "," + horas;
        escribirLinea(ayudante, linea);
    }

    public static void eliminarAyudantiaDelArchivo(Ayudante ayudante, String ramo) {
        List<String[]> lineas = leerLineasDesdeArchivo(ayudante);
        List<String> filtradas = new ArrayList<>();

        for (String[] partes : lineas) {
            if (!partes[1].equalsIgnoreCase(ramo)) {
                filtradas.add(String.join(",", partes));
            }
        }
        reemplazarArchivo(ayudante, filtradas);
    }

    public static void eliminarRegistroDelArchivo(Ayudante ayudante, String ramo, RegistroHoras registro) {
        List<String[]> lineas = leerLineasDesdeArchivo(ayudante);
        List<String> filtradas = new ArrayList<>();
        boolean eliminado = false;

        for (String[] partes : lineas) {
            boolean esIgual = partes[0].equals(registro.getFecha().toString()) && partes[1].equalsIgnoreCase(ramo) && Double.parseDouble(partes[2]) == registro.getCantidad();

            if (!eliminado && esIgual) {
                filtradas.add(String.join(",", partes));
            }
        }
        reemplazarArchivo(ayudante, filtradas);
    }

    public static void mostrarHistorialPagos(Ayudante ayudante, double valorPorHora) {
        List<String[]> lineas = leerLineasDesdeArchivo(ayudante);
        double total = mostrarTablaPagos(lineas, valorPorHora);
        mostrarTotalPagado(total);
    }

    private static List<String[]> leerLineasDesdeArchivo(Ayudante ayudante) {
        List<String[]> registros = new ArrayList<>();
        String archivo = "historial_" + ayudante.getMatricula() + ".txt";

        File file = new File(archivo);
        if (!file.exists()) {
            System.out.println("No existe historial para esta matricula.");
            return registros;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    registros.add(partes);
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer el historial: " + e.getMessage());
        }

        return registros;
    }

    private static void procesarLineaHistorial(Ayudante ayudante, String[] partes) {
        try {
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
            ayudante.registrarHoras(ramo, 0); // evitar duplicar
            ayudante.registrarHoras(ramo, horas);
        } catch (Exception e) {
            System.out.println("Error al procesar linea de historial");
        }
    }

    private static void escribirLinea(Ayudante ayudante, String linea) {
        String archivo = "historial_" + ayudante.getMatricula() + ".txt";
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo, true))) {
            escritor.write(linea);
            escritor.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar historial: " + e.getMessage());
        }
    }

    private static void reemplazarArchivo(Ayudante ayudante, List<String> nuevasLineas) {
        String archivo = "historial_" + ayudante.getMatricula() + ".txt";
        File original = new File(archivo);
        File temporal = new File(archivo + ".tmp");

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(temporal))) {
            for (String linea : nuevasLineas) {
                escritor.write(linea);
                escritor.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir archivo temporal");
            return;
        }

        if (!original.delete() || !temporal.renameTo(original)) {
            System.out.println("Error al sobrescribir el archivo original");
        }
    }

    private static double mostrarTablaPagos(List<String[]> registros, double valorPorHora) {
        double total = 0;

        System.out.println("========== Historial de Pagos ==========");
        System.out.printf("%-12s | %-15s | %-8s | %-8s%n", "Fecha", "Ramo", "Horas", "Pago");
        System.out.println("--------------------------------------------");

        for (String[] partes : registros) {
            String fecha = partes[0];
            String ramo = partes[1];
            double horas = Double.parseDouble(partes[2]);
            double pago = horas * valorPorHora;
            total += pago;

            System.out.printf("%-12s | %-15s | %-8.2f | $%-8.2f%n", fecha, ramo, horas, pago);
        }

        return total;
    }

    private static void mostrarTotalPagado(double total) {
        System.out.println("--------------------------------------------");
        System.out.printf("Total de pagos: $%.2f%n", total);
    }


}
