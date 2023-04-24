package basicFunction;

import java.time.LocalDate;

public class Elector {
    private final int dni;
    private final String nombre;
    private final LocalDate fecha;

    public Elector(int dni, String nombre, LocalDate fecha) {
        this.dni = dni;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "Elector[" +
                "dni=" + dni +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ']';
    }
}
