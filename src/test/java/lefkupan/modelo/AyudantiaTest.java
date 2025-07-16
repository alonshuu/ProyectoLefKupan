package lefkupan.modelo;

import lefkupan.modelo.dominio.Ayudantia;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AyudantiaTest {

    @Test
    public void agregarHorasInvalidaLanzaExcepcion() {
        Ayudantia a = new Ayudantia("Prog");
        assertThrows(IllegalArgumentException.class, () -> a.agregarHoras(-1));
    } // lanza una execepcion si se le agregan horas negativas

    @Test
    public void agregarHorasAcumulaRegistros() {
        Ayudantia a = new Ayudantia("Prog");
        a.agregarHoras(2);
        a.agregarHoras(1.5);
        assertEquals(2, a.getRegistrosHoras().size());
        assertEquals(3.5, a.getTotalHoras(), 0.0001);
    } // confirma que llamadas multiples a la misma asignatura guarda las horas de forma correcta
}