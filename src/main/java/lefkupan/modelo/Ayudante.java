package lefkupan.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//CLASE Ayudante: representa a un ayudante de la DME, con datos y ayudantias.
public class Ayudante {
    private final String matricula;
    private String contrasena; //CAMBIO:se quito el atributo horasTrabajadas porque era un dato derivado de otro.
    private final List<Ayudantia> ayudantias;

    public Ayudante(String matricula, String contrasena){
        //CAMBIO:validaciones estrictas en el metodo contructor.
        if(matricula == null || matricula.isBlank()){
            throw new IllegalArgumentException("La matricula no puede estar vacia");
        }
        if(contrasena == null || contrasena.isBlank()){
            throw new IllegalArgumentException("La contrasena no puede estar vacia");
        }
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.ayudantias = new ArrayList<>();
    }

    public void registrarHoras(String nombreRamo, double horas) {
        //CAMBIO: metodo unico para registrar horas, reutiliza y evita duplicacion de logica.
        Ayudantia ayudantia = buscarAyudantia(nombreRamo)
                .orElseGet(() -> crearAyudantia(nombreRamo));
        ayudantia.agregarHoras(horas);
    }

    public boolean eliminarAyudantia(String nombreRamo) {
        return ayudantias.removeIf(a -> a.getNombreRamo().equalsIgnoreCase(nombreRamo));
    }

    public double calcularHorasTotales() {
        return ayudantias.stream().mapToDouble(Ayudantia::getTotalHoras).sum();
    }

    public double calcularPago(double valorHora) {
        return calcularHorasTotales() * valorHora;
    }

    public Optional<Ayudantia> buscarAyudantia(String nombreRamo) {
        return ayudantias.stream()
                .filter(a -> a.getNombreRamo().equalsIgnoreCase(nombreRamo))
                .findFirst();
    }

    public boolean verificarContrasena(String input) {
        return contrasena.equals(input);
    }

    public void cambiarContrasena(String nueva) {
        if(nueva == null || nueva.isBlank()){
            throw new IllegalArgumentException("La contrasena no puede estar vacia");
        }
        this.contrasena = nueva;
    }

    public String getMatricula(){
        return matricula;
    }

    public List<Ayudantia> getAyudantias() {
        return new ArrayList<>(ayudantias);
    }

    private Ayudantia crearAyudantia(String nombreRamo){
        //CAMBIO: metodo auxiliar privado para crear ayudantias.
        Ayudantia nueva = new Ayudantia(nombreRamo);
        ayudantias.add(nueva);
        return nueva;
   }

}
