package lefkupan.modelo;

public interface BaseDatos {
    Ayudante obtenerAyudantePorMatricula(String matricula);
    void guardarAyudante(Ayudante ayudante);
}
