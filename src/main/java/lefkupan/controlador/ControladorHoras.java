package lefkupan.controlador;

import lefkupan.modelo.dominio.Ayudantia;
import lefkupan.modelo.dominio.RegistroHoras;
import lefkupan.modelo.dominio.TipoActividad;
import lefkupan.persistencia.HistorialJson;
import lefkupan.modelo.usuario.Ayudante;

import java.time.LocalDate;
import java.util.List;

public class ControladorHoras { //maneja el registro y consulta de horas para un ayudante
    private final Ayudante ayudante;

    public ControladorHoras(Ayudante ayudante) {
        this.ayudante = ayudante;
    }

    public Ayudante getAyudante() {
        return ayudante;
    }

    public void registrarHoras(String nombreRamo, LocalDate fecha, double horas, TipoActividad tipoActividad) { //registra horas trabajadas en una ayudantia especifica
       Ayudantia ayudantia = buscarOcrearAyudantia(nombreRamo);
       ayudantia.agregarHoras(fecha, horas, tipoActividad);

       //CAMBIO:guardar despues de agregar
        HistorialJson.guardarHistorial(ayudante);
    }

    public boolean eliminarAyudantia(String nombreRamo) {
        boolean eliminada = ayudante.eliminarAyudantia(nombreRamo);
        if (eliminada) {
            HistorialJson.guardarHistorial(ayudante);
        }
        return eliminada;
    }

    public boolean eliminarRegistroEspecifico(String nombreRamo, RegistroHoras registro) {
        for (Ayudantia ayudantia : ayudante.getAyudantias()) {
            if (ayudantia.getNombreRamo().equals(nombreRamo)) {
                boolean ok = ayudantia.eliminarRegistro(registro.getFecha(), registro.getCantidad());
                if (ok) {
                    HistorialJson.guardarHistorial(ayudante);
                }
                return ok;
            }
        }
        return false;
    }

    public void mostrarEncabezadoResumen() {
        System.out.printf("\nResumen del ayudante %s:\n", ayudante.getMatricula());
        System.out.printf("Total horas trabajadas: %.2f\n", ayudante.getHorasTotales());
    }

    public void mostrarDetalleAyudantias() {
        List<Ayudantia> lista = ayudante.getAyudantias();
        if (lista.isEmpty()) {
            System.out.println("No hay ayudantias registradas");
            return;
        }

        for (Ayudantia ayudantia : lista) {
            System.out.println("\n" + ayudantia);
            for (RegistroHoras registrohoras : ayudantia.getRegistrosHoras()) {
                System.out.println(" - " + registrohoras);
            }
        }
    }

    private Ayudantia buscarOcrearAyudantia(String nombreRamo) {
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
