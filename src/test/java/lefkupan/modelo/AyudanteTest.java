package lefkupan.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AyudanteTest {

    @Test
    public void registrarHorasCreaAyudantiaNueva() {
        Ayudante a = new Ayudante("1","pass");
        a.registrarHoras("Prog", 3);
        assertEquals(3, a.getHorasTrabajadas(), 0.0001);
        assertEquals(1, a.getAyudantias().size());
        Ayudantia ay = a.getAyudantias().get(0);
        assertEquals("Prog", ay.getNombreRamo());
        assertEquals(3, ay.getTotalHoras(), 0.0001);
    }


    // chequea que registrar horas para un curso nuevo crea una nueva Ayudantía y updatea las horas totales de forma correcta

    @Test
    public void registrarHorasNegativasNoCambia() {
        Ayudante a = new Ayudante("1","pass");
        a.registrarHoras("Prog", -2);
        assertEquals(0, a.getHorasTrabajadas(), 0.0001);
        assertTrue(a.getAyudantias().isEmpty());
    }

    // verifica que pasar horas negativas no cuenta y deja el total vacio


    @Test
    public void calcularPago() {
        Ayudante a = new Ayudante("1","pass");
        a.registrarHoras("Prog", 2);
        a.registrarHoras("Calc", 3);
        assertEquals(5, a.getHorasTrabajadas(), 0.0001);
        assertEquals(50.0, a.calcularPago(10), 0.0001);
    }

    // confirma que el monto acumulado de horas y los pagos resultados computados son asignados correctamente
    @Test
    public void eliminarAyudantiaActualizaHoras() {
        Ayudante a = new Ayudante("1","pass");
        a.registrarHoras("Prog", 2);
        a.registrarHoras("Calc", 3);
        boolean eliminado = a.eliminarAyudantia("Prog");
        assertTrue(eliminado);
        assertEquals(1, a.getAyudantias().size());
        assertEquals(3, a.getHorasTrabajadas(), 0.0001);
    }

    // SE ASEGURA que eliminar una ayudantia elimina las horas del total y ademas las quita de la lista

    @Test
    public void recalcularHorasTotalesSumaNuevosRegistros() {
        Ayudante a = new Ayudante("1","pass");
        Ayudantia ay = new Ayudantia("Prog");
        ay.agregarHoras(2);
        a.agregarAyudantia(ay);
        a.recalcularHorasTotales();
        assertEquals(2, a.getHorasTrabajadas(), 0.0001);
    }

    // valida que recalcular las horas totales recomputa las horas totales del ayudante de sus registros
}