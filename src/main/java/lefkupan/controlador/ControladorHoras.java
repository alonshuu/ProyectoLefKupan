package lefkupan.controlador;

import lefkupan.modelo.Ayudante;
import lefkupan.modelo.Ayudantia;

//CLASE ControladorHoras: controlador de operaciones sobre las ayudantias y el registro de horas.
public class ControladorHoras {

    private final Ayudante ayudante; //CAMBIO: atributo final para asegurar consistencia e inmutabilidad.

    public ControladorHoras(Ayudante ayudante) {
        //CAMBIO: validacion obligatoria para evitar errores en ejecucion.
        if (ayudante == null) {
            throw new IllegalArgumentException("Ayudante no puede estar vacio");
        }
        this.ayudante = ayudante;
    }

    public void registrarHoras(String ramo, double horas) { //registra horas en una ayudantia existente o nueva.
        //CAMBIO: delegacion completa al paquete modelo.
        ayudante.registrarHoras(ramo, horas);
    }

    private void mostrarEncabezadoResumen() { //muestra el encabezado del resumen con la informacion basica del ayudante.
        System.out.println("Resumen de ayudantias para: " + ayudante.getMatricula());
    }

    private void mostrarDetalleAyudantias() { //muestra en consola cada ayudantia con su detalle.
        for (Ayudantia a : ayudante.getAyudantias()) {
            System.out.println(a); //CAMBIO: uso de toString en Ayudantia.
        }
    }

    private void mostrarTotales(double valorHora) { //calcula y muestra el total de horas y el pago correspondiente.
        //CAMBIO: este metodo ya no calcula nada manualmente, se apoya del paquete modelo.
        double totalHoras = ayudante.calcularHorasTotales();
        double totalPago = ayudante.calcularHorasTotales();

        System.out.printf("Total de horas: %.2f hrs\n", totalHoras);
        System.out.printf("Pago estimado: $%.0f\n", totalPago);
    }

    public Ayudante getAyudante() {
        return ayudante;
    }
}
