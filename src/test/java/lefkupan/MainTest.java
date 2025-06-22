package lefkupan;

import junit.framework.TestCase;
import lefkupan.vista.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTest extends TestCase {

    @BeforeEach
    void setup() {
        Main.totalHorasActuales = 0;
        Main.matriculaActual = "1111111122";
        Main.historialHoras.clear();
    }

    @Test
    public void testVerHorasIniciales(){
        assertEquals(0.0,Main.verHorasTotales()); // horas totales al iniciar el programa deberían ser 0.0 (double)
    }
    @Test
    public void testSumarHoras(){
        Main.registrarHoras(7.0);
        assertEquals(7.0,Main.verHorasTotales()); // deberian existir 7 horas registradas
    }
    @Test
    public void testRestarHoras(){
        Main.registrarHoras(-2.0);
        assertEquals(0.0,Main.verHorasTotales()); // deberian ser 0 horas, ya que no deberiamos poder añadir horas negativas
    }

    @Test
    public void testCalcularPagos(){
        Main.registrarHoras(7.0);
        assertEquals(14.0,Main.calcularPago(2.00)); // operatoria aritmetica simple
    }









}